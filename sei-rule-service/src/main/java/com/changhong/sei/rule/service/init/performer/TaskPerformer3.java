package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dao.RuleReturnTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
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
public class TaskPerformer3 extends BasePerformer<RuleReturnType> {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleReturnTypeDao ruleReturnTypeDao;

    /**
     * 设置初始化业务实体名称
     *
     * @return 业务实体名称
     */
    @Override
    protected String getEntityName() {
        return "规则主体的返回结果";
    }

    /**
     * 在子类中设置初始换业务实体清单（执行一次）
     * initEntities = new LinkedList<>();
     * initEntities.add(...);
     */
    @Override
    protected void setInitEntities() {
        initEntities = new LinkedList<>();
        // 获取规则业务实体
        RuleEntityType ebillEntityType = ruleEntityTypeDao.findByCode(EBILL_INVOICE_CHECK);
        if (Objects.nonNull(ebillEntityType)) {
            String entityTypeId = ebillEntityType.getId();
            initEntities.add(new RuleReturnType(entityTypeId, "com.changhong.ebill.dto.rule.CheckResultStatusEntity", "查验结果状态业务实体", "ComboListLocal", "ebill/myBillRule/findAllCheckResultStatus"));
        }
        RuleEntityType somsEntityType = ruleEntityTypeDao.findByCode(SOMS_ALLOT_WORK_STRATEGY);
        if (Objects.nonNull(somsEntityType)) {
            String entityTypeId = somsEntityType.getId();
            initEntities.add(new RuleReturnType(entityTypeId, "com.changhong.soms.dto.AbilityGroupDto", "能力组", "ComboListLocal", "soms-v6/abilityGroup/findAll"));
            initEntities.add(new RuleReturnType(entityTypeId, "com.changhong.soms.dto.AllotWorkPositionDto", "派工岗位", "ComboListLocal", "soms-v6/allotWorkPosition/findAll"));
            initEntities.add(new RuleReturnType(entityTypeId, "com.changhong.soms.dto.task.AllotWorkModeEntity", "派工模式", "ComboListLocal", "soms-v6/workNode/findAllAllotWorkMode"));
        }
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    @Override
    protected boolean alreadyExists(RuleReturnType entity) {
        RuleReturnType returnType = ruleReturnTypeDao.findByRuleEntityTypeIdAndCode(entity.getRuleEntityTypeId(), entity.getCode());
        if (Objects.nonNull(returnType)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 创建一个初始化业务实体
     *
     * @param entity 初始化业务实体
     */
    @Override
    protected void save(RuleReturnType entity) {
        ruleReturnTypeDao.save(entity);
    }
}
