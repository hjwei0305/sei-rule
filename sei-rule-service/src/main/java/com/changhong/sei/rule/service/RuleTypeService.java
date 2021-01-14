package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.RuleTreeNodeDao;
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
    @Autowired
    private RuleTreeNodeDao ruleTreeNodeDao;

    @Override
    protected BaseEntityDao<RuleType> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 规则类型Id
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查规则类型是否配置了规则树
        if (ruleTreeNodeDao.isExistsByProperty("ruleTypeId", id)) {
            // 规则类型已经配置了规则树，禁止删除！
            return OperateResult.operationFailure("00014");
        }
        return super.preDelete(id);
    }
}