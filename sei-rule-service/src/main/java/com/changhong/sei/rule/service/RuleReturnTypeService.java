package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.dao.RuleReturnTypeDao;
import com.changhong.sei.rule.entity.RuleReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 返回结果定义(RuleReturnType)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-14 08:56:06
 */
@Service("ruleReturnTypeService")
public class RuleReturnTypeService extends BaseEntityService<RuleReturnType> {
    @Autowired
    private RuleReturnTypeDao dao;

    @Override
    protected BaseEntityDao<RuleReturnType> getDao() {
        return dao;
    }

    /**
     * 获取规则业务实体配置的属性清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 属性清单
     */
    public List<RuleReturnType> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return dao.findByRuleEntityTypeId(ruleEntityTypeId);
    }
}