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
}