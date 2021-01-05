package com.changhong.sei.rule.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能: 规则匹配属性
 *
 * @author 王锦光 wangjg
 * @version 2020-08-21 9:19
 */
@ApiModel("规则匹配属性")
public class RuleProperty implements Serializable {
    private static final long serialVersionUID = -8498298133647620138L;
    /**
     * 属性代码
     */
    @ApiModelProperty("属性代码")
    private String code;

    /**
     * 属性名称
     */
    @ApiModelProperty("属性名称")
    private String name;


    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String note;

    public RuleProperty() {
    }

    public RuleProperty(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public RuleProperty(String code, String name, String note) {
        this.code = code;
        this.name = name;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
