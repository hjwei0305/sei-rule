package com.changhong.sei.rule.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.rule.dao.LogicalExpressionDao;
import com.changhong.sei.rule.dao.NodeReturnResultDao;
import com.changhong.sei.rule.dao.RuleTreeNodeDao;
import com.changhong.sei.rule.dao.RuleTypeDao;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.ruletree.NodeSynthesisExpression;
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

import java.util.*;
import java.util.stream.Collectors;


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
    private AsyncRunUtil asyncRunUtil;
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
        //放在后面是因为需要获取保存后的id
        //保存逻辑表达式
        if (!entity.getTrueNode()) {
            saveLogicalExpression(entity);
        }
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
     *
     * @param node 节点
     */
    private void assembleNodeInfo(RuleTreeNode node) {
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
        ruleChainService.deleteRuleChainCache(rootNodeId);
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
        //异步构建缓存
        asyncRunUtil.runAsync(() -> ruleChainService.buildCache(ruleNode));
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
        if (CollectionUtils.isEmpty(children)) {
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
        }
        expression.setExpressions(synthesisExpressions);
        return expression;
    }
}