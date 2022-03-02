package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.rule.dao.RuleAttributeDao;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.entity.RuleEntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 实现功能: 初始化规则主体属性配置
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer2 extends BasePerformer<RuleAttribute> {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleAttributeDao ruleAttributeDao;

    /**
     * 设置初始化业务实体名称
     *
     * @return 业务实体名称
     */
    @Override
    protected String getEntityName() {
        return "规则主体的属性";
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
        RuleEntityType ebillEntityType = ruleEntityTypeDao.findByCode(TaskPerformer1.EBILL_INVOICE_CHECK);
        if (Objects.nonNull(ebillEntityType)) {
            String entityTypeId = ebillEntityType.getId();
            initEntities.add(new RuleAttribute(entityTypeId, "buyerName", "购买方名称", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "buyerTaxCode", "购买方税号", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "content", "发票明细内容", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "invoiceCode", "发票代码", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "invoiceDate", "开票日期", RuleAttributeType.DATETIME, "DatePicker"));
            initEntities.add(new RuleAttribute(entityTypeId, "invoiceNo", "发票号码", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "printingCode", "印刷发票代码", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "printingNo", "印刷发票号码", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "sellerName", "销售方名称", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "totalTaxAmount", "价税合计金额", RuleAttributeType.NUMBER, "MoneyInput"));
            initEntities.add(new RuleAttribute(entityTypeId, "bizTypeName", "业务类型名称", RuleAttributeType.STRING, "STRING"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessInfo.amount", "费用金额", RuleAttributeType.NUMBER, "MoneyInput"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessInfo.corporationName", "公司名称", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessInfo.corporationTaxNo", "公司税号", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessInfo.createdDate", "单据创建日期", RuleAttributeType.DATETIME, "DatePicker"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessInfo.expenseDetailTypeName", "费用明细类型名称", RuleAttributeType.DATETIME, "DatePicker"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessInfo.passengerName", "报销人", RuleAttributeType.STRING, "DatePicker"));
        }
        RuleEntityType somsEntityType = ruleEntityTypeDao.findByCode(TaskPerformer1.SOMS_ALLOT_WORK_STRATEGY);
        if (Objects.nonNull(somsEntityType)) {
            String entityTypeId = somsEntityType.getId();
            initEntities.add(new RuleAttribute(entityTypeId, "businessCategoryCode", "业务分类", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/businessCategory/findAll"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessTypeCode", "业务类型", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/businessType/findAllUnfrozen"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessTypeName", "业务类型名称", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "corporationCode", "公司代码", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "sei-basic/corporation/findAllAuthEntityData"));
            initEntities.add(new RuleAttribute(entityTypeId, "workNodeCode", "工作节点", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/workNode/findAll"));
        }
        RuleEntityType somsOrderEntityType = ruleEntityTypeDao.findByCode(TaskPerformer1.SOMS_SHARE_ORDER);
        if (Objects.nonNull(somsOrderEntityType)) {
            String entityTypeId = somsOrderEntityType.getId();
            initEntities.add(new RuleAttribute(entityTypeId, "businessCategoryCode", "业务分类", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/businessCategory/findAll"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessTypeCode", "业务类型", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "soms-v6/businessType/findAllUnfrozen"));
            initEntities.add(new RuleAttribute(entityTypeId, "businessTypeName", "业务类型名称", RuleAttributeType.STRING, "Input"));
            initEntities.add(new RuleAttribute(entityTypeId, "corporationCode", "公司代码", RuleAttributeType.STRING, "ComboListLocal", "code", "name", "sei-basic/corporation/findAllAuthEntityData"));
            initEntities.add(new RuleAttribute(entityTypeId, "orderMoney", "订单金额", RuleAttributeType.NUMBER, "MoneyInput"));
        }
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    @Override
    protected boolean alreadyExists(RuleAttribute entity) {
        RuleAttribute ruleAttribute = ruleAttributeDao.findByRuleEntityTypeIdAndAttribute(entity.getRuleEntityTypeId(), entity.getAttribute());
        if (Objects.nonNull(ruleAttribute)) {
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
    protected void save(RuleAttribute entity) {
        ruleAttributeDao.save(entity);
    }
}
