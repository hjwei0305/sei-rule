package com.changhong.sei.rule.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 规则业务实体(RuleEntityType)DTO类
 *
 * @author sei
 * @since 2021-01-13 14:55:08
 */
@ApiModel(description = "$tool.trim($!{tableInfo.comment})DTO")
public class RuleEntityTypeDto extends BaseEntityDto {
    private static final long serialVersionUID = -6841562517354867788L;
    /**
     * 代码
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
    private String name;
    /**
     * 服务名
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "$tool.trim(${column.comment})", required = true)
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