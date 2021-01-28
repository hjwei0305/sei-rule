package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.rule.entity.RuleAttribute;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规则属性定义(RuleAttribute)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 15:36:44
 */
@Repository
public interface RuleAttributeDao extends BaseEntityDao<RuleAttribute> {
    /**
     * 获取规则业务实体配置的属性清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 属性清单
     */
    List<RuleAttribute> findByRuleEntityTypeId(String ruleEntityTypeId);

    /**
     * 通过唯一索引获取规则属性
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @param attribute 属性名
     * @return 规则属性
     */
    RuleAttribute findByRuleEntityTypeIdAndAttribute(String ruleEntityTypeId, String attribute);
}