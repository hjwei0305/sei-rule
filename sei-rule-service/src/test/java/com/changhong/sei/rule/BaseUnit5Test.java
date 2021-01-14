package com.changhong.sei.rule;

import com.changhong.sei.core.config.properties.mock.MockUserProperties;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.context.mock.MockUser;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.util.thread.ThreadLocalHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 实现功能: JUNIT5单元测试基类
 *
 * @author 王锦光 wangjg
 * @version 2021-01-14 16:20
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BaseUnit5Test {
    @Autowired
    public MockUserProperties properties;
    @Autowired
    public MockUser mockUser;

    @BeforeAll
    public static void setup() {
        // 初始化
        ThreadLocalHolder.begin();
        System.out.println("开始进入单元测试.......");
    }

    @BeforeEach
    public void mock() {
        SessionUser sessionUser = mockUser.mockUser(properties);
        System.out.println("当前模拟用户: " + sessionUser.toString());
    }


    @AfterAll
    public static void cleanup() {
        // 释放
        ThreadLocalHolder.end();
        System.out.println("单元测试资源释放.......");
    }
}
