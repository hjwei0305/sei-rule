package com.changhong.sei.rule.service.bo;

import com.changhong.sei.rule.entity.RuleServiceMethod;
import com.changhong.sei.rule.sdk.dto.RuleReturnEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:规则链
 * @date 2021/01/18 15:26
 */
public class RuleChain implements Serializable {
    private static final long serialVersionUID = -1494565377707568667L;
    /**
     * 表达式
     */
    private String expression;

    /**
     * 结果节点Id
     */
    private String ruleTreeNodeId;

    /**
     * 结果节点名称
     */
    private String ruleTreeNodeName;

    /**
     * 规则返回结果（返回一个字符串常量）
     */
    private String returnConstant;
    /**
     * 返回对象
     */
    private List<RuleReturnEntity> returnEntities;

    /**
     * 执行方法
     */
    private RuleServiceMethod ruleServiceMethod;

    /**
     * 执行方法是异步执行
     */
    private Boolean asyncExecute = Boolean.FALSE;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getRuleTreeNodeId() {
        return ruleTreeNodeId;
    }

    public void setRuleTreeNodeId(String ruleTreeNodeId) {
        this.ruleTreeNodeId = ruleTreeNodeId;
    }

    public String getRuleTreeNodeName() {
        return ruleTreeNodeName;
    }

    public void setRuleTreeNodeName(String ruleTreeNodeName) {
        this.ruleTreeNodeName = ruleTreeNodeName;
    }

    public String getReturnConstant() {
        return returnConstant;
    }

    public void setReturnConstant(String returnConstant) {
        this.returnConstant = returnConstant;
    }

    public List<RuleReturnEntity> getReturnEntities() {
        return returnEntities;
    }

    public void setReturnEntities(List<RuleReturnEntity> returnEntities) {
        this.returnEntities = returnEntities;
    }

    public RuleServiceMethod getRuleServiceMethod() {
        return ruleServiceMethod;
    }

    public void setRuleServiceMethod(RuleServiceMethod ruleServiceMethod) {
        this.ruleServiceMethod = ruleServiceMethod;
    }

    public Boolean getAsyncExecute() {
        return asyncExecute;
    }

    public void setAsyncExecute(Boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
    }
}
