package com.changhong.sei.rule.service.aviator.function;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import com.changhong.sei.rule.service.aviator.cache.SimpleCache;
import com.changhong.sei.rule.service.exception.RuleEngineException;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.changhong.sei.rule.service.aviator.AviatorExpressionService.RULE_CHAIN_PARAM_PREFIX;
import static com.changhong.sei.rule.service.aviator.AviatorExpressionService.RULE_TYPE_CODE;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:外部自定义Aviator函数
 * @date 2020/12/25 15:09
 */
@Component("MatchRuleComparator")
public class MatchRuleComparatorFunction extends AbstractFunction {

    /**
     * 单次调用（同一线程）外部比较器缓存
     */
    private final ThreadLocal<SimpleCache<String, Boolean>> cacheHolder = ThreadLocal.withInitial(() -> new SimpleCache<>(new HashMap<>()));


    /**
     * 函数名称
     */
    public static final String MATCH_RULE_COMPARATOR_FUNCTION = "MatchRuleComparator";

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
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String appModuleCode = FunctionUtils.getStringValue(arg1, env);
        if (StringUtils.isEmpty(appModuleCode)) {
            //00002 = 访问外部服务模块代码不能为空！
            throw new RuleEngineException("00002");
        }
        String path = FunctionUtils.getStringValue(arg2, env);
        if (StringUtils.isEmpty(path)) {
            //00003 = 访问外部服务API路径不能为空！
            throw new RuleEngineException("00003");
        }
        //先从缓存中获取
        SimpleCache<String, Boolean> cache = cacheHolder.get();
        String cacheKey = appModuleCode + ":" + path;
        Boolean cacheResult = cache.get(cacheKey);
        if (Objects.nonNull(cacheResult)) {
            return AviatorBoolean.valueOf(cacheResult);
        } else {
            //获取传入的参数
            String param = JsonUtils.toJson(env.get(RULE_CHAIN_PARAM_PREFIX));
            String ruleTypeCode = String.valueOf(env.get(RULE_TYPE_CODE));
            RuleRunRequest request = new RuleRunRequest();
            request.setRuleEntityJson(param);
            request.setRuleTypeCode(ruleTypeCode);
            try {
                ResultData<Boolean> apiResult = apiTemplate.postByAppModuleCode(appModuleCode, path, ResultData.class, request);
                if (apiResult.successful()) {
                    cache.put(cacheKey, apiResult.getData());
                    return AviatorBoolean.valueOf(apiResult.getData());
                } else {
                    // 访问外部服务模块{0}，path={1}自定义方法返回失败:[message={2}]，请查看应用模块[{0}]的异常日志！
                    throw new RuleEngineException("00001", appModuleCode, path, apiResult.getMessage());
                }
            } catch (Exception e) {
                LogUtil.error("自定义比较器出现异常:" + e.getMessage(), e);
                throw new RuleEngineException("自定义比较器出现异常:" + e.getMessage());
            }

        }
    }

    @Override
    public String getName() {
        return MATCH_RULE_COMPARATOR_FUNCTION;
    }

}
