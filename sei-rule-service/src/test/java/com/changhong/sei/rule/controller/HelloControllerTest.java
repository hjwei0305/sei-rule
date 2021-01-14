package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现功能: Hello 单元测试
 */
class HelloControllerTest extends BaseUnit5Test {
    @Autowired
    private HelloController controller;

    @Test
    public void sayHello() {
        String name = "程序员";
        ResultData<String> result = controller.sayHello(name);
        System.out.println(JsonUtils.toJson(result));
    }
}