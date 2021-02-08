package com.changhong.sei.rule.dto.engine;

import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 实现功能: 规则测试执行请求
 *
 * @author 王锦光 wangjg
 * @version 2021-02-08 14:16
 */
@ApiModel("规则测试执行请求")
public class TestRunRequest extends RuleRunRequest {
    private static final long serialVersionUID = 6805579643935479052L;
    /**
     * 执行服务方法
     */
    @NotNull
    @ApiModelProperty(value = "执行服务方法", notes = "测试需要执行服务方法, 默认值为FALSE")
    private Boolean executeMethod = Boolean.FALSE;

    public Boolean getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(Boolean executeMethod) {
        this.executeMethod = executeMethod;
    }
}
