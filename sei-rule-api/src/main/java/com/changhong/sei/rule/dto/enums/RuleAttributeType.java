package com.changhong.sei.rule.dto.enums;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能: 规则属性类型
 *
 * @author 王锦光 wangjg
 * @version 2020-08-14 9:26
 */
public enum RuleAttributeType {
    /**
     * 字符串
     */
    @Remark("字符串")
    STRING,
    /**
     * 数值
     */
    @Remark("数值")
    NUMBER,
    /**
     * 布尔值
     */
    @Remark("布尔值")
    BOOLEAN,
    /**
     * 日期
     */
    @Remark("日期")
    DATETIME
}
