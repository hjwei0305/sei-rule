package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.RuleAttributeDao;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dao.RuleReturnTypeDao;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.EBILL_INVOICE_CHECK;
import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.SOMS_ALLOT_WORK_STRATEGY;

/**
 * 实现功能: 初始化规则主体返回结果配置
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer3 extends BasePerformer {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleReturnTypeDao ruleReturnTypeDao;
    /**
     * 定义初始化业务实体
     */
    private static final List<RuleReturnType> ebillInvoiceReturnTypes;
    private static final List<RuleReturnType> somsAllotReturnTypes;

    static {
        ebillInvoiceReturnTypes = new LinkedList<>();
        ebillInvoiceReturnTypes.add(new RuleReturnType("com.changhong.ebill.dto.rule.CheckResultStatusEntity", "查验结果状态业务实体", "ComboListLocal", "ebill/myBillRule/findAllCheckResultStatus"));

        somsAllotReturnTypes = new LinkedList<>();
        somsAllotReturnTypes.add(new RuleReturnType("com.changhong.soms.dto.AbilityGroupDto", "能力组", "ComboListLocal", "soms-v6/abilityGroup/findAll"));
        somsAllotReturnTypes.add(new RuleReturnType("com.changhong.soms.dto.AllotWorkPositionDto", "派工岗位", "ComboListLocal", "soms-v6/allotWorkPosition/findAll"));
        somsAllotReturnTypes.add(new RuleReturnType("com.changhong.soms.dto.task.AllotWorkModeEntity", "派工模式", "ComboListLocal", "soms-v6/workNode/findAllAllotWorkMode"));
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    private boolean checkExists(RuleReturnType entity) {
        RuleReturnType returnType = ruleReturnTypeDao.findByRuleEntityTypeIdAndCode(entity.getRuleEntityTypeId(), entity.getCode());
        if (Objects.nonNull(returnType)) {
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
            ebillInvoiceReturnTypes.forEach( entity -> {
                // 设置规则业务实体Id
                entity.setRuleEntityTypeId(ebillEntityType.getId());
                // 判断是否已经存在
                if (!checkExists(entity)) {
                    ruleReturnTypeDao.save(entity);
                }
            });
        }
        RuleEntityType somsEntityType = ruleEntityTypeDao.findByCode(SOMS_ALLOT_WORK_STRATEGY);
        if (Objects.nonNull(somsEntityType)) {
            somsAllotReturnTypes.forEach( entity -> {
                // 设置规则业务实体Id
                entity.setRuleEntityTypeId(somsEntityType.getId());
                // 判断是否已经存在
                if (!checkExists(entity)) {
                    ruleReturnTypeDao.save(entity);
                }
            });
        }
        // 规则主体返回结果初始化完毕！
        return OperateResult.operationSuccess("00051");
    }
}
