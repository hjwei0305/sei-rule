package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseTreeApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.MatchingRuleDto;
import com.changhong.sei.rule.dto.RuleProperty;
import com.changhong.sei.rule.dto.RuleTree;
import com.changhong.sei.rule.dto.enums.RuleCategory;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 业务匹配规则(MatchingRule)API
 *
 * @author sei
 * @since 2020-08-14 09:22:29
 */
@Valid
@FeignClient(name = "sei-rule", path = "matchingRule")
public interface MatchingRuleApi extends BaseTreeApi<MatchingRuleDto> {
    /**
     * 获取业务匹配规则分类枚举值
     * @return 枚举值清单
     */
    @GetMapping(path = "getRuleCategoryEnum")
    @ApiOperation(value = "获取业务匹配规则分类枚举值", notes = "获取业务匹配规则分类枚举值数据清单")
    ResultData<List<EnumUtils.EnumEntity>> getRuleCategoryEnum();

    /**
     * 获取比较运算符枚举值
     * @return 枚举值清单
     */
    @GetMapping(path = "getComparisonOperatorEnum")
    @ApiOperation(value = "获取比较运算符枚举值", notes = "获取比较运算符枚举值数据清单")
    ResultData<List<EnumUtils.EnumEntity>> getComparisonOperatorEnum();

    /**
     * 通过分类获取所有规则树
     * @param category 规则分类
     * @return 规则树集合
     */
    @GetMapping(path = "getRuleTrees")
    @ApiOperation(notes = "通过分类获取所有规则树", value = "通过分类获取所有业务匹配规则树")
    ResultData<List<MatchingRuleDto>> getRuleTrees(@RequestParam("category") RuleCategory category);

    /**
     * 通过公司和分类获取规则根节点
     * @param category 规则分类
     * @param corpCode 公司代码
     * @return 规则根节点
     */
    @GetMapping(path = "getRuleRootNodes")
    @ApiOperation(notes = "通过公司和分类获取规则根节点", value = "通过公司代码和规则分类，获取规则根节点清单")
    ResultData<List<MatchingRuleDto>> getRuleRootNodes(@RequestParam("category") RuleCategory category,
                                                       @RequestParam(value = "corpCode", required = false) String corpCode);

    /**
     * 保存业务规则树
     * @param ruleTree 业务规则树
     * @return 处理结果
     */
    @PostMapping(path = "saveRuleTree", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(notes = "保存业务规则树", value = "保存一个完整的业务规则树")
    ResultData<?> saveRuleTree(@RequestBody RuleTree ruleTree);

    /**
     * 删除业务规则树
     * @param rootId 根节点Id
     * @return 处理结果
     */
    @DeleteMapping(path = "deleteRuleTree/{rootId}")
    ResultData<?> deleteRuleTree(@PathVariable("rootId") String rootId);

    /**
     * 获取可以使用的规则匹配属性
     * @return 属性清单
     */
    @GetMapping(path = "getRuleProperties")
    @ApiOperation(notes = "获取可以使用的规则匹配属性", value = "获取认款明细可以使用的规则匹配属性")
    ResultData<List<RuleProperty>> getRuleProperties();
}