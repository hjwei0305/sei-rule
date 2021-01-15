package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.dto.RuleTypeDto;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能: 规则树单元测试
 *
 * @author 王锦光 wangjg
 * @version 2021-01-13 16:53
 */
class RuleTreeNodeControllerTest extends BaseUnit5Test {
    @Autowired
    private RuleTreeNodeController controller;

    @Test
    void findOne() {
        String id = "123";
        ResultData<RuleTreeNodeDto> resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void findRootNodes() {
        String ruleTypeId = "76E87452-562B-11EB-B2C8-3C6AA7266A51";
        ResultData<List<RuleTreeRoot>> resultData = controller.findRootNodes(ruleTypeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}