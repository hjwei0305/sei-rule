package com.changhong.sei.rule.dto.enums;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能: 匹配值类型
 *
 * @author 王锦光 wangjg
 * @version 2021-03-19 10:43
 */
public enum ComparisonValueType {
    /**
     * 一般属性值
     */
    @Remark("属性值")
    NORMAL,

    /**
     * 其他属性
     */
    @Remark("其他属性")
    OTHER
}
