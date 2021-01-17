package com.changhong.sei.rule.controller.engine;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.sdk.api.RuleEngineApi;
import com.changhong.sei.rule.sdk.dto.RuleReturnEntity;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import com.changhong.sei.rule.sdk.dto.RuleRunResponse;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现功能: 规则引擎OpenAPI服务实现
 *
 * @author 王锦光 wangjg
 * @version 2021-01-17 15:45
 */
@RestController
@Api(value = "RuleEngineApi", tags = "规则引擎OpenAPI服务实现")
@RequestMapping(path = "ruleEngine", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleEngineController implements RuleEngineApi {
    /**
     * 执行规则
     *
     * @param request 规则执行请求
     * @return 规则执行结果
     */
    @Override
    public ResultData<RuleRunResponse> run(@Valid RuleRunRequest request) {
        RuleRunResponse response = new RuleRunResponse();
        response.setMatched(Boolean.TRUE);
        response.setExecuted(Boolean.TRUE);
        Map<String, RuleReturnEntity> returnEntityMap = new HashMap<>();
        RuleReturnEntity returnEntity1 = new RuleReturnEntity("001", "认款业务类型1");
        RuleReturnEntity returnEntity2 = new RuleReturnEntity("002", "认款记账类型2");
        returnEntityMap.put("com.changhong.beis.entity.ItemBusinessType", returnEntity1);
        returnEntityMap.put("com.changhong.beis.entity.ItemAccountType", returnEntity2);
        response.setReturnEntityMap(returnEntityMap);
        return ResultData.success(response);
    }
}
