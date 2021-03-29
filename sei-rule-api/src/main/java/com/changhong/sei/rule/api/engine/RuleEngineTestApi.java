package com.changhong.sei.rule.api.engine;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.engine.TestRunRequest;
import com.changhong.sei.rule.dto.engine.TestRunResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 实现功能: 规则引擎测试API服务接口
 *
 * @author 王锦光 wangjg
 * @version 2021-02-08 14:40
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleEngineTest")
public interface RuleEngineTestApi {
    /**
     * 执行测试规则
     * @param request 规则执行请求
     * @return 规则执行结果
     */
    @PostMapping(path = "testRun", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "执行测试规则", notes = "通过规则类型码和业务实体JSON，来执行一个规则测试，返回执行结果")
    ResultData<TestRunResponse> testRun(@RequestBody @Valid TestRunRequest request);
}
