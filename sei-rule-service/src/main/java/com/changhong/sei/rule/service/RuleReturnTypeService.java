package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.NodeReturnResultDao;
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
    @Autowired
    private NodeReturnResultDao nodeReturnResultDao;

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

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 返回结果类型Id
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查规则树节点是否配置了返回结果
        if (nodeReturnResultDao.isExistsByProperty("ruleReturnTypeId", id)) {
            // 规则树节点已经配置了返回结果类型，禁止删除！
            return OperateResult.operationFailure("00010");
        }
        return super.preDelete(id);
    }
}