package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import com.changhong.sei.rule.dto.RuleServiceMethodDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:服务方法定义服务测试
 * @date 2021/01/18 15:53
 */
public class RuleServiceMethodControllerTest extends BaseUnit5Test {

    @Autowired
    private RuleServiceMethodController controller;

    @Test
    void findOne() {
        String id = "123";
        ResultData<RuleServiceMethodDto> resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void save(){
        RuleServiceMethodDto dto = new RuleServiceMethodDto();
        dto.setRuleEntityTypeId("21E17C8F-562B-11EB-B2C8-3C6AA7266A51");
        dto.setMethod("test");
        dto.setName("test");
        dto.setPath("test");
        ResultData<RuleServiceMethodDto> resultData = controller.save(dto);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}
