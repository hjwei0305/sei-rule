package com.changhong.sei.rule.controller.engine;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.engine.TestRunRequest;
import com.changhong.sei.rule.dto.engine.TestRunResponse;
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

    @Test
    void testRun() {
        TestRunRequest request = new TestRunRequest();
        request.setRuleTypeCode("beis-reiminvoicecheckinfo-fpjcgz");
        String json = "{\n" +
                "\t\"buyer\": \"虹信智远软件有限公司\",\n" +
                "\t\"seller\": \"滴滴出行科技有限公司\",\n" +
                "\t\"ocrText\": \"*运输服务*客运服务费\",\n" +
                "\t\"corporation\": \"四川虹信软件股份有限公司\"\n" +
                "}";
        request.setRuleEntityJson(json);
        request.setExecuteMethod(Boolean.FALSE);
        ResultData<TestRunResponse> resultData = controller.testRun(request);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}