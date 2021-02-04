package com.changhong.sei.rule.dto.ruletree;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能: 综合表达式
 *
 * @author 王锦光 wangjg
 * @version 2021-02-04 13:44
 */
@ApiModel("综合表达式")
public class SynthesisExpression implements Serializable {
    private static final long serialVersionUID = -4489156372912686794L;
    /**
     * 属性名称
     */
    @ApiModelProperty("属性名称")
    private String ruleAttributeName;

    /**
     * 比较运算名称
     */
    @ApiModelProperty("比较运算名称")
    private String comparisonName;

    /**
     * 匹配值
     */
    @ApiModelProperty("匹配值")
    private String comparisonValue;

    public String getRuleAttributeName() {
        return ruleAttributeName;
    }

    public void setRuleAttributeName(String ruleAttributeName) {
        this.ruleAttributeName = ruleAttributeName;
    }

    public String getComparisonName() {
        return comparisonName;
    }

    public void setComparisonName(String comparisonName) {
        this.comparisonName = comparisonName;
    }

    public String getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(String comparisonValue) {
        this.comparisonValue = comparisonValue;
    }
}
