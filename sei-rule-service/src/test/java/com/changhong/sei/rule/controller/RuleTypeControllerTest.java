package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.RuleTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能: 规则类型单元测试
 *
 * @author 王锦光 wangjg
 * @version 2021-01-13 16:25
 */
public class RuleTypeControllerTest extends BaseUnit5Test {
    @Autowired
    private RuleTypeController controller;

    @Test
    void findOne() {
        String id = "76E87452-562B-11EB-B2C8-3C6AA7266A51";
        ResultData<RuleTypeDto> resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void save() {
        RuleTypeDto ruleTypeDto = new RuleTypeDto();
        ruleTypeDto.setRuleEntityTypeId("21E17C8F-562B-11EB-B2C8-3C6AA7266A51");
        ruleTypeDto.setName("银企互联无需认款规则");
        ResultData<RuleTypeDto> resultData = controller.save(ruleTypeDto);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}