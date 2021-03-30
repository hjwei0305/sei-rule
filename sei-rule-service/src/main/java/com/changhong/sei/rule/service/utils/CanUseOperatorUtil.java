package com.changhong.sei.rule.service.utils;

import com.changhong.sei.rule.dto.engine.CanUseOperator;
import com.changhong.sei.rule.dto.engine.RuleFunction;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 获取可以使用的运算符工具类
 *
 * @author 王锦光 wangjg
 * @version 2021-01-19 10:26
 */
public class CanUseOperatorUtil {
    private final static List<RuleFunction> FUNCTIONS;
    static {
        FUNCTIONS = new LinkedList<>();
        FUNCTIONS.add(new RuleFunction("isBlank", "是空字符串"));
        FUNCTIONS.add(new RuleFunction("isNotBlank", "非空字符串"));
    }
    /**
     * 通过规则属性类型获取可用运算符
     * @param ruleAttributeType 规则属性类型
     * @return 用运算符
     */
    public static List<CanUseOperator> getOperators(RuleAttributeType ruleAttributeType) {
        List<CanUseOperator> operators = new LinkedList<>();
        switch (ruleAttributeType) {
            case STRING:
                operators.add(new CanUseOperator(ComparisonOperator.CONTAIN));
                operators.add(new CanUseOperator(ComparisonOperator.EQUAL));
                operators.add(new CanUseOperator(ComparisonOperator.MATCH));
                operators.add(new CanUseOperator(ComparisonOperator.NOTEQUAL));
                break;
            case NUMBER:
                operators.add(new CanUseOperator(ComparisonOperator.EQUAL));
                operators.add(new CanUseOperator(ComparisonOperator.GREATER));
                operators.add(new CanUseOperator(ComparisonOperator.GREATER_EQUAL));
                operators.add(new CanUseOperator(ComparisonOperator.LESS));
                operators.add(new CanUseOperator(ComparisonOperator.LESS_EQUAL));
                operators.add(new CanUseOperator(ComparisonOperator.NOTEQUAL));
                break;
            case BOOLEAN:
                operators.add(new CanUseOperator(ComparisonOperator.EQUAL));
                operators.add(new CanUseOperator(ComparisonOperator.NOTEQUAL));
                break;
            case DATETIME:
                operators.add(new CanUseOperator(ComparisonOperator.EQUAL));
                operators.add(new CanUseOperator(ComparisonOperator.GREATER));
                operators.add(new CanUseOperator(ComparisonOperator.GREATER_EQUAL));
                operators.add(new CanUseOperator(ComparisonOperator.LESS));
                operators.add(new CanUseOperator(ComparisonOperator.LESS_EQUAL));
                break;
            default:
                break;
        }
        // 公共操作符
        operators.add(new CanUseOperator(ComparisonOperator.COMPARER));
        return operators;
    }

    /**
     * 通过规则属性类型获取可用函数
     * @param ruleAttributeType 规则属性类型
     * @return 用运算符
     */
    public static List<RuleFunction> getFunctions(RuleAttributeType ruleAttributeType) {
        return FUNCTIONS.stream().filter(f -> f.getRuleAttributeType() == ruleAttributeType).collect(Collectors.toList());
    }
}
