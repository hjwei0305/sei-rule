package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.LogicalExpressionDao;
import com.changhong.sei.rule.dao.RuleAttributeDao;
import com.changhong.sei.rule.dto.engine.CanUseOperator;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.service.utils.CanUseOperatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


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
    @Autowired
    private LogicalExpressionDao logicalExpressionDao;

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

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 规则属性定义Id
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查规则树节点是否使用了规则属性
        if (logicalExpressionDao.isExistsByProperty("ruleAttributeId", id)) {
            // 规则树节点已经使用了规则属性，禁止删除！
            return OperateResult.operationFailure("00012");
        }
        return super.preDelete(id);
    }

    /**
     * 通过规则属性获取可使用的运算符
     *
     * @param ruleAttributeId 规则属性Id
     * @return 可使用的运算符
     */
    public List<CanUseOperator> getCanUseOperators(String ruleAttributeId) {
        // 获取股则属性
        RuleAttribute attribute = dao.findOne(ruleAttributeId);
        if (Objects.isNull(attribute)) {
            return new LinkedList<>();
        }
        return CanUseOperatorUtil.getOperators(attribute.getRuleAttributeType());
    }
}