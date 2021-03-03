package com.changhong.sei.rule.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.rule.dao.LogicalExpressionDao;
import com.changhong.sei.rule.dao.NodeReturnResultDao;
import com.changhong.sei.rule.dao.RuleTreeNodeDao;
import com.changhong.sei.rule.dao.RuleTypeDao;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.ruletree.NodeSynthesisExpression;
import com.changhong.sei.rule.dto.ruletree.ReferenceRoot;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import com.changhong.sei.rule.dto.ruletree.SynthesisExpression;
import com.changhong.sei.rule.entity.*;
import com.changhong.sei.rule.service.engine.RuleChainService;
import com.changhong.sei.serial.sdk.SerialService;
import com.changhong.sei.util.EnumUtils;
import com.changhong.sei.utils.AsyncRunUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * 规则树节点(RuleTreeNode)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-13 16:29:53
 */
@Service("ruleTreeNodeService")
public class RuleTreeNodeService extends BaseTreeService<RuleTreeNode> {
    @Autowired
    private RuleTreeNodeDao dao;
    @Autowired
    private LogicalExpressionDao logicalExpressionDao;
    @Autowired
    private NodeReturnResultDao nodeReturnResultDao;
    @Autowired
    private RuleTypeDao ruleTypeDao;
    @Autowired(required = false)
    private SerialService serialService;
    @Autowired
    private RuleChainService ruleChainService;

    @Override
    protected BaseTreeDao<RuleTreeNode> getDao() {
        return dao;
    }

    /**
     * 定义通用的严格匹配实体转换器
     */
    private static final ModelMapper strictModelMapper;
    // 初始化静态属性
    static {
        // 初始化转换器
        strictModelMapper = new ModelMapper();
        // 设置为严格匹配
        strictModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * 获取规则实体类型的所有根节点
     *
     * @param ruleTypeId 规则类型Id
     * @return 根节点清单
     */
    public List<RuleTreeNode> findRuleTreeRootNodes(String ruleTypeId) {
        return dao.findRootNodes(ruleTypeId, ContextUtil.getTenantCode());
    }

    /**
     * 获取规则实体类型的所有根节点
     *
     * @param ruleTypeId 规则类型Id
     * @return 根节点清单
     */
    public List<RuleTreeRoot> findRootNodes(String ruleTypeId) {
        List<RuleTreeNode> nodes = findRuleTreeRootNodes(ruleTypeId);
        List<RuleTreeRoot> roots = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(nodes)) {
            nodes.forEach(node -> {
                RuleTreeRoot root = strictModelMapper.map(node, RuleTreeRoot.class);
                if (Objects.nonNull(root)) {
                    roots.add(root);
                }
            });
        }
        return roots;
    }

    /**
     * 通过一个子节点Id获取根节点信息
     * @param nodeId 节点Id
     * @return 根节点信息
     */
    public RuleTreeRoot findRootByNodeId(String nodeId) {
        RuleTreeNode node = dao.findOne(nodeId);
        if (Objects.isNull(node)) {
            return null;
        }
        return strictModelMapper.map(getRootNode(node), RuleTreeRoot.class);
    }

    /**
     * 创建规则树根节点信息
     *
     * @param ruleTreeRoot 规则树根节点
     * @return 处理结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult createRootNode(RuleTreeRoot ruleTreeRoot) {
        // 构造一个规则树根节点
        RuleTreeNode ruleTreeNode = new RuleTreeNode();
        // 给号
        if (StringUtils.isBlank(ruleTreeNode.getCode())) {
            ruleTreeNode.setCode(serialService.getNumber(RuleTreeNode.class));
        }
        RuleType ruleType = ruleTypeDao.findOne(ruleTreeRoot.getRuleTypeId());
        if (Objects.isNull(ruleType)) {
            // 指定规则类型不存在！【{0}】
            return OperateResult.operationFailure("00027", ruleTreeRoot.getRuleTypeId());
        }
        ruleTreeNode.setNodeLevel(0);
        ruleTreeNode.setRuleTypeId(ruleTreeRoot.getRuleTypeId());
        ruleTreeNode.setName(ruleTreeRoot.getName());
        ruleTreeNode.setRank(ruleTreeRoot.getRank());
        ruleTreeNode.setEnabled(Boolean.FALSE);
        dao.save(ruleTreeNode);
        // 规则树根节点信息创建成功！
        return OperateResult.operationSuccess("00037");
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
        ruleTreeNode.setEnabled(ruleTreeRoot.getEnabled());
        dao.save(ruleTreeNode);
        // 规则树根节点信息更新成功！
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
        //不是真节点 需要填表达式
        if (!entity.getTrueNode()) {
            if (Objects.isNull(expressions) || expressions.isEmpty()) {
                //规则树节点[{0}]表达式不能为空！
                return OperateResultWithData.operationFailure("00020", entity.getName());
            }
        }
        // 检查如果是规则结束，不能存在子节点
        if (entity.getFinished() && !entity.isNew()) {
            List<RuleTreeNode> children = getChildren(entity.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                // [{0}]存在子节点，不能作为规则结束节点！
                return OperateResultWithData.operationFailure("00021", entity.getName());
            }
        }
        OperateResultWithData<RuleTreeNode> result = super.save(entity);
        if (result.notSuccessful()) {
            return result;
        }
        //保存逻辑表达式
        RuleTreeNode treeNode = result.getData();
        if (!entity.getTrueNode()) {
            saveLogicalExpression(treeNode);
        }
        // 保存规则返回结果集
        if (entity.getFinished()) {
            saveNodeResult(treeNode);
        } else {
            // 清除返回结果集
            nodeReturnResultDao.deleteByRuleTreeNodeId(treeNode.getId());
        }
        // 获取根节点
        RuleTreeNode rootNode = getRootNode(treeNode);
        // 删除规则链缓存
        ruleChainService.deleteRuleChainCache(rootNode.getId());
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
     *
     * @param node 节点
     */
    public void assembleNodeInfo(RuleTreeNode node) {
        String nodeId = node.getId();
        node.setLogicalExpressions(logicalExpressionDao.findByRuleTreeNodeId(nodeId));
        node.setNodeReturnResults(nodeReturnResultDao.findByRuleTreeNodeId(nodeId));
    }


    /**
     * 获取一颗规则树
     *
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
     *
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
     *
     * @param parentId 父节点Id
     * @return 子节点清单
     */
    private List<RuleTreeNode> getChildren(String parentId) {
        return dao.findByParentIdOrderByRankAsc(parentId);
    }

    /**
     * 保存业务逻辑表达式
     *
     * @param ruleNode 规则树节点
     */
    private void saveLogicalExpression(RuleTreeNode ruleNode) {
        //赋值根节点值
        ruleNode.getLogicalExpressions().forEach(expression -> {
            expression.setRuleTreeNodeId(ruleNode.getId());
            logicalExpressionDao.save(expression);
        });
    }


    /**
     * 保存规则节点结果
     *
     * @param ruleNode 规则树节点
     */
    private void saveNodeResult(RuleTreeNode ruleNode) {
        // 获取已经存在的返回结果Id清单
        List<String> existIds = nodeReturnResultDao.findIdsByRuleTreeNodeId(ruleNode.getId());
        List<NodeReturnResult> nodeReturnResults = ruleNode.getNodeReturnResults();
        // 删除已经存在的返回结果
        if (CollectionUtils.isNotEmpty(existIds)) {
            existIds.forEach(id -> {
                if (CollectionUtils.isNotEmpty(nodeReturnResults)) {
                    Optional<NodeReturnResult> findResult = nodeReturnResults.stream().filter(r -> StringUtils.equals(r.getId(), id)).findAny();
                    if (!findResult.isPresent()) {
                        nodeReturnResultDao.delete(id);
                    }
                } else {
                    nodeReturnResultDao.delete(id);
                }
            });
        }
        // 保存输入的结果
        if (CollectionUtils.isNotEmpty(nodeReturnResults)) {
            nodeReturnResults.forEach(nodeReturnResult -> {
                nodeReturnResult.setRuleTreeNodeId(ruleNode.getId());
                nodeReturnResultDao.save(nodeReturnResult);
            });
        }
    }

    /**
     * 删除业务规则树节点(级联删除子节点)
     *
     * @param nodeId 节点Id
     * @return 处理结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult deleteNode(String nodeId) {
        // 获取当前节点
        RuleTreeNode node = dao.findOne(nodeId);
        if (Objects.isNull(node)) {
            // 规则树节点【{0}】不存在！
            return OperateResult.operationFailure("00024", nodeId);
        }
        // 删除一个节点（递归）
        deleteOneNode(node);
        // 获取根节点
        RuleTreeNode rootNode = getRootNode(node);
        // 删除规则链缓存
        ruleChainService.deleteRuleChainCache(rootNode.getId());
        // 规则树节点【{0}】删除成功！
        return OperateResult.operationSuccess("00038", node.getName());
    }

    /**
     * 删除一个节点（递归）
     * @param node 树节点
     */
    private void deleteOneNode(RuleTreeNode node) {
        String nodeId = node.getId();
        // 删除逻辑表达式
        List<LogicalExpression> logicalExpressions = logicalExpressionDao.findByRuleTreeNodeId(nodeId);
        if (CollectionUtils.isNotEmpty(logicalExpressions)) {
            logicalExpressions.forEach(logicalExpression -> logicalExpressionDao.delete(logicalExpression));
        }
        // 删除返回实体结果
        List<NodeReturnResult> nodeReturnResults = nodeReturnResultDao.findByRuleTreeNodeId(nodeId);
        if (CollectionUtils.isNotEmpty(nodeReturnResults)) {
            nodeReturnResults.forEach(nodeReturnResult -> nodeReturnResultDao.delete(nodeReturnResult));
        }
        // 获取子节点清单
        List<RuleTreeNode> children = getChildren(nodeId);
        if (CollectionUtils.isNotEmpty(children)) {
            // 递归删除
            children.forEach(this::deleteOneNode);
        }
        // 删除本节点
        dao.delete(node);
    }

    /**
     * 通过一个节点获取其根节点
     * @param node 规则节点
     * @return 根节点
     */
    public RuleTreeNode getRootNode(RuleTreeNode node) {
        if (Objects.isNull(node) || StringUtils.isBlank(node.getParentId())) {
            return node;
        }
        // 获取父节点清单
        List<RuleTreeNode> parents = getParentNodes(node, false);
        for (RuleTreeNode ruleTreeNode: parents) {
            if (StringUtils.isBlank(ruleTreeNode.getParentId())) {
                return ruleTreeNode;
            }
        }
        throw new ServiceException("通过一个节点获取其根节点异常，未找到根节点！");
    }

    /**
     * 获取一个节点的综合表达式清单
     *
     * @param nodeId 节点Id
     * @return 综合表达式清单
     */
    public List<NodeSynthesisExpression> getNodeSynthesisExpressions(String nodeId) {
        // 获取所有父节点清单
        List<RuleTreeNode> parents = getParentNodes(nodeId, Boolean.TRUE);
        if (CollectionUtils.isEmpty(parents)) {
            return new LinkedList<>();
        }
        List<NodeSynthesisExpression> synthesisExpressions = new LinkedList<>();
        // 倒序循环生成综合表达式
        for (int i = parents.size() - 1; i >=0 ; i--) {
            RuleTreeNode node = parents.get(i);
            NodeSynthesisExpression nodeSynthesisExpression = generateExpression(node);
            if (CollectionUtils.isNotEmpty(nodeSynthesisExpression.getExpressions())) {
                synthesisExpressions.add(generateExpression(node));
            }
        }
        return synthesisExpressions;
    }

    /**
     * 生成综合表达式
     * @param node 规则树节点
     * @return 综合表达式
     */
    private NodeSynthesisExpression generateExpression(RuleTreeNode node) {
        NodeSynthesisExpression expression = new NodeSynthesisExpression();
        expression.setId(node.getId());
        expression.setName(node.getName());
        expression.setNodeLevel(node.getNodeLevel());
        List<SynthesisExpression> synthesisExpressions = new LinkedList<>();
        List<LogicalExpression> logicalExpressions = logicalExpressionDao.findByRuleTreeNodeId(node.getId());
        if (CollectionUtils.isNotEmpty(logicalExpressions)) {
            logicalExpressions.forEach( logicalExpression -> {
                SynthesisExpression synthesisExpression = new SynthesisExpression();
                RuleAttribute ruleAttribute = logicalExpression.getRuleAttribute();
                // 比较器没有属性名
                if (Objects.nonNull(ruleAttribute)
                        && logicalExpression.getComparisonOperator() != ComparisonOperator.COMPARER) {
                    synthesisExpression.setRuleAttributeName(ruleAttribute.getName());
                }
                synthesisExpression.setComparisonName(EnumUtils.getEnumItemRemark(ComparisonOperator.class, logicalExpression.getComparisonOperator()));
                String value = StringUtils.trim(logicalExpression.getComparisonValue());
                if (StringUtils.isNotBlank(logicalExpression.getDisplayValue())) {
                    value = logicalExpression.getDisplayValue();
                }
                synthesisExpression.setComparisonValue(value);
                synthesisExpressions.add(synthesisExpression);
            });
        } else if (node.getTrueNode()) {
            // 处理真节点
            SynthesisExpression synthesisExpression = new SynthesisExpression();
            synthesisExpression.setRuleAttributeName("始终");
            synthesisExpression.setComparisonName(EnumUtils.getEnumItemRemark(ComparisonOperator.class, ComparisonOperator.EQUAL));
            synthesisExpression.setComparisonValue("真");
            synthesisExpressions.add(synthesisExpression);
        }
        expression.setExpressions(synthesisExpressions);
        return expression;
    }

    /**
     * 参考创建一个规则树
     *
     * @param referenceRoot 参考创建根节点信息
     * @return 处理结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult referenceCreate(ReferenceRoot referenceRoot) {
        // 获取根节点
        RuleTreeNode referenceNode = dao.findOne(referenceRoot.getReferenceRootId());
        if (Objects.isNull(referenceNode)) {
            // 参考的规则树根节点不存在【{0}】！
            return OperateResult.operationFailure("00039", referenceRoot.getReferenceRootId());
        }
        RuleTreeNode rootNode = cloneNode(referenceNode, null);
        rootNode.setName(referenceRoot.getName());
        rootNode.setRank(referenceRoot.getRank());
        rootNode.setEnabled(Boolean.FALSE);
        OperateResultWithData<RuleTreeNode> rootSaveResult = save(rootNode);
        if (rootSaveResult.notSuccessful()) {
            return OperateResultWithData.converterNoneData(rootSaveResult);
        }
        // 循环递归复制
        referenceCreateChildren(rootSaveResult.getData(), referenceNode.getId());
        // 规则树【{0}】参考创建成功！
        return OperateResult.operationSuccess("00040", referenceRoot.getName());
    }

    /**
     * 循环递归复制所有子节点
     * @param parent 已经创建成功的父节点
     * @param referenceParentId 参考的父节点
     */
    private void referenceCreateChildren(RuleTreeNode parent, String referenceParentId) {
        List<RuleTreeNode> referenceChildren = getChildren(referenceParentId);
        if (CollectionUtils.isEmpty(referenceChildren)) {
            return;
        }
        referenceChildren.forEach(referenceNode -> {
            RuleTreeNode rootNode = cloneNode(referenceNode, parent.getId());
            OperateResultWithData<RuleTreeNode> nodeSaveResult = save(rootNode);
            if (nodeSaveResult.notSuccessful()) {
                throw new ServiceException(nodeSaveResult.getMessage());
            }
            // 递归
            referenceCreateChildren(nodeSaveResult.getData(), referenceNode.getId());
        });
    }

    /**
     * 克隆一个树节点
     * @param referenceNode 参考的节点
     * @param parentId 父节点Id
     * @return 克隆的新节点
     */
    private RuleTreeNode cloneNode(RuleTreeNode referenceNode, String parentId) {
        RuleTreeNode node = JsonUtils.cloneByJson(referenceNode);
        // 初始化审计信息
        initAuditableEntity(node);
        node.setCode(serialService.getNumber(RuleTreeNode.class));
        node.setCodePath(null);
        node.setNamePath(null);
        node.setNodeLevel(null);
        node.setParentId(parentId);
        // 复制规则表达式
        List<LogicalExpression> referenceExpressions = logicalExpressionDao.findByRuleTreeNodeId(referenceNode.getId());
        List<LogicalExpression> logicalExpressions = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(referenceExpressions)) {
            referenceExpressions.forEach(reference -> {
                LogicalExpression expression = JsonUtils.cloneByJson(reference);
                initAuditableEntity(expression);
                expression.setRuleTreeNodeId(null);
                logicalExpressions.add(expression);
            });
        }
        node.setLogicalExpressions(logicalExpressions);
        // 复制规则返回结果
        List<NodeReturnResult> referenceResults = nodeReturnResultDao.findByRuleTreeNodeId(referenceNode.getId());
        List<NodeReturnResult> nodeReturnResults = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(referenceResults)) {
            referenceResults.forEach(reference -> {
                NodeReturnResult returnResult = JsonUtils.cloneByJson(reference);
                initAuditableEntity(returnResult);
                returnResult.setRuleTreeNodeId(null);
                nodeReturnResults.add(returnResult);
            });
        }
        node.setNodeReturnResults(nodeReturnResults);
        return node;
    }

    /**
     * 初始化审计信息
     * @param entity 业务实体
     */
    private void initAuditableEntity(BaseAuditableEntity entity) {
        entity.setId(null);
        entity.setCreatedDate(null);
        entity.setCreatorId(null);
        entity.setCreatorAccount(null);
        entity.setCreatorName(null);
        entity.setLastEditedDate(null);
        entity.setLastEditorId(null);
        entity.setLastEditorAccount(null);
        entity.setLastEditorName(null);
    }
}