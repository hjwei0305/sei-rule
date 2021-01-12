package com.changhong.sei.rule.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.api.HelloApi;
import com.changhong.sei.rule.service.HelloService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import io.swagger.annotations.Api;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现功能: 你好的API服务实现
 */
@RestController
@RefreshScope
@Api(value = "HelloApi", tags = "调试你好的API服务")
@RequestMapping(path = "demoHello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HelloController implements HelloApi {
    @Autowired
    private HelloService service;

    @Value("${demo.test-key:123456}")
    private String testKey;

    /**
     * 你好
     *
     * @param name 姓名
     * @return 返回句子
     */
    @Override
    public ResultData<String> sayHello(String name) {
        try {
            SessionUser sessionUser = ContextUtil.getSessionUser();
            LogUtil.bizLog(JsonUtils.toJson(sessionUser));
            String data = service.sayHello(name, testKey);
            return ResultData.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.fail("你好说失败了！" + e.getMessage());
        }
    }

    /**
     * say hello 无返回参数
     *
     * @param name name
     * @return hello name
     */
    @Override
    public void sayVoid(String name) {
        String data = service.sayHello(name, testKey);
        LogUtil.bizLog("已经执行了说你好的方法！" + data);
    }

    @Override
    public ResultData<Boolean> compare(Map<String, Object> env) {
        return ResultData.success(MapUtils.getDouble(env, "a") > MapUtils.getDouble(env, "b"));
    }

    @Override
    public Boolean compare(Float a, Float b) {
        String expression = "MatchRuleComparator('sei-rule','demoHello/matchRuleComparator')";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", a);
        env.put("b", b);
        // 执行表达式
        return (Boolean) compiledExp.execute(env);
    }

    @Override
    public Boolean isMyCompany(Object value) {
        String expression = "object.company.code == '10044'";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("object", value);
        // 执行表达式
        return (Boolean) compiledExp.execute(env);
    }

    @Override
    public void testSpeed(){
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
        boolean result = (Boolean) compiledExp.execute(env);
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
    }

    @Override
    public void testSpeed2(){
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
    }
}
