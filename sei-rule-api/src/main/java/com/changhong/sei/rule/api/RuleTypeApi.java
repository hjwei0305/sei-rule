package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.rule.dto.RuleTypeDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 规则类型(RuleType)API
 *
 * @author sei
 * @since 2021-01-13 16:15:32
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleType")
public interface RuleTypeApi extends BaseEntityApi<RuleTypeDto> {

}