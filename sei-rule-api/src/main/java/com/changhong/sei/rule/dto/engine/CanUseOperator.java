package com.changhong.sei.rule.dto.engine;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能: 由规则属性类型限制的可用运算符
 *
 * @author 王锦光 wangjg
 * @version 2021-01-19 9:52
 */
@ApiModel("可用运算符")
public class CanUseOperator implements Serializable {
    private static final long serialVersionUID = -8909137160340518680L;
    /**
     * 比较运算符
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty("比较运算符")
    private ComparisonOperator comparisonOperator = ComparisonOperator.CONTAIN;

    public CanUseOperator() {
    }

    public CanUseOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }
}
