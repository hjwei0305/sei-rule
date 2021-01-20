package com.changhong.sei.rule.controller.engine;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import com.changhong.sei.rule.sdk.dto.RuleRunResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能: 规则引擎单元测试
 *
 * @author 王锦光 wangjg
 * @version 2021-01-17 15:59
 */
class RuleEngineControllerTest extends BaseUnit5Test {
    @Autowired
    private RuleEngineController controller;

    @Test
    void run() {
        RuleRunRequest request = new RuleRunRequest();
        request.setRuleTypeCode("beis-recongnize");
        String json = "{\"postscript\":\"保证金\",\"amount\":200,\"tradeDate\":\"2021-01-20 14:18:05\",\"enable\":true,\"idCard\":\"511621199301012355\"}";
        request.setRuleEntityJson(json);
        ResultData<RuleRunResponse> resultData = controller.run(request);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}