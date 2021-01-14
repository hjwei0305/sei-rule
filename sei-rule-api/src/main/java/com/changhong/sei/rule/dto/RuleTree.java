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
     * 规则名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "规则名称", required = true)
    private String name;

    /**
     * 公司代码
     */
    @Size(max = 10)
    @ApiModelProperty(value = "公司代码")
    private String corporationCode;
    /**
     * 公司名称
     */
    @Size(max = 100)
    @ApiModelProperty(value = "公司名称")
    private String corporationName;
    /**
     * 优先级
     */
    @NotNull
    @ApiModelProperty(value = "优先级", required = true)
    private Integer rank = 0;
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

    public String getCorporationCode() {
        return corporationCode;
    }

    public void setCorporationCode(String corporationCode) {
        this.corporationCode = corporationCode;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public RuleTreeNodeDto getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(RuleTreeNodeDto treeNode) {
        this.treeNode = treeNode;
    }
}
