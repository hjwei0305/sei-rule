package com.changhong.sei.rule.service.utils;

import com.changhong.sei.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
}