package com.changhong.sei.rule.sdk.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 实现功能: 规则返回的实体
 *
 * @author 王锦光 wangjg
 * @version 2021-01-16 21:13
 */
@ApiModel("规则返回的实体")
public class RuleReturnEntity implements Serializable {
    private static final long serialVersionUID = 45697932739016187L;
    /**
     * 实体Id标识
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "实体Id标识", required = true)
    private String id;

    /**
     * 实体名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "实体名称", required = true)
    private String name;

    public RuleReturnEntity() {
    }

    public RuleReturnEntity(@NotBlank @Size(max = 36) String id, @NotBlank @Size(max = 100) String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
