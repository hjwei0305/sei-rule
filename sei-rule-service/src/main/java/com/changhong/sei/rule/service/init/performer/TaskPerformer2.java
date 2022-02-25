package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.RuleAttributeDao;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.entity.RuleEntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.EBILL_INVOICE_CHECK;
import static com.changhong.sei.rule.service.init.performer.TaskPerformer1.SOMS_ALLOT_WORK_STRATEGY;

/**
 * 实现功能: 初始化规则主体属性配置
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer2 extends BasePerformer {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleAttributeDao ruleAttributeDao;
    /**
     * 定义初始化业务实体
     */
    private static final List<RuleAttribute> ebillInvoiceAttributes;
    private static final List<RuleAttribute> somsAllotAttributes;

    static {
        ebillInvoiceAttributes = new LinkedList<>();
        ebillInvoiceAttributes.add(new RuleAttribute("buyerName", "购买方名称", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("buyerTaxCode", "购买方税号", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("content", "发票明细内容", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("invoiceCode", "发票代码", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("invoiceDate", "开票日期", RuleAttributeType.DATETIME, "DatePicker"));
        ebillInvoiceAttributes.add(new RuleAttribute("invoiceNo", "发票号码", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("printingCode", "印刷发票代码", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("printingNo", "印刷发票号码", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("sellerName", "销售方名称", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("totalTaxAmount", "价税合计金额", RuleAttributeType.NUMBER, "MoneyInput"));
        ebillInvoiceAttributes.add(new RuleAttribute("bizTypeName", "业务类型名称", RuleAttributeType.STRING, "STRING"));
        ebillInvoiceAttributes.add(new RuleAttribute("businessInfo.amount", "费用金额", RuleAttributeType.NUMBER, "MoneyInput"));
        ebillInvoiceAttributes.add(new RuleAttribute("businessInfo.corporationName", "公司名称", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("businessInfo.corporationTaxNo", "公司税号", RuleAttributeType.STRING, "Input"));
        ebillInvoiceAttributes.add(new RuleAttribute("businessInfo.createdDate", "单据创建日期", RuleAttributeType.DATETIME, "DatePicker"));
        ebillInvoiceAttributes.add(new RuleAttribute("businessInfo.expenseDetailTypeName", "费用明细类型名称", RuleAttributeType.DATETIME, "DatePicker"));
        ebillInvoiceAttributes.add(new RuleAttribute("businessInfo.passengerName", "报销人", RuleAttributeType.STRING, "DatePicker"));

        somsAllotAttributes = new LinkedList<>();
        somsAllotAttributes.add(new RuleAttribute("businessCategoryCode", "业务分类", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/businessCategory/findAll"));
        somsAllotAttributes.add(new RuleAttribute("businessTypeCode", "业务类型", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/businessType/findAllUnfrozen"));
        somsAllotAttributes.add(new RuleAttribute("businessTypeName", "业务类型名称", RuleAttributeType.STRING, "Input"));
        somsAllotAttributes.add(new RuleAttribute("corporationCode", "公司代码", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "sei-basic/corporation/findAllAuthEntityData"));
        somsAllotAttributes.add(new RuleAttribute("workNodeCode", "工作节点", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/workNode/findAll"));
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    private boolean checkExists(RuleAttribute entity) {
        RuleAttribute ruleAttribute = ruleAttributeDao.findByRuleEntityTypeIdAndAttribute(entity.getRuleEntityTypeId(), entity.getAttribute());
        if (Objects.nonNull(ruleAttribute)) {
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
            ebillInvoiceAttributes.forEach( entity -> {
                // 设置规则业务实体Id
                entity.setRuleEntityTypeId(ebillEntityType.getId());
                // 判断是否已经存在
                if (!checkExists(entity)) {
                    ruleAttributeDao.save(entity);
                }
            });
        }
        RuleEntityType somsEntityType = ruleEntityTypeDao.findByCode(SOMS_ALLOT_WORK_STRATEGY);
        if (Objects.nonNull(somsEntityType)) {
            somsAllotAttributes.forEach(entity -> {
                // 设置规则业务实体Id
                entity.setRuleEntityTypeId(somsEntityType.getId());
                // 判断是否已经存在
                if (!checkExists(entity)) {
                    ruleAttributeDao.save(entity);
                }
            });
        }
        // 规则主体属性初始化完毕！
        return OperateResult.operationSuccess("00050");
    }
}
