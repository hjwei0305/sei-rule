package com.changhong.sei.rule.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.rule.api.MatchingRuleComparator;
import com.changhong.sei.rule.dao.*;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import com.changhong.sei.rule.entity.*;
import com.changhong.sei.rule.service.exception.MatchingRuleComparatorException;
import com.changhong.sei.serial.sdk.SerialService;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.changhong.sei.util.DateUtils.DEFAULT_DATE_FORMAT;


/**
 * 规则树节点(RuleTreeNode)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-13 16:29:53
 */
@Service("ruleTreeNodeService")
public class RuleTreeNodeService extends BaseTreeService<RuleTreeNode> {

    /**
     * 表达式缓存key
     */
    public static final String RULE_CHAIN_CACHE_KEY_PREFIX = "sei:sei-rule:rule-chains:";
    /**
     * 表达式前缀
     */
    public static final String RULE_CHAIN_PARAM_PREFIX = "param.";
    /**
     * 或表达式
     */
    private static final String OR_EXPRESSION = " || ";

    @Autowired
    private RuleTreeNodeDao dao;
    @Autowired
    private LogicalExpressionDao logicalExpressionDao;
    @Autowired
    private NodeReturnResultDao nodeReturnResultDao;
    @Autowired
    private RuleAttributeDao ruleAttributeDao;
    @Autowired
    private RuleTypeDao ruleTypeDao;
    @Autowired(required = false)
    private SerialService serialService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected BaseTreeDao<RuleTreeNode> getDao() {
        return dao;
    }

    /**
     * 获取规则实体类型的所有根节点
     *
     * @param ruleTypeId 规则类型Id
     * @return 根节点清单
     */
    public List<RuleTreeNode> findRootNodes(String ruleTypeId) {
        return dao.findRootNodes(ruleTypeId, ContextUtil.getTenantCode());
    }

    /**
     * 根据根节点Id查询对应的规则链表达式列表
     *
     * @param rootNodeId 规则类型Id
     * @return 根节点清单
     */
    public List<String> getExpressionByRootNode(String rootNodeId) {
        BoundListOperations<String, String> operations = redisTemplate.boundListOps(RULE_CHAIN_CACHE_KEY_PREFIX + rootNodeId);
        List<String> expressions = operations.range(0, operations.size());
        return expressions;
    }

    /**
     * 更新规则树根节点信息
     *
     * @param ruleTreeRoot 规则树根节点
     * @return 处理结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult updateRootNode(RuleTreeRoot ruleTreeRoot) {
        String nodeId = ruleTreeRoot.getId();
        // 获取规则树节点
        RuleTreeNode ruleTreeNode = dao.findOne(nodeId);
        if (Objects.isNull(ruleTreeNode)) {
            // 规则树节点【{0}】不存在！
            return OperateResult.operationFailure("00024", ruleTreeRoot.getId());
        }
        // 更新可以更新的属性
        ruleTreeNode.setName(ruleTreeRoot.getName());
        ruleTreeNode.setRank(ruleTreeRoot.getRank());
        ruleTreeNode.setEnabled(ruleTreeNode.getEnabled());
        dao.save(ruleTreeNode);
        // 更新规则树根节点信息成功！
        return OperateResult.operationSuccess("00025");
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
            return OperateResult.operationSuccess("00015");
        }
        RuleTreeNode parent = dao.findOne(parentId);
        if (Objects.isNull(parent)) {
            // 检查通过！
            return OperateResult.operationSuccess("00015");
        }
        // 检查通过！
        return OperateResult.operationSuccess("00015");
    }

    /**
     * 保存一个规则树节点
     *
     * @param entity 规则树节点
     * @return 操作结果
     */
    @Override
    @Transactional
    public OperateResultWithData<RuleTreeNode> save(RuleTreeNode entity) {
        // 检查父节点
        OperateResult checkResult = checkParentNode(entity.getParentId());
        if (checkResult.notSuccessful()) {
            return OperateResult.converterWithData(checkResult, entity);
        }
        if (StringUtils.isBlank(entity.getCode())) {
            entity.setCode(serialService.getNumber(RuleTreeNode.class));
        }
        //检查逻辑表达式
        List<LogicalExpression> expressions = entity.getLogicalExpressions();
        if (Objects.isNull(expressions) || expressions.isEmpty()) {
            //规则树节点[{0}]表达式不能为空！
            return OperateResultWithData.operationFailure("00020", entity.getName());
        }
        //检查结果
        if (entity.getFinished()) {
            List<NodeReturnResult> nodeReturnResults = entity.getNodeReturnResults();
            if (Objects.isNull(nodeReturnResults) || nodeReturnResults.isEmpty()) {
                //规则树节点[{0}]已勾结束节点,返回结果不能为空！
                return OperateResultWithData.operationFailure("00021", entity.getName());
            }

        }
        OperateResultWithData<RuleTreeNode> result = super.save(entity);
        if (result.notSuccessful()) {
            return result;
        }
        //放在后面是因为需要获取保存后的id
        //保存逻辑表达式
        saveLogicalExpression(entity);
        //保存规则结果
        if (entity.getFinished()) {
            saveNodeResult(entity);
        }
        return result;
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
        //禁止移动节点!
        return OperateResult.operationFailure("00016");
    }

    /**
     * 通过规则树根节点Id获取规则树
     *
     * @param rootNodeId 根节点Id
     * @return 规则树
     */
    public RuleTreeNode getRuleTree(String rootNodeId) {
        RuleTreeNode root = dao.findOne(rootNodeId);
        if (Objects.isNull(root)) {
            return null;
        }
        assembleNodeInfo(root);
        return findOneTree(root);
    }

    /**
     * 组装节点的其他信息
     * @param node 节点
     */
    private void assembleNodeInfo(RuleTreeNode node) {
        String nodeId = node.getId();
        node.setLogicalExpressions(logicalExpressionDao.findByRuleTreeNodeId(nodeId));
        node.setNodeReturnResults(nodeReturnResultDao.findByRuleTreeNodeId(nodeId));
    }


    /**
     * 获取一颗规则树
     * @param node 起始节点
     * @return 规则树
     */
    private RuleTreeNode findOneTree(RuleTreeNode node) {
        if (Objects.isNull(node)) {
            return null;
        }
        // 递归设置所有子节点
        setChildrenInFindOneTree(node);
        return node;
    }

    /**
     * 递归设置所有子节点
     * @param node 起始节点
     */
    private void setChildrenInFindOneTree(RuleTreeNode node) {
        List<RuleTreeNode> children = getChildren(node.getId());
        if (CollectionUtils.isNotEmpty(children)) {
            children.forEach(child -> {
                assembleNodeInfo(child);
                setChildrenInFindOneTree(child);
            });
        }
        node.setChildren(children);
    }

    /**
     * 获取一个节点的子节点清单（不递归）
     * @param parentId 父节点Id
     * @return 子节点清单
     */
    private List<RuleTreeNode> getChildren(String parentId) {
        return dao.findByParentId(parentId);
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
        RuleTreeNode tree = getTree(rootNodeId);
        if (Objects.isNull(tree)) {
            return;
        }
        //删除规则链缓存
        redisTemplate.delete(RULE_CHAIN_CACHE_KEY_PREFIX + rootNodeId);
        //删除逻辑表达式
        logicalExpressionDao.deleteByRuleTreeRootNodeId(rootNodeId);
        //删除结果
        nodeReturnResultDao.deleteByRuleTreeRootNodeId(rootNodeId);
        // 获取所有树节点清单
        List<RuleTreeNode> rules = unBuildTree(Collections.singletonList(tree));
        // 按层级倒序排列
        Comparator<RuleTreeNode> comparator = Comparator.comparing(RuleTreeNode::getNodeLevel);
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
    public OperateResult saveRuleTree(RuleTreeNode ruleNode) {
        // 如果根节点已经存在，则删除整棵树的所有节点
        if (StringUtils.isNotBlank(ruleNode.getId())) {
            deleteRuleTree(ruleNode.getId());
            // 全部新建
            ruleNode.setId(null);
            ruleNode.setCode(null);
        }
        //查询规则分类是否存在
        RuleType ruleType = ruleTypeDao.findOne(ruleNode.getRuleTypeId());
        if (Objects.isNull(ruleType)) {
            //指定规则类型[{0}]不存在！
            return OperateResult.operationFailure("00023", ruleNode.getRuleTypeId());
        }
        OperateResultWithData<RuleTreeNode> saveResult = save(ruleNode);
        if (saveResult.notSuccessful()) {
            return OperateResultWithData.converterNoneData(saveResult);
        }
        //保存子节点
        saveChildren(saveResult.getData(), ruleNode.getChildren());
        // 业务规则【{0}】保存成功！
        return OperateResult.operationSuccess("00017", ruleNode.getName());
    }

    /**
     * 递归保存业务规则树子节点
     *
     * @param ruleNode 树节点
     * @param children 子节点清单
     */
    private void saveChildren(RuleTreeNode ruleNode, List<RuleTreeNode> children) {
        //循环传递根节点的id
        if (StringUtils.isBlank(ruleNode.getParentId())) {
            ruleNode.setRootId(ruleNode.getId());
        } else {
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
            LogUtil.bizLog("00004", ruleNode.getRootId(), ruleNode.getId(), ruleNode.getExpression());
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
            //循环传递规则分类的id
            node.setRuleTypeId(ruleNode.getRuleTypeId());
            // 设置公共属性
            OperateResultWithData<RuleTreeNode> saveResult = save(node);
            if (saveResult.notSuccessful()) {
                throw new ServiceException("递归保存规则树节点失败！" + saveResult.getMessage());
            }
            saveChildren(saveResult.getData(), node.getChildren());
        });
    }

    /**
     * 保存业务逻辑表达式
     *
     * @param ruleNode 规则树节点
     */
    private void saveLogicalExpression(RuleTreeNode ruleNode) {
        //赋值根节点值
        for (LogicalExpression expression : ruleNode.getLogicalExpressions()) {
            if (StringUtils.isBlank(ruleNode.getRootId())) {
                expression.setRuleTreeRootNodeId(ruleNode.getId());
            } else {
                expression.setRuleTreeRootNodeId(ruleNode.getRootId());
            }
            expression.setRuleTreeNodeId(ruleNode.getId());
            logicalExpressionDao.save(expression);
        }
    }


    /**
     * 保存规则节点结果
     *
     * @param ruleNode 规则树节点
     */
    private void saveNodeResult(RuleTreeNode ruleNode) {
        //保存结果
        //赋值根节点值
        for (NodeReturnResult nodeReturnResult : ruleNode.getNodeReturnResults()) {
            if (StringUtils.isBlank(ruleNode.getRootId())) {
                nodeReturnResult.setRuleTreeRootNodeId(ruleNode.getId());
            } else {
                nodeReturnResult.setRuleTreeRootNodeId(ruleNode.getRootId());
            }
            nodeReturnResult.setRuleTreeNodeId(ruleNode.getId());
            nodeReturnResultDao.save(nodeReturnResult);
        }
    }

    private String convertToExpression(RuleTreeNode ruleNode) {
        //真节点跳过
        if (ruleNode.getTrueNode()) {
            return "";
        }
        //查询逻辑表达式
        List<LogicalExpression> expressions = ruleNode.getLogicalExpressions();
        StringBuilder expression = new StringBuilder("(");
        expressions.forEach(ex -> expression.append(convertToExpression(ex)).append(OR_EXPRESSION));
        //去除最后一个||
        if (expression.toString().endsWith(OR_EXPRESSION)) {
            int expressionLength = expression.toString().length();
            expression.delete(expressionLength - OR_EXPRESSION.length(), expressionLength);
        }
        expression.append(")");
        return expression.toString();
    }

    /**
     * 根据逻辑表达式返回对应的表达式
     *
     * @param expression 逻辑表达式
     * @return 表达式
     */
    private String convertToExpression(LogicalExpression expression) {
        ComparisonOperator operator = expression.getComparisonOperator();
        RuleAttribute ruleAttribute = ruleAttributeDao.findOne(expression.getRuleAttributeId());
        String propertyCode = ruleAttribute.getAttribute();
        String comparisonValue = expression.getComparisonValue();
        RuleAttributeType ruleAttributeType = ruleAttribute.getRuleAttributeType();
        switch (ruleAttributeType) {
            case STRING:
                //字符串类型需要在两侧加单引号
                comparisonValue = "'" + comparisonValue + "'";
                break;
            case DATETIME:
                //日期类型需要转化为yyyy-MM-dd HH:mm:ss:SS 格式 在两侧加单引号
                Date date = DateUtils.parseDate(comparisonValue, DEFAULT_DATE_FORMAT);
                comparisonValue = "'" + DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss:SS") + "'";
                break;
            default:
                break;
        }
        //需要在参数上加上前缀
        propertyCode = RULE_CHAIN_PARAM_PREFIX + propertyCode;
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
                break;
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