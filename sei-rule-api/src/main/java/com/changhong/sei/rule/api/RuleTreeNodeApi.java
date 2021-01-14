package com.changhong.sei.rule.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
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
}