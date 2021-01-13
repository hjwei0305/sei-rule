package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 规则属性定义(RuleAttribute)DTO类
 *
 * @author sei
 * @since 2021-01-13 15:45:53
 */
@ApiModel(description = "$tool.trim($!{tableInfo.comment})DTO")
public class RuleAttributeDto extends BaseEntityDto {
    private static final long serialVersionUID = 7929147332119138792L;
    /**
     * 规则业务实体Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String ruleEntityTypeId;
    /**
     * 属性名
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String attribute;
    /**
     * 属性名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String name;
    /**
     * 属性类型(字符串：STRING,日期：DATETIME,数值：NUMBER,布尔：BOOLEAN)
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true,
            notes = "(字符串：STRING,日期：DATETIME,数值：NUMBER,布尔：BOOLEAN)")
    private RuleAttributeType ruleAttributeType = RuleAttributeType.STRING;
    /**
     * UI组件
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
    private String uiComponent;

    public String getRuleEntityTypeId() {
        return ruleEntityTypeId;
    }

    public void setRuleEntityTypeId(String ruleEntityTypeId) {
        this.ruleEntityTypeId = ruleEntityTypeId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RuleAttributeType getRuleAttributeType() {
        return ruleAttributeType;
    }

    public void setRuleAttributeType(RuleAttributeType ruleAttributeType) {
        this.ruleAttributeType = ruleAttributeType;
    }

    public String getUiComponent() {
        return uiComponent;
    }

    public void setUiComponent(String uiComponent) {
        this.uiComponent = uiComponent;
    }
}