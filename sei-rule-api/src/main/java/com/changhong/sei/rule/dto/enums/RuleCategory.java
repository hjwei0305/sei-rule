package com.changhong.sei.rule.dto.enums;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能: 业务匹配规则分类
 *
 * @author 王锦光 wangjg
 * @version 2020-08-14 9:23
 */
public enum RuleCategory {
    /**
     * 无需认款规则
     */
    @Remark("无需认款规则")
    UNRECOGNIZE,

    /**
     * 认款规则
     */
    @Remark("认款规则")
    RECOGNIZE
}
