package com.changhong.sei.rule.service.bo;

import com.changhong.sei.rule.entity.RuleServiceMethod;
import com.changhong.sei.rule.sdk.dto.RuleReturnEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:规则链
 * @date 2021/01/18 15:26
 */
public class RuleChain implements Serializable {

    /**
     * 表达式
     */
    private String expression;

    /**
     * 结果节点ID
     */
    private String ruleTreeNodeId;

    /**
     * 返回对象类名
     */
    private String returnEntityClass;

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

    public String getReturnEntityClass() {
        return returnEntityClass;
    }

    public void setReturnEntityClass(String returnEntityClass) {
        this.returnEntityClass = returnEntityClass;
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
