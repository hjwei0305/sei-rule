package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 规则类型(RuleType)DTO类
 *
 * @author sei
 * @since 2021-01-13 16:15:28
 */
@ApiModel(description = "规则类型DTO")
public class RuleTypeDto extends BaseEntityDto {
    private static final long serialVersionUID = 7527833729348951167L;
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
    @Size(max = 100)
    @ApiModelProperty(value = "代码")
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    /**
     * 业务描述
     */
    @Size(max = 200)
    @ApiModelProperty(value = "业务描述")
    private String remark;
    /**
     * 租户代码
     */
    @ApiModelProperty("租户代码")
    private String tenantCode;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}