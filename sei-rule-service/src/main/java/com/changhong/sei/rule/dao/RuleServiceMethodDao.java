package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.rule.entity.RuleReturnType;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 服务方法定义(RuleServiceMethod)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 16:05:02
 */
@Repository
public interface RuleServiceMethodDao extends BaseEntityDao<RuleServiceMethod> {
    /**
     * 获取规则业务实体配置的服务方法
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 服务方法清单
     */
    List<RuleServiceMethod> findByRuleEntityTypeId(String ruleEntityTypeId);
}