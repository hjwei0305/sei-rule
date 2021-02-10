package com.changhong.sei.rule.service.utils;

import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 实现功能: 日期转换工具类
 *
 * @author 王锦光 wangjg
 * @version 2021-02-10 19:34
 */
public class DateParseUtil {
    /**
     * 将匹配值转换为日期型
     * @param comparisonValue 匹配值
     * @return 日期
     */
    public static Date parseToDate(String comparisonValue)  {
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.DEFAULT_TIME_FORMAT);
        try {
            return format.parse(comparisonValue);
        } catch (ParseException e) {
            format = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT);
            try {
                return format.parse(comparisonValue);
            } catch (ParseException parseException) {
                throw new ServiceException("日期类型的匹配值转换异常！【"+comparisonValue+"】");
            }
        }
    }
}
