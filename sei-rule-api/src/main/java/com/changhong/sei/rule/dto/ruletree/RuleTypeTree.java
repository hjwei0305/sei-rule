package com.changhong.sei.rule.dto.ruletree;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import com.changhong.sei.rule.dto.RuleTypeDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 实现功能: 规则类型树
 *
 * @author 王锦光 wangjg
 * @version 2021-01-18 8:43
 */
@ApiModel("规则类型树")
public class RuleTypeTree extends BaseEntityDto {
    private static final long serialVersionUID = 6975835158593258861L;
    /**
     * 代码
     */
    @ApiModelProperty("代码")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 层级
     */
    @ApiModelProperty("层级")
    private Integer nodeLevel = 0;
    /**
     * 规则业务实体类型Id
     */
    @ApiModelProperty("规则业务实体类型Id")
    private String ruleEntityTypeId;
    /**
     * 子节点
     */
    @ApiModelProperty("子节点")
    private List<RuleTypeTree> children;

    public RuleTypeTree() {
    }

    public RuleTypeTree(RuleTypeDto ruleType) {
        this.id = ruleType.getId();
        this.code = ruleType.getCode();
        this.name = ruleType.getName();
        this.nodeLevel = 1;
        this.ruleEntityTypeId = ruleType.getRuleEntityTypeId();
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

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getRuleEntityTypeId() {
        return ruleEntityTypeId;
    }

    public void setRuleEntityTypeId(String ruleEntityTypeId) {
        this.ruleEntityTypeId = ruleEntityTypeId;
    }

    public List<RuleTypeTree> getChildren() {
        return children;
    }

    public void setChildren(List<RuleTypeTree> children) {
        this.children = children;
    }
}
