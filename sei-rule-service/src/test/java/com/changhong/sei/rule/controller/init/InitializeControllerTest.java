package com.changhong.sei.rule.controller.init;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnit5Test;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.util.EnumUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:13
 */
class InitializeControllerTest extends BaseUnit5Test {
    @Autowired
    private InitializeController controller;

    @Test
    void getInitializeTasks() {
        ResultData<?> resultData = controller.getInitializeTasks();
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void performTask() {
        ResultData<?> resultData = controller.performTask(2);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}