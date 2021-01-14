package com.changhong.sei.rule.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 规则树节点返回结果(NodeReturnResult)实体类
 *
 * @author sei
 * @since 2021-01-13 17:18:44
 */
@Entity
@Table(name = "node_return_result")
@DynamicInsert
@DynamicUpdate
public class NodeReturnResult extends BaseAuditableEntity implements ITenant {
    private static final long serialVersionUID = -221320875576807774L;
    /**
     * 规则树根节点Id
     */
    @Column(name = "rule_tree_root_node_id")
    private String ruleTreeRootNodeId;
    /**
     * 规则树节点Id
     */
    @Column(name = "rule_tree_node_id")
    private String ruleTreeNodeId;
    /**
     * 返回结果类型Id
     */
    @Column(name = "rule_return_type_id")
    private String ruleReturnTypeId;
    /**
     * 返回结果类型
     */
    @ManyToOne
    @JoinColumn(name = "rule_return_type_id", insertable = false, updatable = false)
    private RuleReturnType ruleReturnType;
    /**
     * 返回对象Id
     */
    @Column(name = "return_value_id")
    private String returnValueId;
    /**
     * 返回对象名称
     */
    @Column(name = "return_value_name")
    private String returnValueName;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;

    public String getRuleTreeRootNodeId() {
        return ruleTreeRootNodeId;
    }

    public void setRuleTreeRootNodeId(String ruleTreeRootNodeId) {
        this.ruleTreeRootNodeId = ruleTreeRootNodeId;
    }

    public String getRuleTreeNodeId() {
        return ruleTreeNodeId;
    }

    public void setRuleTreeNodeId(String ruleTreeNodeId) {
        this.ruleTreeNodeId = ruleTreeNodeId;
    }

    public String getRuleReturnTypeId() {
        return ruleReturnTypeId;
    }

    public void setRuleReturnTypeId(String ruleReturnTypeId) {
        this.ruleReturnTypeId = ruleReturnTypeId;
    }

    public RuleReturnType getRuleReturnType() {
        return ruleReturnType;
    }

    public void setRuleReturnType(RuleReturnType ruleReturnType) {
        this.ruleReturnType = ruleReturnType;
    }

    public String getReturnValueId() {
        return returnValueId;
    }

    public void setReturnValueId(String returnValueId) {
        this.returnValueId = returnValueId;
    }

    public String getReturnValueName() {
        return returnValueName;
    }

    public void setReturnValueName(String returnValueName) {
        this.returnValueName = returnValueName;
    }

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}