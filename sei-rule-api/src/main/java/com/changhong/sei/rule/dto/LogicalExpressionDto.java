package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 规则树逻辑表达式(LogicalExpression)DTO类
 *
 * @author sei
 * @since 2021-01-13 17:04:57
 */
@ApiModel(description = "$tool.trim($!{tableInfo.comment})DTO")
public class LogicalExpressionDto extends BaseEntityDto {
    private static final long serialVersionUID = 4068060971518565005L;
    /**
     * 规则树节点Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String ruleTreeNodeId;
    /**
     * 规则属性Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String ruleAttributeId;
    /**
     * 运算符
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true,
            notes = "包含：CONTAIN,等于：EQUAL,小于：LESS,小于等于：LESS_EQUAL,大于：GREATER,大于等于：GREATER_EQUAL,不等于：NOTEQUAL,正则匹配：MATCH,比较器：COMPARER")
    private ComparisonOperator comparisonOperator = ComparisonOperator.CONTAIN;
    /**
     * 匹配值
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String comparisonValue;
    /**
     * 租户代码
     */
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
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

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}