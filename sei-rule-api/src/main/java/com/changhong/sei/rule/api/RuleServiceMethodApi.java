package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleReturnTypeDto;
import com.changhong.sei.rule.dto.RuleServiceMethodDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 服务方法定义(RuleServiceMethod)API
 *
 * @author sei
 * @since 2021-01-14 09:12:15
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleServiceMethod")
public interface RuleServiceMethodApi extends BaseEntityApi<RuleServiceMethodDto> {
    /**
     * 获取规则业务实体配置的服务方法清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 服务方法清单
     */
    @GetMapping(path = "findByRuleEntityTypeId")
    @ApiOperation(value = "获取规则业务实体配置的服务方法清单", notes = "通过业务实体类型Id,获取配置的服务方法清单")
    ResultData<List<RuleServiceMethodDto>> findByRuleEntityTypeId(@RequestParam("ruleEntityTypeId") String ruleEntityTypeId);
}