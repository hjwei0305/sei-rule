package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.rule.dao.RuleComparatorDao;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.entity.RuleComparator;
import com.changhong.sei.rule.entity.RuleEntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Objects;

import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.EBILL_INVOICE_CHECK;

/**
 * 实现功能: 初始化规则主体比较器配置
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer4 extends BasePerformer<RuleComparator> {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleComparatorDao ruleComparatorDao;

    /**
     * 设置初始化业务实体名称
     *
     * @return 业务实体名称
     */
    @Override
    protected String getEntityName() {
        return "规则主体的比较器";
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
            initEntities.add(new RuleComparator(entityTypeId, "inSupplierBlacklist", "在供应商黑名单中", "myBillRule"));
            initEntities.add(new RuleComparator(entityTypeId, "invoiceIsOverdue", "开票日期超过6个月", "myBillRule"));
        }
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    protected boolean alreadyExists(RuleComparator entity) {
        RuleComparator comparator = ruleComparatorDao.findByRuleEntityTypeIdAndPathAndMethod(entity.getRuleEntityTypeId(), entity.getPath(), entity.getMethod());
        if (Objects.nonNull(comparator)) {
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
    protected void save(RuleComparator entity) {
        ruleComparatorDao.save(entity);
    }
}
