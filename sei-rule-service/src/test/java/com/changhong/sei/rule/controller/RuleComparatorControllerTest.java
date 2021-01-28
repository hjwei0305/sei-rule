package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.RuleComparatorDto;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2021-01-28 9:32
 */
class RuleComparatorControllerTest extends BaseUnit5Test {
    @Autowired
    private RuleComparatorController controller;

    @Test
    void findByRuleEntityTypeId() {
        String ruleEntityTypeId = "21E17C8F-562B-11EB-B2C8-3C6AA7266A51";
        ResultData<List<RuleComparatorDto>> resultData = controller.findByRuleEntityTypeId(ruleEntityTypeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}