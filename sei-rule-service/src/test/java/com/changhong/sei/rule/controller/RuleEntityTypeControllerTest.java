package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能: 单元测试规则实体类型
 *
 * @author 王锦光 wangjg
 * @version 2021-01-13 14:59
 */
class RuleEntityTypeControllerTest extends BaseUnit5Test {
    @Autowired
    private RuleEntityTypeController controller;

    @Test
    void findOne() {
        String id = "123";
        ResultData<RuleEntityTypeDto> resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}