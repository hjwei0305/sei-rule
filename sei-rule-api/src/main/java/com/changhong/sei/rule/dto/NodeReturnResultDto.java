package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 规则树节点返回结果(NodeReturnResult)DTO类
 *
 * @author sei
 * @since 2021-01-13 17:18:52
 */
@ApiModel(description = "$tool.trim($!{tableInfo.comment})DTO")
public class NodeReturnResultDto extends BaseEntityDto {
    private static final long serialVersionUID = 8958116065995438120L;
    /**
     * 规则树节点Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String ruleTreeNodeId;
    /**
     * 返回结果类型Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String ruleReturnTypeId;
    /**
     * 返回对象Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String returnValueId;
    /**
     * 返回对象名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String returnValueName;
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

    public String getRuleReturnTypeId() {
        return ruleReturnTypeId;
    }

    public void setRuleReturnTypeId(String ruleReturnTypeId) {
        this.ruleReturnTypeId = ruleReturnTypeId;
    }

    public String getReturnValueId() {
        return returnValueId;
    }

    public void setReturnValueId(String returnValueId) {
        this.returnValueId = returnValueId;
    }

    public String getReturnValueName() {
        return returnValueName;
    }

    public void setReturnValueName(String returnValueName) {
        this.returnValueName = returnValueName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}