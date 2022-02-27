package com.changhong.sei.rule.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 比较器定义(RuleComparator)实体类
 *
 * @author sei
 * @since 2021-01-13 16:09:33
 */
@Entity
@Table(name = "rule_comparator")
@DynamicInsert
@DynamicUpdate
public class RuleComparator extends BaseAuditableEntity {
    private static final long serialVersionUID = -310876330514268595L;
    /**
     * 规则业务实体Id
     */
    @Column(name = "rule_entity_type_id")
    private String ruleEntityTypeId;
    /**
     * 规则业务实体
     */
    @ManyToOne
    @JoinColumn(name = "rule_entity_type_id", insertable = false, updatable = false)
    private RuleEntityType ruleEntityType;
    /**
     * 方法名
     */
    @Column(name = "method")
    private String method;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * API相对路径
     */
    @Column(name = "path")
    private String path;

    public RuleComparator() {
    }

    public RuleComparator(String ruleEntityType, String method, String name, String path) {
        this.ruleEntityTypeId = ruleEntityType;
        this.method = method;
        this.name = name;
        this.path = path;
    }

    public String getRuleEntityTypeId() {
        return ruleEntityTypeId;
    }

    public void setRuleEntityTypeId(String ruleEntityTypeId) {
        this.ruleEntityTypeId = ruleEntityTypeId;
    }

    public RuleEntityType getRuleEntityType() {
        return ruleEntityType;
    }

    public void setRuleEntityType(RuleEntityType ruleEntityType) {
        this.ruleEntityType = ruleEntityType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}