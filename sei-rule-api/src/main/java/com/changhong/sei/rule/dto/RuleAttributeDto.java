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
@ApiModel(description = "规则属性定义DTO")
public class RuleAttributeDto extends BaseEntityDto {
    private static final long serialVersionUID = 7929147332119138792L;
    /**
     * 规则业务实体Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "规则业务实体Id", required = true)
    private String ruleEntityTypeId;
    /**
     * 属性名
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "属性名", required = true)
    private String attribute;
    /**
     * 属性名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "属性名称", required = true)
    private String name;
    /**
     * 属性类型(字符串：STRING,日期：DATETIME,数值：NUMBER,布尔：BOOLEAN)
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "属性类型", required = true,
            notes = "(字符串：STRING,日期：DATETIME,数值：NUMBER,布尔：BOOLEAN)")
    private RuleAttributeType ruleAttributeType = RuleAttributeType.STRING;
    /**
     * UI组件
     */
    @Size(max = 100)
    @ApiModelProperty(value = "UI组件")
    private String uiComponent;
    /**
     * 匹配值使用的字段
     */
    @Size(max = 50)
    @ApiModelProperty(value = "匹配值使用的字段")
    private String matchField;
    /**
     * 获取数据的url
     */
    @Size(max = 200)
    @ApiModelProperty(value = "获取数据的url")
    private String findDataUrl;

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

    public String getMatchField() {
        return matchField;
    }

    public void setMatchField(String matchField) {
        this.matchField = matchField;
    }

    public String getFindDataUrl() {
        return findDataUrl;
    }

    public void setFindDataUrl(String findDataUrl) {
        this.findDataUrl = findDataUrl;
    }
}