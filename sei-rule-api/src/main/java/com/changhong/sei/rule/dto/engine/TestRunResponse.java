package com.changhong.sei.rule.dto.engine;

import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import com.changhong.sei.rule.sdk.dto.RuleRunResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 实现功能: 规则测试执行结果
 *
 * @author 王锦光 wangjg
 * @version 2021-02-08 14:15
 */
@ApiModel("规则测试执行结果")
public class TestRunResponse implements Serializable {
    private static final long serialVersionUID = 359446511710079526L;
    /**
     * 路由命中的规则
     */
    @ApiModelProperty(value = "路由命中的规则")
    private RuleTreeRoot ruleTreeRoot;

    /**
     * 匹配成功的规则链执行结果清单
     */
    @ApiModelProperty("匹配成功的规则链执行结果清单")
    private List<RuleRunResponse> responses;

    public RuleTreeRoot getRuleTreeRoot() {
        return ruleTreeRoot;
    }

    public void setRuleTreeRoot(RuleTreeRoot ruleTreeRoot) {
        this.ruleTreeRoot = ruleTreeRoot;
    }

    public List<RuleRunResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<RuleRunResponse> responses) {
        this.responses = responses;
    }
}
