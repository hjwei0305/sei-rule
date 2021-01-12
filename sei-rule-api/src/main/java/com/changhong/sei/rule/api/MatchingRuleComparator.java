package com.changhong.sei.rule.api;

import com.changhong.sei.core.dto.ResultData;

import java.util.Map;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:外部自定义规则匹配接口
 * @date 2020/12/25 16:16
 */
public interface MatchingRuleComparator {

    /**
     * 模块与url路径分隔符
     */
    public static final char SPLIT_CHAR = ':';
    /**
     * 正确的分割后数组长度
     */
    public static final Integer SPLIT_LENGTH = 2;

    /**
     * 规则计算
     *
     * @param env 环境参数
     * @return 匹配结果
     */
    ResultData<Boolean> compare(Map<String, Object> env);
}
