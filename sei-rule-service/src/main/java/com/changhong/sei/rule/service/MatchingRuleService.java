package com.changhong.sei.rule.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.rule.api.MatchingRuleApi;
import com.changhong.sei.rule.api.MatchingRuleComparator;
import com.changhong.sei.rule.dao.MatchingRuleDao;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.DataType;
import com.changhong.sei.rule.dto.enums.RuleCategory;
import com.changhong.sei.rule.entity.MatchingRule;
import com.changhong.sei.rule.service.exception.MatchingRuleComparatorException;
import com.changhong.sei.serial.sdk.SerialService;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.changhong.sei.util.DateUtils.DEFAULT_DATE_FORMAT;


/**
 * 业务匹配规则(MatchingRule)业务逻辑实现类
 *
 * @author sei
 * @since 2020-08-14 09:22:20
 */
@Service("matchingRuleService")
public class MatchingRuleService extends BaseTreeService<MatchingRule> {

    public static final String RULE_CHAIN_CACHE_KEY_PREFIX = "sei:sei-rule:rule-chains:";

    @Autowired
    private MatchingRuleDao dao;
    @Autowired
    private SerialService serialService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected BaseTreeDao<MatchingRule> getDao() {
        return dao;
    }

    /**
     * 检查菜单父节点
     *
     * @param parentId 父节点Id
     * @return 检查结果
     */
    private OperateResult checkParentNode(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            // 检查通过！
            return OperateResult.operationSuccess("00001");
        }
        MatchingRule parent = dao.findOne(parentId);
        if (Objects.isNull(parent)) {
            // 检查通过！
            return OperateResult.operationSuccess("00001");
        }
        // 检查通过！
        return OperateResult.operationSuccess("00001");
    }

    /**
     * 保存一个规则树节点
     *
     * @param entity 规则树节点
     * @return 操作结果
     */
    @Override
    public OperateResultWithData<MatchingRule> save(MatchingRule entity) {
        // 检查父节点
        OperateResult checkResult = checkParentNode(entity.getParentId());
        if (checkResult.notSuccessful()) {
            return OperateResult.converterWithData(checkResult, entity);
        }
        if (StringUtils.isBlank(entity.getCode())) {
            entity.setCode(serialService.getNumber(MatchingRule.class));
        }
        // 设置节点名称
        if (StringUtils.isBlank(entity.getName())) {
            entity.setName(entity.getPropertyName());
        }
        return super.save(entity);
    }

    /**
     * 移动节点
     *
     * @param nodeId         当前节点ID
     * @param targetParentId 目标父节点ID
     * @return 返回操作结果对象
     */
    @Override
    public OperateResult move(String nodeId, String targetParentId) {
        // 检查父节点
        OperateResult checkResult = checkParentNode(targetParentId);
        if (checkResult.notSuccessful()) {
            return checkResult;
        }
        return super.move(nodeId, targetParentId);
    }

    /**
     * 通过分类获取所有规则树
     *
     * @param category 规则分类
     * @return 规则树集合
     */
    public List<MatchingRule> getRuleTrees(RuleCategory category) {
        // 限制规则分类
        List<MatchingRule> rootTree = dao.findRootNode(category);
        if (CollectionUtils.isEmpty(rootTree)) {
            return new ArrayList<>();
        }
        List<MatchingRule> rootRuleTree = new ArrayList<>();
        for (MatchingRule aRootTree : rootTree) {
            MatchingRule rule = getTree(aRootTree.getId());
            rootRuleTree.add(rule);
        }
        return rootRuleTree;
    }

    /**
     * 通过公司和分类获取规则根节点
     *
     * @param category 规则分类
     * @param corpCode 公司代码
     * @return 规则根节点
     */
    public List<MatchingRule> getRuleRootNodes(RuleCategory category, String corpCode) {
        // 限制规则分类
        List<MatchingRule> rootNodes = dao.findRootNode(category);
        if (CollectionUtils.isEmpty(rootNodes)) {
            return new ArrayList<>();
        }
        return rootNodes;
    }

    /**
     * 删除一个规则树
     *
     * @param rootNodeId 根节点Id
     * @return 处理结果
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRuleTree(String rootNodeId) {
        // 获取树
        MatchingRule tree = getTree(rootNodeId);
        if (Objects.isNull(tree)) {
            return;
        }
        //删除规则链缓存
        redisTemplate.delete(RULE_CHAIN_CACHE_KEY_PREFIX + rootNodeId);
        // 获取所有树节点清单
        List<MatchingRule> rules = unBuildTree(Collections.singletonList(tree));
        // 按层级倒序排列
        Comparator<MatchingRule> comparator = Comparator.comparing(MatchingRule::getNodeLevel);
        rules = rules.stream().sorted(comparator.reversed()).collect(Collectors.toList());
        rules.forEach(node -> dao.delete(node));
    }

    /**
     * 保存业务规则树
     *
     * @param ruleNode 业务规则树节点(包含子节点)
     * @return 处理结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult saveRuleTree(MatchingRule ruleNode) {
        // 如果根节点已经存在，则删除整棵树的所有节点
        if (StringUtils.isNotBlank(ruleNode.getId())) {
            deleteRuleTree(ruleNode.getId());
            // 全部新建
            ruleNode.setId(null);
            ruleNode.setCode(null);
        }
        OperateResultWithData<MatchingRule> saveResult = save(ruleNode);
        if (saveResult.notSuccessful()) {
            return OperateResultWithData.converterNoneData(saveResult);
        }
        saveChildren(saveResult.getData(), ruleNode.getChildren());
        // 业务规则【{0}】保存成功！
        return OperateResult.operationSuccess("00151", ruleNode.getName());
    }

    /**
     * 递归保存业务规则树子节点
     *
     * @param ruleNode 树节点
     * @param children 子节点清单
     */
    private void saveChildren(MatchingRule ruleNode, List<MatchingRule> children) {
        //循环传递根节点的id
        if (StringUtils.isBlank(ruleNode.getParentId())){
            ruleNode.setRootId(ruleNode.getId());
        }else {
            ruleNode.setRootId(ruleNode.getRootId());
        }
        //获得当前节点表达式
        String expression = convertToExpression(ruleNode);
        //循环拼接表达式
        if (StringUtils.isNotBlank(expression)) {
            //判断前面是否已经有节点生成表达式
            String parentExpression = ruleNode.getExpression();
            if (StringUtils.isNotBlank(parentExpression)) {
                ruleNode.setExpression(parentExpression + " && " + expression);
            } else {
                ruleNode.setExpression(expression);
            }
        }
        if (CollectionUtils.isEmpty(children)) {
            //子节点为空，则把这一条规则链的表达式保存起来
            //00004 = 规则[{0}]:规则叶子节点[{1}]生成表达式{2}！
            LogUtil.bizLog("00004",ruleNode.getRootId(),ruleNode.getId(),ruleNode.getExpression());
            BoundListOperations<String, String> operations = redisTemplate.boundListOps(RULE_CHAIN_CACHE_KEY_PREFIX + ruleNode.getRootId());
            operations.leftPush(ruleNode.getExpression());
            return;
        }
        children.forEach(node -> {
            // 重新创建节点
            node.setId(null);
            node.setCode(null);
            node.setRootId(ruleNode.getRootId());
            node.setParentId(ruleNode.getId());
            // 设置公共属性
            node.setRuleCategory(ruleNode.getRuleCategory());
            OperateResultWithData<MatchingRule> saveResult = save(node);
            if (saveResult.notSuccessful()) {
                throw new ServiceException("递归保存规则树节点失败！" + saveResult.getMessage());
            }
            saveChildren(saveResult.getData(), node.getChildren());
        });
    }

    private String convertToExpression(MatchingRule ruleNode) {
        ComparisonOperator operator = ruleNode.getComparisonOperator();
        String propertyCode = ruleNode.getPropertyCode();
        String comparisonValue = ruleNode.getComparisonValue();
        DataType dataType = ruleNode.getDataType();
        switch (dataType) {
            case STRING:
                //字符串类型需要在两侧加单引号
                comparisonValue = "'" + comparisonValue + "'";
                break;
            case DATE:
                //日期类型需要转化为yyyy-MM-dd HH:mm:ss:SS 格式 在两侧加单引号
                Date date = DateUtils.parseDate(comparisonValue, DEFAULT_DATE_FORMAT);
                comparisonValue = "'" + DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss:SS") + "'";
                break;
            default:
                break;
        }
        StringBuilder builder = new StringBuilder();
        switch (operator) {
            case EQUAL:
                builder.append(propertyCode).append("==").append(comparisonValue);
                break;
            case NOTEQUAL:
                builder.append(propertyCode).append("!=").append(comparisonValue);
                break;
            case LESS:
                builder.append(propertyCode).append("<").append(comparisonValue);
                break;
            case GREATER:
                builder.append(propertyCode).append(">").append(comparisonValue);
                break;
            case LESS_EQUAL:
                builder.append(propertyCode).append("<=").append(comparisonValue);
                break;
            case GREATER_EQUAL:
                builder.append(propertyCode).append(">=").append(comparisonValue);
                break;
            case CONTAIN:
                builder.append("string.contains(").append(propertyCode).append(",").append(comparisonValue).append(")");
            case MATCH:
                builder.append(propertyCode).append("=~").append(comparisonValue);
                break;
            case COMPARER:
                //MatchRuleComparator('sei-rule','demoHello/matchRuleComparator')
                //comparisonValue中模块名与url路径以:相隔
                String[] splits = StringUtils.split(comparisonValue, MatchingRuleComparator.SPLIT_CHAR);
                if (splits.length != MatchingRuleComparator.SPLIT_LENGTH) {
                    throw new MatchingRuleComparatorException("匹配规则计算接口实现[" + comparisonValue + "]不符合定义,正确定义为[模块名" + MatchingRuleComparator.SPLIT_CHAR + "URL路径]");
                }
                String module = splits[0];
                String url = splits[1];
                if (StringUtils.isBlank(module)) {
                    throw new MatchingRuleComparatorException("匹配规则计算接口实现[" + comparisonValue + "]模块名为空");
                }
                if (StringUtils.isBlank(url)) {
                    throw new MatchingRuleComparatorException("匹配规则计算接口实现[" + comparisonValue + "]URL路径为空");
                }
                builder.append("MatchRuleComparator('").append(module).append("','").append(url).append("')");
                break;
            default:
                break;
        }
        return builder.toString();
    }
}