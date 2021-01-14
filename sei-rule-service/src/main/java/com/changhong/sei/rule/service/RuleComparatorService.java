package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.LogicalExpressionDao;
import com.changhong.sei.rule.dao.RuleComparatorDao;
import com.changhong.sei.rule.entity.RuleComparator;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 比较器定义(RuleComparator)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-14 09:19:15
 */
@Service("ruleComparatorService")
public class RuleComparatorService extends BaseEntityService<RuleComparator> {
    @Autowired
    private RuleComparatorDao dao;
    @Autowired
    private LogicalExpressionDao logicalExpressionDao;

    @Override
    protected BaseEntityDao<RuleComparator> getDao() {
        return dao;
    }

    /**
     * 获取规则业务实体配置的比较器清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 比较器清单
     */
    public List<RuleComparator> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return dao.findByRuleEntityTypeId(ruleEntityTypeId);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 比较器定义Id
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查规则树节点是否使用了比较器
        if (logicalExpressionDao.isExistsByProperty("comparisonValue", id)) {
            // 规则树节点已经使用了比较器，禁止删除！
            return OperateResult.operationFailure("00013");
        }
        return super.preDelete(id);
    }
}