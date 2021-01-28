package com.changhong.sei.rule.service;

import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.changhong.sei.rule.entity.RuleAttribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2021-01-28 10:05
 */
class RuleAttributeServiceTest extends BaseUnit5Test {
    @Autowired
    private RuleAttributeService service;

    @Test
    void save() {
        RuleAttribute ruleAttribute = new RuleAttribute();
        //ruleAttribute.setId("2F6ABAFD-5AE0-11EB-807C-0242C0A8462D");
        ruleAttribute.setRuleEntityTypeId("21E17C8F-562B-11EB-B2C8-3C6AA7266A51");
        ruleAttribute.setAttribute("tradeDate");
        ruleAttribute.setRuleAttributeType(RuleAttributeType.STRING);
        ruleAttribute.setUiComponent("UI01");
        ruleAttribute.setName("身份证");
        OperateResultWithData<RuleAttribute> result = service.save(ruleAttribute);
        System.out.println(result);
        Assertions.assertTrue(result.successful());
    }
}