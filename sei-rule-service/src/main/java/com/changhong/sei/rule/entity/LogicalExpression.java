package com.changhong.sei.rule.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 规则树逻辑表达式(LogicalExpression)实体类
 *
 * @author sei
 * @since 2021-01-13 17:04:39
 */
@Entity
@Table(name = "logical_expression")
@DynamicInsert
@DynamicUpdate
public class LogicalExpression extends BaseAuditableEntity implements ITenant {
    private static final long serialVersionUID = -7572118685803376897L;
    /**
     * 规则树节点Id
     */
    @Column(name = "rule_tree_node_id")
    private String ruleTreeNodeId;

    /**
     * 规则属性Id
     */
    @Column(name = "rule_attribute_id")
    private String ruleAttributeId;
    /**
     * 规则属性
     */
    @ManyToOne
    @JoinColumn(name = "rule_attribute_id", insertable = false, updatable = false)
    private RuleAttribute ruleAttribute;
    /**
     * 运算符(包含：CONTAIN,等于：EQUAL,小于：LESS,小于等于：LESS_EQUAL,大于：GREATER,大于等于：GREATER_EQUAL,不等于：NOTEQUAL,正则匹配：MATCH,比较器：COMPARER)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "comparison_operator")
    private ComparisonOperator comparisonOperator = ComparisonOperator.CONTAIN;
    /**
     * 匹配值
     */
    @Column(name = "comparison_value")
    private String comparisonValue;
    /**
     * 匹配的显示值
     */
    @Column(name = "display_value")
    private String displayValue;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;

    public String getRuleTreeNodeId() {
        return ruleTreeNodeId;
    }

    public void setRuleTreeNodeId(String ruleTreeNodeId) {
        this.ruleTreeNodeId = ruleTreeNodeId;
    }


    public String getRuleAttributeId() {
        return ruleAttributeId;
    }

    public void setRuleAttributeId(String ruleAttributeId) {
        this.ruleAttributeId = ruleAttributeId;
    }

    public RuleAttribute getRuleAttribute() {
        return ruleAttribute;
    }

    public void setRuleAttribute(RuleAttribute ruleAttribute) {
        this.ruleAttribute = ruleAttribute;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public String getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(String comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}