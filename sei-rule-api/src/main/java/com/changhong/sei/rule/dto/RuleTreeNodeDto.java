package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 规则树节点(RuleTreeNode)DTO类
 *
 * @author sei
 * @since 2021-01-13 16:30:12
 */
@ApiModel(description = "$tool.trim($!{tableInfo.comment})DTO")
public class RuleTreeNodeDto extends BaseEntityDto {
    private static final long serialVersionUID = -7270581327275285182L;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 10)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String name;
    /**
     * 父节点Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
    private String parentId;
    /**
     * 层级
     */
    @NotNull
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private Integer nodeLevel = 0;
    /**
     * 代码路径
     */
    @Size(max = 100)
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
    private String codePath;
    /**
     * 名称路径
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
    private String namePath;
    /**
     * 优先级
     */
    @NotNull
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private Integer rank = 0;
    /**
     * 规则类型Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String ruleTypeId;
    /**
     * 真节点
     */
    @NotNull
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private Boolean trueNode = Boolean.FALSE;
    /**
     * 规则结束
     */
    @NotNull
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private Boolean finished = Boolean.FALSE;
    /**
     * 服务方法Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
    private String ruleServiceMethodId;
    /**
     * 是异步执行
     */
    @NotNull
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private Boolean asyncExecute = Boolean.FALSE;
    /**
     * 启用标识
     */
    @NotNull
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
    private Boolean enabled = Boolean.FALSE;
    /**
     * 租户代码
     */
    @ApiModelProperty(value = "$tool.trim(${column.comment})")
    private String tenantCode;
    /**
     * 子节点清单
     */
    @ApiModelProperty(value = "子节点清单")
    private List<RuleTreeNodeDto> children;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
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

    public Boolean getTrueNode() {
        return trueNode;
    }

    public void setTrueNode(Boolean trueNode) {
        this.trueNode = trueNode;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getRuleServiceMethodId() {
        return ruleServiceMethodId;
    }

    public void setRuleServiceMethodId(String ruleServiceMethodId) {
        this.ruleServiceMethodId = ruleServiceMethodId;
    }

    public Boolean getAsyncExecute() {
        return asyncExecute;
    }

    public void setAsyncExecute(Boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
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

    public List<RuleTreeNodeDto> getChildren() {
        return children;
    }

    public void setChildren(List<RuleTreeNodeDto> children) {
        this.children = children;
    }
}