package com.changhong.sei.rule.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 规则业务实体(RuleEntityType)实体类
 *
 * @author sei
 * @since 2021-01-13 14:47:14
 */
@Entity
@Table(name = "rule_entity_type")
@DynamicInsert
@DynamicUpdate
public class RuleEntityType extends BaseAuditableEntity implements ICodeUnique {
    private static final long serialVersionUID = 7228765445477082669L;
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
     * 服务名
     */
    @Column(name = "service_name")
    private String serviceName;

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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}