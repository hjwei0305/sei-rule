package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.dao.RuleAttributeDao;
import com.changhong.sei.rule.entity.RuleAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 规则属性定义(RuleAttribute)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-14 08:46:23
 */
@Service("ruleAttributeService")
public class RuleAttributeService extends BaseEntityService<RuleAttribute> {
    @Autowired
    private RuleAttributeDao dao;

    @Override
    protected BaseEntityDao<RuleAttribute> getDao() {
        return dao;
    }

    /**
     * 获取规则业务实体配置的属性清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 属性清单
     */
    public List<RuleAttribute> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return dao.findByRuleEntityTypeId(ruleEntityTypeId);
    }
}