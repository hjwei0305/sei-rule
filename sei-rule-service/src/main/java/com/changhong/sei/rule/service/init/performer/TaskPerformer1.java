package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 实现功能: 初始化规则主体
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer1 extends BasePerformer<RuleEntityType> {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    public static final String EBILL_INVOICE_CHECK = "ebill-invoice_check";
    public static final String SOMS_ALLOT_WORK_STRATEGY = "soms-AllotWorkStrategy";
    public static final String SOMS_SHARE_ORDER = "soms-share_order";

    /**
     * 设置初始化业务实体名称
     *
     * @return 业务实体名称
     */
    @Override
    protected String getEntityName() {
        return "规则主体";
    }

    /**
     * 在子类中设置初始换业务实体清单
     * initEntities = new LinkedList<>();
     * initEntities.add(...);
     */
    @Override
    protected List<RuleEntityType> constructInitEntities() {
        List<RuleEntityType> initEntities = new LinkedList<>();
        initEntities.add(new RuleEntityType(EBILL_INVOICE_CHECK, "我的票据合规性检查", "ebill"));
        initEntities.add(new RuleEntityType(SOMS_ALLOT_WORK_STRATEGY, "共享运营派工策略", "soms-v6"));
        initEntities.add(new RuleEntityType(SOMS_SHARE_ORDER, "共享服务订单规则", "soms-v6"));
        return initEntities;
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    @Override
    protected boolean alreadyExists(RuleEntityType entity) {
        RuleEntityType entityType = ruleEntityTypeDao.findByCode(entity.getCode());
        if (Objects.nonNull(entityType)) {
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
    protected void save(RuleEntityType entity) {
        ruleEntityTypeDao.save(entity);
    }
}
