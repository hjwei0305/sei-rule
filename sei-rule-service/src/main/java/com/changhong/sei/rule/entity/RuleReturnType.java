package com.changhong.sei.rule.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 返回结果定义(RuleReturnType)实体类
 *
 * @author sei
 * @since 2021-01-13 15:55:46
 */
@Entity
@Table(name = "rule_return_type")
@DynamicInsert
@DynamicUpdate
public class RuleReturnType extends BaseAuditableEntity {
    private static final long serialVersionUID = -2997202066472400974L;

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
     * UI组件
     */
    @Column(name = "ui_component")
    private String uiComponent;
    /**
     * 获取数据的url
     */
    @Column(name = "find_data_url")
    private String findDataUrl;

    public RuleReturnType() {
    }

    public RuleReturnType(String ruleEntityTypeId, String code, String name, String uiComponent, String findDataUrl) {
        this.ruleEntityTypeId = ruleEntityTypeId;
        this.code = code;
        this.name = name;
        this.uiComponent = uiComponent;
        this.findDataUrl = findDataUrl;
    }

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

    public String getUiComponent() {
        return uiComponent;
    }

    public void setUiComponent(String uiComponent) {
        this.uiComponent = uiComponent;
    }

    public String getFindDataUrl() {
        return findDataUrl;
    }

    public void setFindDataUrl(String findDataUrl) {
        this.findDataUrl = findDataUrl;
    }
}