package com.changhong.sei.rule.dto.enums;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能: 比较运算符
 *
 * @author 王锦光 wangjg
 * @version 2020-08-14 9:26
 */
public enum ComparisonOperator {
    /**
     * 包含
     */
    @Remark("包含")
    CONTAIN,
    /**
     * 等于
     */
    @Remark("等于")
    EQUAL,
    /**
     * 不等于
     */
    @Remark("不等于")
    NOTEQUAL,
    /**
     * 正则匹配
     */
    @Remark("正则匹配")
    MATCH,
    /**
     * 运算
     */
    @Remark("比较器")
    COMPARER

}
