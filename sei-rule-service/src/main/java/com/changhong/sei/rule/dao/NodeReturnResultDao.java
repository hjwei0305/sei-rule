package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.rule.entity.LogicalExpression;
import com.changhong.sei.rule.entity.NodeReturnResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规则树节点返回结果(NodeReturnResult)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 17:18:45
 */
@Repository
public interface NodeReturnResultDao extends BaseEntityDao<NodeReturnResult> {
    /**
     * 获取规则树节点配置的返回结果
     * @param ruleTreeNodeId 规则树节点Id
     * @return 返回结果清单
     */
    List<NodeReturnResult> findByRuleTreeNodeId(String ruleTreeNodeId);

    /**
     * 根据规则树根节点id删除所有返回结果
     * @param ruleTreeRootNodeId 规则树根节点id
     */
    void deleteByRuleTreeRootNodeId(String ruleTreeRootNodeId);
}