package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dao.RuleTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.*;

/**
 * 实现功能: 初始化必要的规则定义
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer5 extends BasePerformer<RuleType> {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleTypeDao ruleTypeDao;

    /**
     * 设置初始化业务实体名称
     *
     * @return 业务实体名称
     */
    @Override
    protected String getEntityName() {
        return "必要的规则定义";
    }

    /**
     * 在子类中设置初始换业务实体清单
     * initEntities = new LinkedList<>();
     * initEntities.add(...);
     * return initEntities;
     */
    @Override
    protected List<RuleType> constructInitEntities() {
        initEntities = new LinkedList<>();
        // 获取规则业务实体
        RuleEntityType ebillEntityType = ruleEntityTypeDao.findByCode(EBILL_INVOICE_CHECK);
        if (Objects.nonNull(ebillEntityType)) {
            String entityTypeId = ebillEntityType.getId();
            initEntities.add(new RuleType(entityTypeId, "ebill-invoice_check-hgxjcgz", "合规性检查规则", "在我的票夹导入我的票据后，系统会执行票据的合规性检查，并在我的票据上保存检查结果。"));
        }
        RuleEntityType somsEntityType = ruleEntityTypeDao.findByCode(SOMS_ALLOT_WORK_STRATEGY);
        if (Objects.nonNull(somsEntityType)) {
            String entityTypeId = somsEntityType.getId();
            initEntities.add(new RuleType(entityTypeId, "soms-allotworkstrategy-fwddpggz", "服务订单派工规则", "在共享服务订单生成工作池任务时，SOMS调用此规则来确定工作池任务的派工策略。"));
        }
        RuleEntityType somsOrderEntityType = ruleEntityTypeDao.findByCode(SOMS_SHARE_ORDER);
        if (Objects.nonNull(somsOrderEntityType)) {
            String entityTypeId = somsOrderEntityType.getId();
            initEntities.add(new RuleType(entityTypeId, "soms-share_order-rgfhblgz", "人工复核比例规则", "在共享服务订单处理流程中，SOMS调用此规则来确定服务订单是否需要人工复核。"));
        }
        return initEntities;
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    @Override
    protected boolean alreadyExists(RuleType entity) {
        RuleType ruleType = ruleTypeDao.findByCodeAndTenantCode(entity.getCode(), ContextUtil.getTenantCode());
        if (Objects.nonNull(ruleType)) {
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
    protected void save(RuleType entity) {
        ruleTypeDao.save(entity);
    }
}
