package com.changhong.sei.rule.service.aviatorFunction;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:外部自定义Aviator函数
 * @date 2020/12/25 15:09
 */
@Component("MatchRuleComparator")
public class MatchRuleComparatorFunction extends AbstractFunction {

    @Autowired
    private ApiTemplate apiTemplate;

    /**
     * 访问外部服务，实现对环境参数自定义规则判断和修改
     *
     * @param env 环境参数
     * @return
     */
    @Override
    public AviatorObject call(Map<String, Object> env) {
        String appModuleCode = MapUtils.getString(env, "appModuleCode");
        String path = MapUtils.getString(env, "path");
        Boolean result = apiTemplate.postByAppModuleCode(appModuleCode, path, Boolean.class, env);
        return AviatorBoolean.valueOf(result);
    }

    @Override
    public String getName() {
        return "MatchRuleComparator";
    }

}
