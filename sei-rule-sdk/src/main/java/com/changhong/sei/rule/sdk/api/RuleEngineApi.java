package com.changhong.sei.rule.sdk.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.sdk.dto.RuleReturnEntity;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

/**
 * 实现功能: 规则引擎API服务接口
 *
 * @author 王锦光 wangjg
 * @version 2021-01-17 10:41
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleEngine")
public interface RuleEngineApi {
    /**
     * 执行规则
     * @param request 规则执行请求
     * @return 规则执行结果
     */
    @PostMapping(path = "run", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "执行规则", notes = "通过规则类型码和业务实体JSON，来执行一个规则，返回执行结果")
    ResultData<Map<String, RuleReturnEntity>> run(RuleRunRequest request);
}
