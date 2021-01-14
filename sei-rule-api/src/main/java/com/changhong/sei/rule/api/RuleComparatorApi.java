package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleComparatorDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 比较器定义(RuleComparator)API
 *
 * @author sei
 * @since 2021-01-14 09:19:38
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleComparator")
public interface RuleComparatorApi extends BaseEntityApi<RuleComparatorDto> {
    /**
     * 获取规则业务实体配置的比较器清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 比较器清单
     */
    @GetMapping(path = "findByRuleEntityTypeId")
    @ApiOperation(value = "获取规则业务实体配置的比较器清单", notes = "通过业务实体类型Id,获取配置的比较器清单")
    ResultData<List<RuleComparatorDto>> findByRuleEntityTypeId(@RequestParam("ruleEntityTypeId") String ruleEntityTypeId);
}