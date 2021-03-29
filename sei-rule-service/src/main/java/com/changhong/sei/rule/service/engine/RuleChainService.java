package com.changhong.sei.rule.service.engine;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.rule.dao.RuleReturnTypeDao;
import com.changhong.sei.rule.dao.RuleServiceMethodDao;
import com.changhong.sei.rule.entity.NodeReturnResult;
import com.changhong.sei.rule.entity.RuleReturnType;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.sdk.dto.RuleReturnEntity;
import com.changhong.sei.rule.service.aviator.AviatorExpressionService;
import com.changhong.sei.rule.service.bo.RuleChain;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 实现功能: 规则链业务逻辑实现
 *
 * @author 王锦光 wangjg
 * @version 2021-02-02 13:27
 */
@Service
public class RuleChainService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AviatorExpressionService aviatorExpressionService;
    @Autowired
    private RuleReturnTypeDao ruleReturnTypeDao;
    @Autowired
    private RuleServiceMethodDao ruleServiceMethodDao;

    /**
     * 表达式缓存key
     */
    public static final String RULE_CHAIN_CACHE_KEY_PREFIX = "sei:sei-rule:rule-chains:";

    /**
     * 规则链缓存KEY
     * @param rootNodeId 规则树根节点Id
     */
    public static String getCacheKey(String rootNodeId) {
        return RULE_CHAIN_CACHE_KEY_PREFIX + rootNodeId;
    }

    /**
     * 清除规则链缓存
     *
     * @param rootNodeId 规则树根节点Id
     */
    public void deleteRuleChainCache(String rootNodeId) {
        redisTemplate.delete(getCacheKey(rootNodeId));
    }

    /**
     * 从缓存中获取规则链
     *
     * @param rootNodeId 规则树根节点Id
     * @return 规则链清单
     */
    public List<RuleChain> getRuleChainsFromCache(String rootNodeId) {
        BoundListOperations<String, RuleChain> operations = redisTemplate.boundListOps(getCacheKey(rootNodeId));
        Long size = operations.size();
        if (Objects.isNull(size)) {
            return null;
        }
        return operations.range(0, size);
    }

    /**
     * 根据根节点Id查询对应的规则链表达式列表
     *
     * @param tree 一个完整的规则树
     * @return 规则链表达式列表
     */
    public List<RuleChain> getExpressionByTree(RuleTreeNode tree) {
        String rootNodeId = tree.getId();
        // 先从缓存获取
        List<RuleChain> ruleChains = getRuleChainsFromCache(rootNodeId);
        if (CollectionUtils.isNotEmpty(ruleChains)) {
            return ruleChains;
        }
        //重新建立缓存
        buildRuleChainCache(tree);
        // 再次从缓存获取
        return getRuleChainsFromCache(rootNodeId);
    }

    /**
     * 通过根节点构建规则链缓存
     * @param tree 一个完整的规则树
     */
    public void buildRuleChainCache(RuleTreeNode tree) {
        String rootNodeId = tree.getId();
        buildCache(tree, rootNodeId);
    }

    /**
     * 重新建立缓存(递归)
     *
     * @param ruleNode 树节点
     * @param rootNodeId 根节点Id
     */
    private void buildCache(RuleTreeNode ruleNode, String rootNodeId) {
        //获得当前节点表达式
        String expression = aviatorExpressionService.convertToExpression(ruleNode);
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
        if (ruleNode.getFinished()) {
            //子节点为空，则把这一条规则链的表达式保存起来
            //00004 = 规则[{0}]:规则叶子节点[{1}]生成表达式{2}！
            LogUtil.bizLog(ContextUtil.getMessage("00004", ruleNode.getCode(), ruleNode.getCode(), ruleNode.getExpression()));
            RuleChain ruleChain = constructRuleChain(ruleNode);
            BoundListOperations<String, RuleChain> operations = redisTemplate.boundListOps(getCacheKey(rootNodeId));
            operations.rightPush(ruleChain);
            return;
        }
        List<RuleTreeNode> children = ruleNode.getChildren();
        //对字节的按照rank顺序排序 数据库查询已rank顺序排序 这里不再排序
        // List<RuleTreeNode> rankedChildren = children.stream().sorted(Comparator.comparing(RuleTreeNode::getRank)).collect(Collectors.toList());
        children.forEach(node -> {
            node.setExpression(ruleNode.getExpression());
            buildCache(node, rootNodeId);
        });
    }

    /**
     * 组装规则链
     *
     * @param ruleNode 规则树节点
     * @return
     */
    private RuleChain constructRuleChain(RuleTreeNode ruleNode) {
        RuleChain ruleChain = new RuleChain();
        //表达式
        ruleChain.setExpression(ruleNode.getExpression());
        //节点Id
        ruleChain.setRuleTreeNodeId(ruleNode.getId());
        ruleChain.setRuleTreeNodeName(ruleNode.getName());
        //返回对象
        ruleChain.setReturnConstant(ruleNode.getReturnConstant());
        List<NodeReturnResult> returnResults = ruleNode.getNodeReturnResults();
        List<RuleReturnEntity> returnEntities = new ArrayList<>();
        for (NodeReturnResult result : returnResults) {
            RuleReturnEntity entity = new RuleReturnEntity();
            RuleReturnType ruleReturnType = ruleReturnTypeDao.findOne(result.getRuleReturnTypeId());
            entity.setClassName(ruleReturnType.getCode());
            entity.setId(result.getReturnValueId());
            entity.setName(result.getReturnValueName());
            returnEntities.add(entity);
        }
        ruleChain.setReturnEntities(returnEntities);
        //执行方法
        if (StringUtils.isNotBlank(ruleNode.getRuleServiceMethodId())) {
            RuleServiceMethod method = ruleServiceMethodDao.findOne(ruleNode.getRuleServiceMethodId());
            ruleChain.setRuleServiceMethod(method);
        }
        //是否异步执行
        if (ruleNode.getAsyncExecute()) {
            ruleChain.setAsyncExecute(Boolean.TRUE);
        }
        return ruleChain;
    }
}
