package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dao.RuleTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.EBILL_INVOICE_CHECK;
import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.SOMS_ALLOT_WORK_STRATEGY;

/**
 * 实现功能: 初始化必要的规则定义
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer5 extends BasePerformer {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleTypeDao ruleTypeDao;
    /**
     * 定义初始化业务实体
     */
    private static final List<RuleType> ebillInvoiceRuleTypes;
    private static final List<RuleType> somsAllotRuleTypes;

    static {
        ebillInvoiceRuleTypes = new LinkedList<>();
        ebillInvoiceRuleTypes.add(new RuleType("ebill-invoice_check-hgxjcgz", "合规性检查规则", "在我的票夹导入我的票据后，系统会执行票据的合规性检查，并在我的票据上保存检查结果。"));

        somsAllotRuleTypes = new LinkedList<>();
        somsAllotRuleTypes.add(new RuleType("soms-allotworkstrategy-fwddpgcl", "服务订单派工规则", "在共享服务订单生成工作池任务时，SOMS调用此规则来确定工作池任务的派工策略。"));
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    private boolean checkExists(RuleType entity) {
        RuleType ruleType = ruleTypeDao.findByCodeAndTenantCode(entity.getCode(), ContextUtil.getTenantCode());
        if (Objects.nonNull(ruleType)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 执行任务
     *
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResult performTask() {
        // 获取规则业务实体
        RuleEntityType ebillEntityType = ruleEntityTypeDao.findByCode(EBILL_INVOICE_CHECK);
        if (Objects.nonNull(ebillEntityType)) {
            ebillInvoiceRuleTypes.forEach( entity -> {
                // 设置规则业务实体Id
                entity.setRuleEntityTypeId(ebillEntityType.getId());
                // 判断是否已经存在
                if (!checkExists(entity)) {
                    ruleTypeDao.save(entity);
                }
            });
        }
        RuleEntityType somsEntityType = ruleEntityTypeDao.findByCode(SOMS_ALLOT_WORK_STRATEGY);
        if (Objects.nonNull(somsEntityType)) {
            somsAllotRuleTypes.forEach( entity -> {
                // 设置规则业务实体Id
                entity.setRuleEntityTypeId(somsEntityType.getId());
                // 判断是否已经存在
                if (!checkExists(entity)) {
                    ruleTypeDao.save(entity);
                }
            });
        }
        // 必要的规则定义初始化完毕！
        return OperateResult.operationSuccess("00053");
    }
}
