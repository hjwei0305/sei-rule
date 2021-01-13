package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 规则树节点(RuleTreeNode)API
 *
 * @author sei
 * @since 2021-01-13 16:30:16
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleTreeNode")
public interface RuleTreeNodeApi extends BaseEntityApi<RuleTreeNodeDto> {

}