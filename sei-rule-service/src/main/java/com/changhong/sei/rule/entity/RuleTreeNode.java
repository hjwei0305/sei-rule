package com.changhong.sei.rule.entity;

import com.changhong.sei.core.dto.TreeEntity;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 规则树节点(RuleTreeNode)实体类
 *
 * @author sei
 * @since 2021-01-13 16:29:52
 */
@Entity
@Table(name = "rule_tree_node")
@DynamicInsert
@DynamicUpdate
public class RuleTreeNode extends BaseAuditableEntity implements TreeEntity<RuleTreeNode>, ITenant {
    private static final long serialVersionUID = -8039521837910367874L;
    /**
     * 代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 父节点Id
     */
    @Column(name = "parent_id")
    private String parentId;
    /**
     * 层级
     */
    @Column(name = "node_level")
    private Integer nodeLevel;
    /**
     * 代码路径
     */
    @Column(name = "code_path")
    private String codePath;
    /**
     * 名称路径
     */
    @Column(name = "name_path")
    private String namePath;
    /**
     * 优先级
     */
    @Column(name = "rank")
    private Integer rank = 0;
    /**
     * 规则类型Id
     */
    @Column(name = "rule_type_id")
    private String ruleTypeId;
    /**
     * 规则类型
     */
    @ManyToOne
    @JoinColumn(name = "rule_type_id", insertable = false, updatable = false)
    private RuleType ruleType;
    /**
     * 真节点
     */
    @Column(name = "true_node")
    private Boolean trueNode = Boolean.FALSE;
    /**
     * 规则结束
     */
    @Column(name = "finished")
    private Boolean finished = Boolean.FALSE;
    /**
     * 服务方法Id
     */
    @Column(name = "rule_service_method_id")
    private String ruleServiceMethodId;
    /**
     * 是异步执行
     */
    @Column(name = "async_execute")
    private Boolean asyncExecute = Boolean.FALSE;
    /**
     * 启用标识
     */
    @Column(name = "enabled")
    private Boolean enabled = Boolean.FALSE;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;
    /**
     * 子节点清单
     */
    @Transient
    private List<RuleTreeNode> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(String ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public Boolean getTrueNode() {
        return trueNode;
    }

    public void setTrueNode(Boolean trueNode) {
        this.trueNode = trueNode;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getRuleServiceMethodId() {
        return ruleServiceMethodId;
    }

    public void setRuleServiceMethodId(String ruleServiceMethodId) {
        this.ruleServiceMethodId = ruleServiceMethodId;
    }

    public Boolean getAsyncExecute() {
        return asyncExecute;
    }

    public void setAsyncExecute(Boolean asyncExecute) {
        this.asyncExecute = asyncExecute;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public List<RuleTreeNode> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<RuleTreeNode> children) {
        this.children = children;
    }
}