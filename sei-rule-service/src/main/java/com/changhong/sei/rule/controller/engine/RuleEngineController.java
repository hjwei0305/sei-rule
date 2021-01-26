package com.changhong.sei.rule.controller.engine;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.rule.sdk.api.RuleEngineApi;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import com.changhong.sei.rule.sdk.dto.RuleRunResponse;
import com.changhong.sei.rule.service.aviator.function.MatchRuleComparatorFunction;
import com.changhong.sei.rule.service.engine.RuleEngineService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @Autowired
    private RuleEngineService service;

    /**
     * 执行规则
     *
     * @param request 规则执行请求
     * @return 规则执行结果
     */
    @Override
    public ResultData<RuleRunResponse> run(@Valid RuleRunRequest request) {
        RuleRunResponse response;
        try {
            response = service.run(request);
        } catch (Exception e) {
            LogUtil.error("规则引擎执行异常:" + e.getMessage(), e);
            return ResultDataUtil.fail("规则引擎执行异常:" + e.getMessage());
        } finally {
            //清理掉自定义比较器的线程的缓存,避免web容器等线程池复用线程导致缓存不清理
            MatchRuleComparatorFunction.getCacheHolder().remove();
        }
        return ResultData.success(response);
    }
}
