package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 返回结果定义(RuleReturnType)DTO类
 *
 * @author sei
 * @since 2021-01-13 15:57:24
 */
@ApiModel(description = "返回结果定义DTO")
public class RuleReturnTypeDto extends BaseEntityDto {
    private static final long serialVersionUID = -5957257447021974798L;
    /**
     * 规则业务实体Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "规则业务实体Id", required = true)
    private String ruleEntityTypeId;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "代码", required = true)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    /**
     * UI组件
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "UI组件")
    private String uiComponent;

    public String getRuleEntityTypeId() {
        return ruleEntityTypeId;
    }

    public void setRuleEntityTypeId(String ruleEntityTypeId) {
        this.ruleEntityTypeId = ruleEntityTypeId;
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

    public String getUiComponent() {
        return uiComponent;
    }

    public void setUiComponent(String uiComponent) {
        this.uiComponent = uiComponent;
    }
}