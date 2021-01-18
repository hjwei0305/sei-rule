package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleTypeDto;
import com.changhong.sei.rule.dto.ruletree.RuleTypeTree;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * 规则类型(RuleType)API
 *
 * @author sei
 * @since 2021-01-13 16:15:32
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleType")
public interface RuleTypeApi extends BaseEntityApi<RuleTypeDto> {
    /**
     * 获取规则类型树清单
     * @return 规则类型树清单
     */
    @GetMapping(path = "getRuleTypeTrees")
    @ApiOperation(value = "获取规则类型树清单", notes = "获取所有业务实体配置了规则类型的属性结构")
    ResultData<List<RuleTypeTree>> getRuleTypeTrees();
}