package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.rule.entity.LogicalExpression;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规则树逻辑表达式(LogicalExpression)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 17:04:39
 */
@Repository
public interface LogicalExpressionDao extends BaseEntityDao<LogicalExpression> {
    /**
     * 获取规则树节点配置的逻辑表达式
     * @param ruleTreeNodeId 规则树节点Id
     * @return 逻辑表达式清单
     */
    List<LogicalExpression> findByRuleTreeNodeId(String ruleTreeNodeId);
}