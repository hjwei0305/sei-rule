package com.changhong.sei.rule.service.exception;

import com.changhong.sei.exception.ServiceException;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能: 匹配规则计算器异常
 * @date 2021/01/11 10:29
 */
public class MatchingRuleComparatorException extends ServiceException {
    public MatchingRuleComparatorException(String msg) {
        super(msg);
    }

    public MatchingRuleComparatorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
