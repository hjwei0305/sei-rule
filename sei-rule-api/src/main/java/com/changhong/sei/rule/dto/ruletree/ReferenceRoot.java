package com.changhong.sei.rule.dto.ruletree;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 实现功能: 参考创建的根节点信息
 *
 * @author 王锦光 wangjg
 * @version 2021-02-22 13:44
 */
@ApiModel("参考创建的根节点信息")
public class ReferenceRoot implements Serializable {
    private static final long serialVersionUID = 1429122367209716465L;
    /**
     * 参考的根节点Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "参考的根节点Id", required = true)
    private String referenceRootId;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    /**
     * 优先级
     */
    @NotNull
    @ApiModelProperty(value = "优先级", required = true)
    private Integer rank = 0;

    public String getReferenceRootId() {
        return referenceRootId;
    }

    public void setReferenceRootId(String referenceRootId) {
        this.referenceRootId = referenceRootId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
