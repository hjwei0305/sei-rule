package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.TreeEntity;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.RuleCategory;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 业务匹配规则(MatchingRule)DTO类
 *
 * @author sei
 * @since 2020-08-14 09:22:29
 */
@ApiModel(description = "业务匹配规则DTO")
public class MatchingRuleDto extends BaseEntityDto implements TreeEntity<MatchingRuleDto> {
    private static final long serialVersionUID = -5831064847793118277L;
    /**
     * 代码
     */
    @ApiModelProperty(value = "代码")
    private String code;
    /**
     * 名称
     */
    @Size(max = 100)
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 父节点Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "父节点Id")
    private String parentId;
    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Integer nodeLevel = 0;
    /**
     * 代码路径
     */
    @ApiModelProperty(value = "代码路径")
    private String codePath;
    /**
     * 名称路径
     */
    @ApiModelProperty(value = "名称路径")
    private String namePath;
    /**
     * 规则分类
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "规则分类")
    private RuleCategory ruleCategory = RuleCategory.UNRECOGNIZE;
    /**
     * 属性代码
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "属性代码")
    private String propertyCode;
    /**
     * 属性名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "属性名称")
    private String propertyName;
    /**
     * 比较运算符
     */
    @NotNull
    @JsonSerialize(using = EnumJsonSerializer.class)
    @ApiModelProperty(value = "比较运算符")
    private ComparisonOperator comparisonOperator = ComparisonOperator.EQUAL;
    /**
     * 比较值
     */
    @NotBlank
    @Size(max = 200)
    @ApiModelProperty(value = "比较值")
    private String comparisonValue;
    /**
     * 无需认款
     */
    @NotNull
    @ApiModelProperty(value = "无需认款")
    private Boolean unrecognize = Boolean.FALSE;
    /**
     * 是否冻结
     */
    @NotNull
    @ApiModelProperty(value = "是否冻结")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 优先级
     */
    @NotNull
    @ApiModelProperty(value = "优先级")
    private Integer rank = 0;
    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /**
     * 子节点列表
     */
    @ApiModelProperty("子节点列表")
    private List<MatchingRuleDto> children;

        
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

    public Boolean getUnrecognize() {
        return unrecognize;
    }

    public void setUnrecognize(Boolean unrecognize) {
        this.unrecognize = unrecognize;
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

    @Override
    public List<MatchingRuleDto> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<MatchingRuleDto> children) {
        this.children = children;
    }


}