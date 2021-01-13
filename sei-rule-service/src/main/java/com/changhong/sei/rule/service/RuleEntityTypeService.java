package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 规则业务实体(RuleEntityType)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-13 14:47:29
 */
@Service("ruleEntityTypeService")
public class RuleEntityTypeService extends BaseEntityService<RuleEntityType> {
    @Autowired
    private RuleEntityTypeDao dao;

    @Override
    protected BaseEntityDao<RuleEntityType> getDao() {
        return dao;
    }

}