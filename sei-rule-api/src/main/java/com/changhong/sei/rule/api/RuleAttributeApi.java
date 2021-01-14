package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleAttributeDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 规则属性定义(RuleAttribute)API
 *
 * @author sei
 * @since 2021-01-14 08:46:40
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleAttribute")
public interface RuleAttributeApi extends BaseEntityApi<RuleAttributeDto> {
    /**
     * 获取规则业务实体配置的属性清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 属性清单
     */
    @GetMapping(path = "findByRuleEntityTypeId")
    @ApiOperation(value = "获取规则业务实体配置的属性清单", notes = "通过业务实体类型Id,获取配置的属性清单")
    ResultData<List<RuleAttributeDto>> findByRuleEntityTypeId(@RequestParam("ruleEntityTypeId") String ruleEntityTypeId);
}