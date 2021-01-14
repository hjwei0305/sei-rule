package com.changhong.sei.rule.api;

import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 实现功能: 你好API接口
 */
@FeignClient(name = "sei-rule", path = "demoHello")
public interface HelloApi {
    /**
     * say hello
     *
     * @param name name
     * @return hello name
     */
    @GetMapping(path = "sayHello")
    @ApiOperation(value = "调试API接口说你好", notes = "备注说明调试API接口说你好")
    ResultData<String> sayHello(@RequestParam("name") String name);

    /**
     * say hello 无返回参数
     *
     * @param name name
     * @return hello name
     */
    @GetMapping(path = "sayVoid")
    @ApiOperation(value = "say hello 无返回参数", notes = "测试无返回参数的服务方法")
    void sayVoid(@RequestParam("name") String name);

    /**
     * 外部自定义规则匹配接口
     *
     * @param env 参数
     * @return
     */
    @PostMapping(path = "matchRuleComparator")
    @ApiOperation(value = "外部自定义规则匹配接口", notes = "外部自定义规则匹配接口")
    ResultData<Boolean> compare(@RequestBody Map<String, Object> env);

    /**
     * 比较两个数的大小
     *
     * @param a
     * @param b
     * @return
     */
    @GetMapping(path = "compare")
    @ApiOperation(value = "比较两个数的大小", notes = "比较两个数的大小")
    Boolean compare(@RequestParam("a") Float a, @RequestParam("b") Float b);

    /**
     * 是否是我的公司
     *
     * @param value
     * @return
     */
    @PostMapping(path = "isMyCompany")
    @ApiOperation(value = "比较两个数的大小", notes = "比较两个数的大小")
    Boolean isMyCompany(@RequestBody String value);

    @GetMapping(path = "testSpeed")
    void testSpeed();

    @GetMapping(path = "testSpeed2")
    void testSpeed2();
}
