package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import com.changhong.sei.rule.entity.RuleType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规则类型(RuleType)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 16:15:11
 */
@Repository
public interface RuleTypeDao extends BaseEntityDao<RuleType> {
    /**
     * 获取规则业务实体配置的规则类型
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @param tenantCode 租户代码
     * @return 规则类型清单
     */
    List<RuleType> findByRuleEntityTypeIdAndTenantCode(String ruleEntityTypeId, String tenantCode);

    /**
     * 根据代码获取指定规则类型
     * @param code 代码
     * @param tenantCode 租户代码
     * @return 规则类型
     */
    RuleType findByCodeAndTenantCode(String code, String tenantCode);
}