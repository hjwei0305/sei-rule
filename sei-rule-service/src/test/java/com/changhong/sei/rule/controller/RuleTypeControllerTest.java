package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import com.changhong.sei.rule.dto.RuleTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能: 规则类型单元测试
 *
 * @author 王锦光 wangjg
 * @version 2021-01-13 16:25
 */
class RuleTypeControllerTest extends BaseUnitTest {
    @Autowired
    private RuleTypeController controller;

    @Test
    void findOne() {
        String id = "123";
        ResultData<RuleTypeDto> resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}