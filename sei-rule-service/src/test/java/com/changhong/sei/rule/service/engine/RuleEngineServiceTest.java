package com.changhong.sei.rule.service.engine;

import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import com.changhong.sei.rule.service.aviator.function.IsBlankFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static com.changhong.sei.rule.service.aviator.AviatorExpressionService.RULE_CHAIN_PARAM_PREFIX;
import static com.changhong.sei.rule.service.aviator.AviatorExpressionService.RULE_TYPE_CODE;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2021-02-11 10:11
 */
class RuleEngineServiceTest extends BaseUnit5Test {
    @Autowired
    private RuleEngineService service;

    @Test
    void runNodeExpression() {
        RuleRunRequest request = new RuleRunRequest();
        request.setRuleTypeCode("beis-recongnize");
        String json = "{\"postscript\":\"租房保证金\",\"amount\":200,\"tradeDate\":\"2021-01-20 14:18:05\",\"enable\":true,\"idCard\":\"511621199301012355\"}";
        request.setRuleEntityJson(json);
        Map<String, Object> env = new HashMap<>();
        Object param = JsonUtils.fromJson(request.getRuleEntityJson(), Object.class);
        env.put(RULE_CHAIN_PARAM_PREFIX, param);
        env.put(RULE_TYPE_CODE, request.getRuleTypeCode());
        String nodeId = "2A86E17F-6C09-11EB-B40D-0242C0A8462D";
        boolean result = service.runNodeExpression(env, nodeId);
        Assertions.assertTrue(result);
    }
}