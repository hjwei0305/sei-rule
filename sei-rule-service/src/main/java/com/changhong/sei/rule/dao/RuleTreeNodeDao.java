package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.rule.entity.RuleTreeNode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规则树节点(RuleTreeNode)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 16:29:52
 */
@Repository
public interface RuleTreeNodeDao extends BaseTreeDao<RuleTreeNode> {
    /**
     * 获取规则实体类型的所有根节点
     * @param ruleTypeId 规则类型Id
     * @param tenantCode 租户代码
     * @return 根节点清单
     */
    @Query("select node from RuleTreeNode node where node.ruleTypeId=?1 and node.tenantCode=?2 and node.parentId is null order by node.rank asc , node.createdDate desc ")
    List<RuleTreeNode> findRootNodes(String ruleTypeId, String tenantCode);

    /**
     * 获取子节点清单(根据rank倒叙排序)
     *
     * @param parentId 父节点Id
     * @return 子节点清单
     */
    List<RuleTreeNode> findByParentIdOrderByRankAsc(String parentId);
}