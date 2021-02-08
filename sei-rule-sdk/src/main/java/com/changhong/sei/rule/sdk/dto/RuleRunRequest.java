package com.changhong.sei.rule.sdk.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 实现功能: 规则执行请求
 *
 * @author 王锦光 wangjg
 * @version 2021-01-16 21:02
 */
@ApiModel("规则执行请求")
public class RuleRunRequest implements Serializable {
    private static final long serialVersionUID = 8119009858098382696L;
    /**
     * 规则类型代码
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "规则类型代码", required = true)
    private String ruleTypeCode;

    /**
     * 执行规则的业务实体JSON字符串
     */
    @NotBlank
    @ApiModelProperty(value = "执行规则的业务实体JSON字符串", required = true)
    private String ruleEntityJson;

    /**
     * 不执行服务方法
     */
    @NotNull
    @ApiModelProperty(value = "不执行服务方法", required = true)
    private Boolean notRunServiceMethod = Boolean.FALSE;

    public String getRuleTypeCode() {
        return ruleTypeCode;
    }

    public void setRuleTypeCode(String ruleTypeCode) {
        this.ruleTypeCode = ruleTypeCode;
    }

    public String getRuleEntityJson() {
        return ruleEntityJson;
    }

    public void setRuleEntityJson(String ruleEntityJson) {
        this.ruleEntityJson = ruleEntityJson;
    }

    public Boolean getNotRunServiceMethod() {
        return notRunServiceMethod;
    }

    public void setNotRunServiceMethod(Boolean notRunServiceMethod) {
        this.notRunServiceMethod = notRunServiceMethod;
    }
}
