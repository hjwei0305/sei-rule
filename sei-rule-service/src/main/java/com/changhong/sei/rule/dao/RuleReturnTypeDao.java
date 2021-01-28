package com.changhong.sei.rule.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.rule.entity.RuleReturnType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 返回结果定义(RuleReturnType)数据库访问类
 *
 * @author sei
 * @since 2021-01-13 15:55:47
 */
@Repository
public interface RuleReturnTypeDao extends BaseEntityDao<RuleReturnType> {
    /**
     * 获取规则业务实体配置的返回实体类型
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 返回实体类型清单
     */
    List<RuleReturnType> findByRuleEntityTypeId(String ruleEntityTypeId);

    /**
     * 通过唯一索引获取返回实体类型
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @param code 代码（全类名）
     * @return 返回实体类型
     */
    RuleReturnType findByRuleEntityTypeIdAndCode(String ruleEntityTypeId, String code);
}