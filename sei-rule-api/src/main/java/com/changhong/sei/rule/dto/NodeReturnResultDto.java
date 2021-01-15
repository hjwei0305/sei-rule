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
@ApiModel(description = "规则树节点返回结果DTO")
public class NodeReturnResultDto extends BaseEntityDto {
    private static final long serialVersionUID = 8958116065995438120L;

    /**
     * 规则树根节点Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "规则树根节点Id", required = true)
    private String ruleTreeRootNodeId;
    /**
     * 规则树节点Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "规则树节点Id", required = true)
    private String ruleTreeNodeId;
    /**
     * 返回结果类型Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "返回结果类型Id", required = true)
    private String ruleReturnTypeId;
    /**
     * 返回对象Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "返回对象Id", required = true)
    private String returnValueId;
    /**
     * 返回对象名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "返回对象名称", required = true)
    private String returnValueName;
    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;
    /**
     * 返回结果类型代码
     */
    @ApiModelProperty("返回结果类型代码")
    private String ruleReturnTypeCode;
    /**
     * 返回结果类型名称
     */
    @ApiModelProperty("返回结果类型名称")
    private String ruleReturnTypeName;
    /**
     * 返回结果类型UI组件
     */
    @ApiModelProperty("返回结果类型UI组件")
    private String ruleReturnTypeUiComponent;

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

    public String getRuleTreeRootNodeId() {
        return ruleTreeRootNodeId;
    }

    public void setRuleTreeRootNodeId(String ruleTreeRootNodeId) {
        this.ruleTreeRootNodeId = ruleTreeRootNodeId;
    }

    public String getRuleReturnTypeCode() {
        return ruleReturnTypeCode;
    }

    public void setRuleReturnTypeCode(String ruleReturnTypeCode) {
        this.ruleReturnTypeCode = ruleReturnTypeCode;
    }

    public String getRuleReturnTypeName() {
        return ruleReturnTypeName;
    }

    public void setRuleReturnTypeName(String ruleReturnTypeName) {
        this.ruleReturnTypeName = ruleReturnTypeName;
    }

    public String getRuleReturnTypeUiComponent() {
        return ruleReturnTypeUiComponent;
    }

    public void setRuleReturnTypeUiComponent(String ruleReturnTypeUiComponent) {
        this.ruleReturnTypeUiComponent = ruleReturnTypeUiComponent;
    }
}