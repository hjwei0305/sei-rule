package com.changhong.sei.rule.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    public String getRuleEntityTypeId() {
        return ruleEntityTypeId;
    }

    public void setRuleEntityTypeId(String ruleEntityTypeId) {
        this.ruleEntityTypeId = ruleEntityTypeId;
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