package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.api.RuleTypeApi;
import com.changhong.sei.rule.dto.RuleTypeDto;
import com.changhong.sei.rule.entity.RuleType;
import com.changhong.sei.rule.service.RuleTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规则类型(RuleType)控制类
 *
 * @author sei
 * @since 2021-01-13 16:15:14
 */
@RestController
@Api(value = "RuleTypeApi", tags = "规则类型服务")
@RequestMapping(path = "ruleType", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleTypeController extends BaseEntityController<RuleType, RuleTypeDto>
        implements RuleTypeApi {
    /**
     * 规则类型服务对象
     */
    @Autowired
    private RuleTypeService service;

    @Override
    public BaseEntityService<RuleType> getService() {
        return service;
    }

}