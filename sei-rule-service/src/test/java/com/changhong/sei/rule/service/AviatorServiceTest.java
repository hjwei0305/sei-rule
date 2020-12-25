package com.changhong.sei.rule.service;

import com.changhong.sei.core.test.BaseUnitTest;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:
 * @date 2020/12/25 14:48
 */
public class AviatorServiceTest extends BaseUnitTest {

    @Test
    public void testExpression() {
        String expression = "a+(b-c)>100";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);
        Assert.assertTrue(result);
    }

    @Test
    public void testUserFunction() {
        Double result = (Double) AviatorEvaluator.execute("AddFunction(1, 2)");
        System.out.println(result);
        Assert.assertEquals(3.0d, result, 0.0);
    }

    @Test
    public void testMatchRuleComparatorFunction() {
        String expression = "MatchRuleComparator()";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 4225);
        env.put("appModuleCode", "sei-rule");
        env.put("path", "demoHello/matchRuleComparator");
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);
        Assert.assertTrue(result);
    }

}
