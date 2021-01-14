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
@ApiModel(description = "规则树节点DTO")
public class RuleTreeNodeDto extends BaseEntityDto {
    private static final long serialVersionUID = -7270581327275285182L;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 10)
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
     * 父节点Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "父节点Id")
    private String parentId;
    /**
     * 层级
     */
    @NotNull
    @ApiModelProperty(value = "层级", required = true)
    private Integer nodeLevel = 0;
    /**
     * 代码路径
     */
    @Size(max = 100)
    @ApiModelProperty(value = "代码路径")
    private String codePath;
    /**
     * 名称路径
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "名称路径")
    private String namePath;
    /**
     * 优先级
     */
    @NotNull
    @ApiModelProperty(value = "优先级", required = true)
    private Integer rank = 0;
    /**
     * 规则类型Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "规则类型Id", required = true)
    private String ruleTypeId;
    /**
     * 真节点
     */
    @NotNull
    @ApiModelProperty(value = "真节点", required = true)
    private Boolean trueNode = Boolean.FALSE;
    /**
     * 规则结束
     */
    @NotNull
    @ApiModelProperty(value = "规则结束", required = true)
    private Boolean finished = Boolean.FALSE;
    /**
     * 服务方法Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "服务方法Id")
    private String ruleServiceMethodId;
    /**
     * 是异步执行
     */
    @NotNull
    @ApiModelProperty(value = "是异步执行", required = true)
    private Boolean asyncExecute = Boolean.FALSE;
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
    /**
     * 规则逻辑表达式
     */
    @ApiModelProperty(value = "规则逻辑表达式")
    private List<LogicalExpressionDto> expressionDtos;
    /**
     * 返回结果
     */
    @ApiModelProperty(value = "返回结果")
    private List<NodeReturnResultDto> nodeReturnResultDtos;
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

    public List<LogicalExpressionDto> getExpressionDtos() {
        return expressionDtos;
    }

    public void setExpressionDtos(List<LogicalExpressionDto> expressionDtos) {
        this.expressionDtos = expressionDtos;
    }

    public List<NodeReturnResultDto> getNodeReturnResultDtos() {
        return nodeReturnResultDtos;
    }

    public void setNodeReturnResultDtos(List<NodeReturnResultDto> nodeReturnResultDtos) {
        this.nodeReturnResultDtos = nodeReturnResultDtos;
    }

    public List<RuleTreeNodeDto> getChildren() {
        return children;
    }

    public void setChildren(List<RuleTreeNodeDto> children) {
        this.children = children;
    }
}