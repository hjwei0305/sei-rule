package com.changhong.sei.rule.entity;

import com.changhong.sei.core.dto.TreeEntity;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.DataType;
import com.changhong.sei.rule.dto.enums.RuleCategory;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 业务匹配规则(MatchingRule)实体类
 *
 * @author sei
 * @since 2020-08-14 09:22:20
 */
@Entity
@Table(name = "matching_rule")
@DynamicInsert
@DynamicUpdate
public class MatchingRule extends BaseAuditableEntity implements TreeEntity<MatchingRule>, ITenant {
    private static final long serialVersionUID = -5978606137465074013L;
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
     * 规则分类
     */
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = EnumJsonSerializer.class)
    @Column(name = "rule_category")
    private RuleCategory ruleCategory = RuleCategory.UNRECOGNIZE;

    /**
     * 属性代码
     */
    @Column(name = "property_code")
    private String propertyCode;
    /**
     * 属性名称
     */
    @Column(name = "property_name")
    private String propertyName;
    /**
     * 比较运算符
     */
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = EnumJsonSerializer.class)
    @Column(name = "comparison_operator")
    private ComparisonOperator comparisonOperator = ComparisonOperator.EQUAL;
    /**
     * 比较值
     */
    @Column(name = "comparison_value")
    private String comparisonValue;

    /**
     * 数据类型
     */
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = EnumJsonSerializer.class)
    @Column(name = "data_type")
    private DataType dataType = DataType.STRING;

    /**
     * 是否冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 优先级
     */
    @Column(name = "rank")
    private Integer rank = 0;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;

    /**
     * 根节点id
     */
    @Transient
    private String rootId;

    /**
     * 该节点表达式
     */
    @Transient
    private String expression;

    /**
     * 子节点列表
     */
    @Transient
    private List<MatchingRule> children;
        
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
        
    public RuleCategory getRuleCategory() {
        return ruleCategory;
    }

    public void setRuleCategory(RuleCategory ruleCategory) {
        this.ruleCategory = ruleCategory;
    }
        
    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }
        
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
        
    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }
        
    public String getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(String comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }
        
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
        
    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public List<MatchingRule> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<MatchingRule> children) {
        this.children = children;
    }
}