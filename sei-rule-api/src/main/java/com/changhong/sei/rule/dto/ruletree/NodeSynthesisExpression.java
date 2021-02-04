package com.changhong.sei.rule.dto.ruletree;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 实现功能: 规则节点的综合表达式
 *
 * @author 王锦光 wangjg
 * @version 2021-02-04 13:58
 */
@ApiModel("规则节点的综合表达式")
public class NodeSynthesisExpression extends BaseEntityDto {
    private static final long serialVersionUID = -577267315858896449L;
    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String name;

    /**
     * 层级
     */
    @ApiModelProperty("层级")
    private Integer nodeLevel = 0;

    /**
     * 节点综合表达式
     */
    @ApiModelProperty("节点综合表达式")
    private List<SynthesisExpression> expressions;

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

    public List<SynthesisExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<SynthesisExpression> expressions) {
        this.expressions = expressions;
    }
}
