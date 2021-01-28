package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.rule.entity.RuleComparator;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 比较器定义(RuleComparator)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 16:09:33
 */
@Repository
public interface RuleComparatorDao extends BaseEntityDao<RuleComparator> {
    /**
     * 获取规则业务实体配置的比较器
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 比较器清单
     */
    List<RuleComparator> findByRuleEntityTypeId(String ruleEntityTypeId);

    /**
     * 通过唯一索引获取比较器定义
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @param path 路径
     * @param method 方法
     * @return 比较器定义
     */
    RuleComparator findByRuleEntityTypeIdAndPathAndMethod(String ruleEntityTypeId, String path, String method);
}