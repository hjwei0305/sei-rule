package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.rule.dao.*;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleReturnType;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import com.changhong.sei.rule.entity.RuleType;
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
    @Autowired
    private RuleAttributeDao ruleAttributeDao;
    @Autowired
    private RuleReturnTypeDao ruleReturnTypeDao;
    @Autowired
    private RuleServiceMethodDao ruleServiceMethodDao;
    @Autowired
    private RuleComparatorDao ruleComparatorDao;
    @Autowired
    private RuleTypeDao ruleTypeDao;

    @Override
    protected BaseEntityDao<RuleEntityType> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 业务实体类型Id
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查是否配置了规则属性
        if (ruleAttributeDao.isExistsByProperty("ruleEntityTypeId", id)) {
            // 业务实体类型已经配置了规则属性，禁止删除！
            return OperateResult.operationFailure("00005");
        }
        // 检查是否配置了返回结果类型
        if (ruleReturnTypeDao.isExistsByProperty("ruleEntityTypeId", id)) {
            // 业务实体类型已经配置了返回结果类型，禁止删除！
            return OperateResult.operationFailure("00006");
        }
        // 检查是否配置了服务方法
        if (ruleServiceMethodDao.isExistsByProperty("ruleEntityTypeId", id)) {
            // 业务实体类型已经配置了服务方法，禁止删除！
            return OperateResult.operationFailure("00007");
        }
        // 检查是否配置了比较器
        if (ruleComparatorDao.isExistsByProperty("ruleEntityTypeId", id)) {
            // 业务实体类型已经配置了规则比较器，禁止删除！
            return OperateResult.operationFailure("00008");
        }
        // 检查是否配置了规则类型
        if (ruleTypeDao.isExistsByProperty("ruleEntityTypeId", id)) {
            // 业务实体类型已经配置了规则类型，禁止删除！
            return OperateResult.operationFailure("00009");
        }
        return super.preDelete(id);
    }
}