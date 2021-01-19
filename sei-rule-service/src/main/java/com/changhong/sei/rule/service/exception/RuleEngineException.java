package com.changhong.sei.rule.service.exception;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.exception.ServiceException;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能: 规则引擎异常
 * @date 2021/01/19 10:35
 */
public class RuleEngineException extends ServiceException {

    /**
     * @param key  多语言key
     * @param args 填充参数 如：key=参数A{0},参数B{1}  此时的args={"A", "B"}
     * @return 返回语意
     */
    public RuleEngineException(String key, Object... args) {
        super(ContextUtil.getMessage(key,args));
    }

    public RuleEngineException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
