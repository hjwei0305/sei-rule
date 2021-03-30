package com.changhong.sei.rule.service.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义函数：不是空字符串
 *
 * @author 王锦光 wangjg
 * @version 2021-03-30 13:40
 */
@Component("isNotBlank")
public class IsNotBlankFunction extends AbstractFunction {

    private static final long serialVersionUID = -4103588766684511553L;

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg) {
        String argString = FunctionUtils.getStringValue(arg, env);
        return StringUtils.isNotBlank(argString) ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
    }

    /**
     * Get the function name
     *
     * @return
     */
    @Override
    public String getName() {
        return "isNotBlank";
    }
}
