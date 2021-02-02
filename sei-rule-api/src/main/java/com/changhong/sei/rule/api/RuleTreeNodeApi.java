package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.dto.ruletree.RuleTree;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
     * 创建规则树根节点信息
     *
     * @param ruleTreeRoot 规则树根节点
     * @return 处理结果
     */
    @PostMapping(path = "createRootNode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "创建规则树根节点信息", notes = "创建规则树根节点信息:名称、优先级、启用等")
    ResultData<?> createRootNode(@RequestBody RuleTreeRoot ruleTreeRoot);

    /**
     * 更新规则树根节点信息
     *
     * @param ruleTreeRoot 规则树根节点
     * @return 处理结果
     */
    @PostMapping(path = "updateRootNode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新规则树根节点信息", notes = "更新规则树根节点信息:名称、优先级、启用")
    ResultData<?> updateRootNode(@RequestBody RuleTreeRoot ruleTreeRoot);

    /**
     * 获取比较运算符枚举值
     * @return 枚举值清单
     */
    @GetMapping(path = "getComparisonOperatorEnum")
    @ApiOperation(value = "获取比较运算符枚举值", notes = "获取比较运算符枚举值数据清单")
    ResultData<List<EnumUtils.EnumEntity>> getComparisonOperatorEnum();

    /**
     * 通过规则树根节点Id获取规则树
     * @param rootNodeId 根节点Id
     * @return 规则树
     */
    @GetMapping(path = "getRuleTree")
    @ApiOperation(notes = "通过规则树根节点Id获取规则树", value = "通过规则树根节点Id，获取规则树并包含子节点及其配置信息")
    ResultData<RuleTreeNodeDto> getRuleTree(@RequestParam("rootNodeId") String rootNodeId);

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

    /**
     * 删除业务规则树节点
     * @param nodeId 节点Id
     * @return 处理结果
     */
    @DeleteMapping(path = "deleteNode/{nodeId}")
    @ApiOperation(notes = "删除业务规则树节点", value = "删除业务规则树的一个节点，并且级联删除所有子节点")
    ResultData<?> deleteNode(@PathVariable("nodeId") String nodeId);
}