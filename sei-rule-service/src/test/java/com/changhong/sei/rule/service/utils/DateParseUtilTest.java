package com.changhong.sei.rule.service.utils;

import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2021-02-10 19:39
 */
class DateParseUtilTest {

    @Test
    void parseToDate() {
        String value = "2021-01-20 12:20:10";
        Date date = DateParseUtil.parseToDate(value);
        System.out.println(DateUtils.formatTime(date));
    }

    @Test
    void mapJson() {
        Map<String, String> map = new HashMap<>();
        map.put("company.id", "001");
        map.put("company.code", "Q000");
        map.put("company.name", "四川虹信");
        System.out.println(JsonUtils.toJson(map));
    }
}