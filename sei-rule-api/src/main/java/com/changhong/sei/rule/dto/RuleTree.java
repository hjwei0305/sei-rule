package com.changhong.sei.rule.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 实现功能: 业务规则树
 *
 * @author 王锦光 wangjg
 * @version 2020-08-20 13:47
 */
@ApiModel("业务规则树")
public class RuleTree implements Serializable {
    private static final long serialVersionUID = 6395924727672357371L;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 10)
    @ApiModelProperty(value = "代码", required = true)
    private String code;
    /**
     * 规则名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "规则名称", required = true)
    private String name;
    /**
     * 规则类型Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "规则类型Id", required = true)
    private String ruleTypeId;
    /**
     * 优先级
     */
    @NotNull
    @ApiModelProperty(value = "优先级", required = true)
    private Integer rank = 0;
    /**
     * 真节点
     */
    @NotNull
    @ApiModelProperty(value = "真节点", required = true)
    private Boolean trueNode = Boolean.FALSE;
    /**
     * 启用标识
     */
    @NotNull
    @ApiModelProperty(value = "启用标识")
    private Boolean enabled = Boolean.FALSE;
    /**
     * 规则树节点(包含子节点)
     */
    @NotNull
    @ApiModelProperty(value = "规则树节点(包含子节点)", required = true)
    private RuleTreeNodeDto treeNode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(String ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getTrueNode() {
        return trueNode;
    }

    public void setTrueNode(Boolean trueNode) {
        this.trueNode = trueNode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public RuleTreeNodeDto getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(RuleTreeNodeDto treeNode) {
        this.treeNode = treeNode;
    }
}
