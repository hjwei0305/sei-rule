package com.changhong.sei.rule.sdk.manager;

import com.changhong.sei.core.dto.ResultData;

/**
 * 实现功能: 业务系统自定义比较器接口
 *
 * @author 王锦光 wangjg
 * @version 2021-01-18 16:51
 */
public interface CustomComparator {
    /**
     * 执行比较
     *
     * @param ruleEntityJson 执行规则的业务实体JSON
     * @return 比较器执行结果
     */
    ResultData<Boolean> compare(String ruleEntityJson);
}
