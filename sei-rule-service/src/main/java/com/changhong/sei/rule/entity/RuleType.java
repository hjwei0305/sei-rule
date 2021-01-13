package com.changhong.sei.rule.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 规则类型(RuleType)实体类
 *
 * @author sei
 * @since 2021-01-13 16:15:10
 */
@Entity
@Table(name = "rule_type")
@DynamicInsert
@DynamicUpdate
public class RuleType extends BaseAuditableEntity implements ITenant {
    private static final long serialVersionUID = 7951335567840214003L;
    /**
     * 规则业务实体Id
     */
    @Column(name = "rule_entity_type_id")
    private String ruleEntityTypeId;
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
     * 业务描述
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;

    public String getRuleEntityTypeId() {
        return ruleEntityTypeId;
    }

    public void setRuleEntityTypeId(String ruleEntityTypeId) {
        this.ruleEntityTypeId = ruleEntityTypeId;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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