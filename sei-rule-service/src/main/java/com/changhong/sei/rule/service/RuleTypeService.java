package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.dao.RuleTypeDao;
import com.changhong.sei.rule.entity.RuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 规则类型(RuleType)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-13 16:15:11
 */
@Service("ruleTypeService")
public class RuleTypeService extends BaseEntityService<RuleType> {
    @Autowired
    private RuleTypeDao dao;

    @Override
    protected BaseEntityDao<RuleType> getDao() {
        return dao;
    }

}