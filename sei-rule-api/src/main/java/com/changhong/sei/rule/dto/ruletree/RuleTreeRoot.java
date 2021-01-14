package com.changhong.sei.rule.dto.ruletree;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 规则树根节点
 *
 * @author sei
 * @since 2021-01-13 16:30:12
 */
@ApiModel("规则树根节点")
public class RuleTreeRoot extends BaseEntityDto {
    private static final long serialVersionUID = -1661639702336759454L;
    /**
     * 代码
     */
    @Size(max = 10)
    @ApiModelProperty("代码")
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    /**
     * 优先级
     */
    @NotNull
    @ApiModelProperty(value = "优先级", required = true)
    private Integer rank = 0;
    /**
     * 规则类型Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "规则类型Id", required = true)
    private String ruleTypeId;
    /**
     * 启用标识
     */
    @NotNull
    @ApiModelProperty(value = "启用标识")
    private Boolean enabled = Boolean.FALSE;
    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(String ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}