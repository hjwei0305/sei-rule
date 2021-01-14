package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.api.RuleReturnTypeApi;
import com.changhong.sei.rule.dto.RuleReturnTypeDto;
import com.changhong.sei.rule.entity.RuleReturnType;
import com.changhong.sei.rule.service.RuleReturnTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 返回结果定义(RuleReturnType)控制类
 *
 * @author sei
 * @since 2021-01-14 08:56:10
 */
@RestController
@Api(value = "RuleReturnTypeApi", tags = "$tool.trim($!{tableInfo.comment})服务")
@RequestMapping(path = "ruleReturnType", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleReturnTypeController extends BaseEntityController<RuleReturnType, RuleReturnTypeDto>
        implements RuleReturnTypeApi {
    /**
     * 返回结果定义服务对象
     */
    @Autowired
    private RuleReturnTypeService service;

    @Override
    public BaseEntityService<RuleReturnType> getService() {
        return service;
    }

    /**
     * 获取规则业务实体配置的返回结果类型清单
     *
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 返回结果类型清单
     */
    @Override
    public ResultData<List<RuleReturnTypeDto>> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return ResultData.success(convertToDtos(service.findByRuleEntityTypeId(ruleEntityTypeId)));
    }
}