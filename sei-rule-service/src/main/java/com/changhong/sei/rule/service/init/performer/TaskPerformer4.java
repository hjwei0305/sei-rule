package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.RuleComparatorDao;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.entity.RuleComparator;
import com.changhong.sei.rule.entity.RuleEntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.EBILL_INVOICE_CHECK;

/**
 * 实现功能: 初始化规则主体返回结果配置
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer4 extends BasePerformer {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleComparatorDao ruleComparatorDao;
    /**
     * 定义初始化业务实体
     */
    private static final List<RuleComparator> ebillInvoiceComparators;

    static {
        ebillInvoiceComparators = new LinkedList<>();
        ebillInvoiceComparators.add(new RuleComparator("inSupplierBlacklist", "在供应商黑名单中", "myBillRule"));
        ebillInvoiceComparators.add(new RuleComparator("invoiceIsOverdue", "开票日期超过6个月", "myBillRule"));
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    private boolean checkExists(RuleComparator entity) {
        RuleComparator comparator = ruleComparatorDao.findByRuleEntityTypeIdAndPathAndMethod(entity.getRuleEntityTypeId(), entity.getPath(), entity.getMethod());
        if (Objects.nonNull(comparator)) {
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
            ebillInvoiceComparators.forEach( entity -> {
                // 设置规则业务实体Id
                entity.setRuleEntityTypeId(ebillEntityType.getId());
                // 判断是否已经存在
                if (!checkExists(entity)) {
                    ruleComparatorDao.save(entity);
                }
            });
        }
        // 规则主体比较器初始化完毕！
        return OperateResult.operationSuccess("00052");
    }
}
