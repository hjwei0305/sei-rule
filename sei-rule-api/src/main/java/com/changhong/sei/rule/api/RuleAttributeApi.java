package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleAttributeDto;
import com.changhong.sei.rule.dto.engine.CanUseOperator;
import com.changhong.sei.util.EnumUtils;
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
     * 获取属性类型枚举值
     * @return 枚举值清单
     */
    @GetMapping(path = "getRuleAttributeTypeEnum")
    @ApiOperation(value = "获取属性类型枚举值", notes = "获取属性类型枚举值数据清单")
    ResultData<List<EnumUtils.EnumEntity>> getRuleAttributeTypeEnum();

    /**
     * 获取规则业务实体配置的属性清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 属性清单
     */
    @GetMapping(path = "findByRuleEntityTypeId")
    @ApiOperation(value = "获取规则业务实体配置的属性清单", notes = "通过业务实体类型Id,获取配置的属性清单")
    ResultData<List<RuleAttributeDto>> findByRuleEntityTypeId(@RequestParam("ruleEntityTypeId") String ruleEntityTypeId);

    /**
     * 获取指定属性可以进行比较的其他属性
     * @param ruleAttributeId 规则属性Id
     * @return 属性清单
     */
    @GetMapping(path = "findByRuleAttributeId")
    @ApiOperation(value = "获取指定属性可以进行比较的其他属性", notes = "通过指定的规则属性Id,获取可以进行比较的其他属性")
    ResultData<List<RuleAttributeDto>> findByRuleAttributeId(@RequestParam("ruleAttributeId") String ruleAttributeId);

    /**
     * 通过规则属性获取可使用的运算符
     * @param ruleAttributeId 规则属性Id
     * @return 可使用的运算符
     */
    @GetMapping(path = "getCanUseOperators")
    @ApiOperation(value = "通过规则属性获取可使用的运算符", notes = "通过规则属性Id,获取可使用的运算符以及是否需要属性和匹配值")
    ResultData<List<CanUseOperator>> getCanUseOperators(@RequestParam("ruleAttributeId") String ruleAttributeId);
}