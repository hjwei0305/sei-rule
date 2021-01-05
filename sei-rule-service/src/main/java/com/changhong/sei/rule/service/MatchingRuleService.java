package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.rule.dao.MatchingRuleDao;
import com.changhong.sei.rule.dto.enums.RuleCategory;
import com.changhong.sei.rule.entity.MatchingRule;
import com.changhong.sei.serial.sdk.SerialService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 业务匹配规则(MatchingRule)业务逻辑实现类
 *
 * @author sei
 * @since 2020-08-14 09:22:20
 */
@Service("matchingRuleService")
public class MatchingRuleService extends BaseTreeService<MatchingRule> {
    @Autowired
    private MatchingRuleDao dao;
    @Autowired
    private SerialService serialService;

    @Override
    protected BaseTreeDao<MatchingRule> getDao() {
        return dao;
    }

    /**
     * 检查菜单父节点
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
        if (StringUtils.isNotBlank(parent.getItemAccountTypeId())
                || parent.getUnrecognize()) {
            // 规则节点【{0}】已经配置了规则结果，禁止作为父节点！
            return OperateResult.operationFailure("00149", parent.getName());
        }
        // 检查通过！
        return OperateResult.operationSuccess("00001");
    }
    /**
     * 保存一个规则树节点
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
     * @param ruleNode 树节点
     * @param children 子节点清单
     */
    private void saveChildren(MatchingRule ruleNode, List<MatchingRule> children) {
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        children.forEach(node -> {
            // 重新创建节点
            node.setId(null);
            node.setCode(null);
            node.setParentId(ruleNode.getId());
            // 设置公共属性
            node.setRuleCategory(ruleNode.getRuleCategory());
            OperateResultWithData<MatchingRule> saveResult = save(node);
            if (saveResult.notSuccessful()) {
                throw new ServiceException("递归保存规则树节点失败！"+saveResult.getMessage());
            }
            saveChildren(saveResult.getData(), node.getChildren());
        });
    }
}