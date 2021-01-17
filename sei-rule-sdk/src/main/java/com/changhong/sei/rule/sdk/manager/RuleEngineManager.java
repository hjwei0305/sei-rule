package com.changhong.sei.rule.sdk.manager;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.sdk.api.RuleEngineApi;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import com.changhong.sei.rule.sdk.dto.RuleRunResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实现功能: 规则引擎SDK执行组件
 *
 * @author 王锦光 wangjg
 * @version 2021-01-17 15:36
 */
@Component
public class RuleEngineManager {
    @Autowired
    private RuleEngineApi ruleEngineApi;

    /**
     * 执行一个规则
     * @param request 规则执行请求
     * @return 规则执行结果
     */
    public ResultData<RuleRunResponse> run(RuleRunRequest request) {
        return ruleEngineApi.run(request);
    }
}
