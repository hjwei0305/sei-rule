package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.ruletree.RuleTree;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.ApiOperation;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 规则树节点(RuleTreeNode)API
 *
 * @author sei
 * @since 2021-01-13 16:30:16
 */
@Valid
@FeignClient(name = "sei-rule", path = "ruleTreeNode")
public interface RuleTreeNodeApi extends BaseEntityApi<RuleTreeNodeDto> {
    /**
     * 获取规则类型下配置的所有规则树
     * @param ruleTypeId 规则类型Id
     * @return 规则树根节点清单
     */
    @GetMapping(path = "findRootNodes")
    @ApiOperation(value = "获取规则类型下配置的所有规则树", notes = "通过规则类型Id获取配置的所有规则树根节点")
    ResultData<List<RuleTreeRoot>> findRootNodes(@RequestParam("ruleTypeId") String ruleTypeId);

    /**
     * 获取比较运算符枚举值
     * @return 枚举值清单
     */
    @GetMapping(path = "getComparisonOperatorEnum")
    @ApiOperation(value = "获取比较运算符枚举值", notes = "获取比较运算符枚举值数据清单")
    ResultData<List<EnumUtils.EnumEntity>> getComparisonOperatorEnum();

    /**
     * 通过规则类型ID获取所有规则树
     * @param ruleTypeId 规则类型ID
     * @return 规则树集合
     */
    @GetMapping(path = "getRuleTrees")
    @ApiOperation(notes = "通过规则类型ID获取所有规则树", value = "通过类型ID获取所有业务匹配规则树")
    ResultData<List<RuleTreeNodeDto>> getRuleTrees(@RequestParam("ruleTypeId") String ruleTypeId);

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
    @ApiOperation(notes = "删除业务规则树", value = "删除业务规则树")
    ResultData<?> deleteRuleTree(@PathVariable("rootId") String rootId);

}