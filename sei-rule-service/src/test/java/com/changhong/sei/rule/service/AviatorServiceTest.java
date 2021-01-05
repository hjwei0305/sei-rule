package com.changhong.sei.rule.service;

import com.alibaba.fastjson.JSONObject;
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
        String expression = "MatchRuleComparator('sei-rule','demoHello/matchRuleComparator')";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 25);
        //env.put("appModuleCode", "sei-rule");
        //env.put("path", "demoHello/matchRuleComparator");
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);
        Assert.assertTrue(result);
    }

    @Test
    public void testSpeed() {
        Map<String, Object> env = new HashMap<>();
        env.put("a", 1);
        env.put("b", 4);
        env.put("c", 5);
        env.put("d", "123445");
        env.put("e", "234");
        long startTime = System.nanoTime();   //获取开始时间
        String expression = "a==1";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression,true);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        String expression2 = "b!=2";
        Expression compiledExp2 = AviatorEvaluator.compile(expression2,true);
        // 执行表达式
        Boolean result2 = (Boolean) compiledExp2.execute(env);
        String expression3 = "c>3";
        Expression compiledExp3 = AviatorEvaluator.compile(expression3,true);
        // 执行表达式
        Boolean result3 = (Boolean) compiledExp3.execute(env);
        String expression4 = "string.contains(d,e)";
        Expression compiledExp4 = AviatorEvaluator.compile(expression4,true);
        // 执行表达式
        Boolean result4 = (Boolean) compiledExp4.execute(env);

        long endTime = System.nanoTime(); //获取结束时间
        System.out.println("执行结果:" + (result && result2 && result3&&result4) + "，消耗时间:" + (endTime - startTime) / 1000 + "ms");

        startTime = System.nanoTime();   //获取开始时间
        expression = "a==1";
        // 编译表达式
        compiledExp = AviatorEvaluator.compile(expression,true);
        // 执行表达式
        result = (Boolean) compiledExp.execute(env);
        expression2 = "b!=2";
        compiledExp2 = AviatorEvaluator.compile(expression2,true);
        // 执行表达式
        result2 = (Boolean) compiledExp2.execute(env);
        expression3 = "c>3";
        compiledExp3 = AviatorEvaluator.compile(expression3,true);
        // 执行表达式
        result3 = (Boolean) compiledExp3.execute(env);
         expression4 = "string.contains(d,e)";
         compiledExp4 = AviatorEvaluator.compile(expression4,true);
        // 执行表达式
         result4 = (Boolean) compiledExp4.execute(env);
        endTime = System.nanoTime(); //获取结束时间
        System.out.println("执行结果:" + (result && result2 && result3&&result4) + "，消耗时间:" + (endTime - startTime) / 1000 + "ms");
    }

    @Test
    public void testSpeed2() {
        Map<String, Object> env = new HashMap<>();
        env.put("a", 1);
        env.put("b", 4);
        env.put("c", 5);
        env.put("d", "123445");
        env.put("e", "234");
        long startTime = System.nanoTime();   //获取开始时间
        String expression = "a==1&&b!=2&&c>3&&string.contains(d,e)";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        long endTime = System.nanoTime(); //获取结束时间
        System.out.println("执行结果:" + (result) + "，消耗时间:" + (endTime - startTime) / 1000 + "ms");

        startTime = System.nanoTime();   //获取开始时间
        expression = "a==1&&b!=2&&c>3&&string.contains(d,e)";
        // 编译表达式
        compiledExp = AviatorEvaluator.compile(expression, true);
        // 执行表达式
        result = (Boolean) compiledExp.execute(env);
        endTime = System.nanoTime(); //获取结束时间
        System.out.println("执行结果:" + (result) + "，消耗时间:" + (endTime - startTime) / 1000 + "ms");
    }

    }
