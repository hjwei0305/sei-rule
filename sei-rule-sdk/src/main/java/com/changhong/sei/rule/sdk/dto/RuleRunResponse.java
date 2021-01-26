package com.changhong.sei.rule.sdk.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 实现功能: 规则执行结果
 *
 * @author 王锦光 wangjg
 * @version 2021-01-17 15:51
 */
@ApiModel("规则执行结果")
public class RuleRunResponse implements Serializable {
    private static final long serialVersionUID = 9105930593111033729L;

    /**
     * 规则匹配成功
     */
    @NotNull
    @ApiModelProperty(value = "规则匹配成功", required = true)
    private Boolean matched = Boolean.FALSE;

    /**
     * 规则匹配成功的节点id
     */
    @NotNull
    @ApiModelProperty(value = "规则匹配成功的节点id", required = true)
    private String matchedNodeId;

    /**
     * 方法执行成功
     */
    @NotNull
    @ApiModelProperty(value = "方法执行成功", required = true)
    private Boolean executed = Boolean.FALSE;

    /**
     * 返回的实体MAP
     */
    @ApiModelProperty(value = "返回的实体", notes = "返回的实体MAP,key为返回结果类型的代码(全类名)")
    private Map<String, RuleReturnEntity> returnEntityMap;


    public Boolean getMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public String getMatchedNodeId() {
        return matchedNodeId;
    }

    public void setMatchedNodeId(String matchedNodeId) {
        this.matchedNodeId = matchedNodeId;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    public Map<String, RuleReturnEntity> getReturnEntityMap() {
        return returnEntityMap;
    }

    public void setReturnEntityMap(Map<String, RuleReturnEntity> returnEntityMap) {
        this.returnEntityMap = returnEntityMap;
    }
}
