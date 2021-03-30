package com.changhong.sei.rule.dto.engine;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能: 自定义规则函数
 *
 * @author 王锦光 wangjg
 * @version 2021-03-30 14:41
 */
@ApiModel("自定义规则函数")
public class RuleFunction implements Serializable {
    private static final long serialVersionUID = -5566245093423223834L;
    /**
     * 函数名
     */
    @ApiModelProperty("函数名")
    private String code;
    /**
     * 函数名称
     */
    @ApiModelProperty("函数名称")
    private String name;
    /**
     * 适用的属性类型
     */
    @ApiModelProperty("适用的属性类型")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private RuleAttributeType ruleAttributeType = RuleAttributeType.STRING;

    public RuleFunction() {
    }

    public RuleFunction(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public RuleFunction(String code, String name, RuleAttributeType ruleAttributeType) {
        this.code = code;
        this.name = name;
        this.ruleAttributeType = ruleAttributeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
