package com.changhong.sei.rule.dao;


import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.rule.dto.enums.RuleCategory;
import com.changhong.sei.rule.entity.MatchingRule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 业务匹配规则(MatchingRule)数据库访问类
 *
 * @author sei
 * @since 2020-08-14 09:22:20
 */
@Repository
public interface MatchingRuleDao extends BaseTreeDao<MatchingRule> {
    /**
     * 获取指定规则分类的所有根节点(未冻结的)
     * @param category 规则分类
     * @return 根节点清单
     */
    @Query("select rule from MatchingRule rule where rule.parentId is null and rule.frozen=false and rule.ruleCategory=?1 order by rule.rank desc ")
    List<MatchingRule> findRootNode(RuleCategory category);

}