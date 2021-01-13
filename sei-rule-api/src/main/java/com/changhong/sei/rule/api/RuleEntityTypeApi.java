package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 规则业务实体(RuleEntityType)API
 *
 * @author sei
 * @since 2021-01-13 14:55:12
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleEntityType")
public interface RuleEntityTypeApi extends BaseEntityApi<RuleEntityTypeDto> {

}