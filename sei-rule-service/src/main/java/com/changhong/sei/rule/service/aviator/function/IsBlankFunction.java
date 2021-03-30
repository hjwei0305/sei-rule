package com.changhong.sei.rule.service.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义函数：是空字符串
 *
 * @author 王锦光 wangjg
 * @version 2021-03-30 13:40
 */
@Component("isBlank")
public class IsBlankFunction extends AbstractFunction {
    private static final long serialVersionUID = -2261674414275917896L;

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg) {
        String argString = FunctionUtils.getStringValue(arg, env);
        return StringUtils.isBlank(argString) ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
    }

    @Override
    public String getName() {
        return "isBlank";
    }
}
