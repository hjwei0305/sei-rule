package com.changhong.sei.rule.dto.engine;

import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import com.changhong.sei.rule.sdk.dto.RuleRunResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实现功能: 规则测试执行结果
 *
 * @author 王锦光 wangjg
 * @version 2021-02-08 14:15
 */
@ApiModel("规则测试执行结果")
public class TestRunResponse extends RuleRunResponse {
    private static final long serialVersionUID = -8366606886652772965L;
    /**
     * 路由命中的规则
     */
    @ApiModelProperty(value = "路由命中的规则")
    private RuleTreeRoot ruleTreeRoot;

    public RuleTreeRoot getRuleTreeRoot() {
        return ruleTreeRoot;
    }

    public void setRuleTreeRoot(RuleTreeRoot ruleTreeRoot) {
        this.ruleTreeRoot = ruleTreeRoot;
    }
}
