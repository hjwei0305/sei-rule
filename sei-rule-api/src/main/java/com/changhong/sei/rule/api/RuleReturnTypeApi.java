package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import com.changhong.sei.rule.dto.RuleReturnTypeDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 返回结果定义(RuleReturnType)API
 *
 * @author sei
 * @since 2021-01-14 08:56:22
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleReturnType")
public interface RuleReturnTypeApi extends BaseEntityApi<RuleReturnTypeDto> {
    /**
     * 获取规则业务实体配置的返回结果类型清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 返回结果类型清单
     */
    @GetMapping(path = "findByRuleEntityTypeId")
    @ApiOperation(value = "获取规则业务实体配置的返回结果类型清单", notes = "通过业务实体类型Id,获取配置的返回结果类型清单")
    ResultData<List<RuleReturnTypeDto>> findByRuleEntityTypeId(@RequestParam("ruleEntityTypeId") String ruleEntityTypeId);
}