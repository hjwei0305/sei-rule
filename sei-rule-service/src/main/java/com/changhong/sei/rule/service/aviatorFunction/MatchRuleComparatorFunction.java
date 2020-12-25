package com.changhong.sei.rule.service.aviatorFunction;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.exception.ServiceException;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
     * @return Boolean 是否匹配成功
     */
    @Override
    @SuppressWarnings("unchecked")
    public AviatorObject call(Map<String, Object> env) {
        String appModuleCode = MapUtils.getString(env, "appModuleCode");
        if (StringUtils.isEmpty(appModuleCode)) {
            //00002 = 访问外部服务模块代码不能为空！
            throw new ServiceException(ContextUtil.getMessage("00002"));
        }
        String path = MapUtils.getString(env, "path");
        if (StringUtils.isEmpty(path)) {
            //00003 = 访问外部服务API路径不能为空！
            throw new ServiceException(ContextUtil.getMessage("00003"));
        }
        ResultData<Boolean> result = apiTemplate.postByAppModuleCode(appModuleCode, path, ResultData.class, env);
        if (result.successful()) {
            return AviatorBoolean.valueOf(result.getData());
        } else {
            // = 访问外部服务模块{0}，path={1}返回失败，请查看应用模块[{0}]的异常日志！
            throw new ServiceException(ContextUtil.getMessage("00001", appModuleCode, path));
        }

    }

    @Override
    public String getName() {
        return "MatchRuleComparator";
    }

}
