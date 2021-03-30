package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.util.EnumUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2021-01-14 16:59
 */
class RuleAttributeControllerTest extends BaseUnit5Test {
    @Autowired
    private RuleAttributeController controller;

    @Test
    void getRuleAttributeTypeEnum() {
        ResultData<List<EnumUtils.EnumEntity>> resultData = controller.getRuleAttributeTypeEnum();
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void getCanUseOperators() {
        String id = "A68050C5-5633-11EB-8D6E-3C6AA7266A51";
        ResultData<?> resultData = controller.getCanUseOperators(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void findByRuleAttributeId() {
        String ruleAttributeId = "4655D862-69B5-11EB-850A-0242C0A8462D";
        ResultData<?> resultData = controller.findByRuleAttributeId(ruleAttributeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void getCanUseFunctions() {
        String id = "A68050C5-5633-11EB-8D6E-3C6AA7266A51";
        ResultData<?> resultData = controller.getCanUseFunctions(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}