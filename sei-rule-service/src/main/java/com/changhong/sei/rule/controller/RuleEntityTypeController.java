package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.api.RuleEntityTypeApi;
import com.changhong.sei.rule.dto.RuleEntityTypeDto;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.service.RuleEntityTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规则业务实体(RuleEntityType)控制类
 *
 * @author sei
 * @since 2021-01-13 14:47:33
 */
@RestController
@Api(value = "RuleEntityTypeApi", tags = "$tool.trim($!{tableInfo.comment})服务")
@RequestMapping(path = "ruleEntityType", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleEntityTypeController extends BaseEntityController<RuleEntityType, RuleEntityTypeDto>
        implements RuleEntityTypeApi {
    /**
     * 规则业务实体服务对象
     */
    @Autowired
    private RuleEntityTypeService service;

    @Override
    public BaseEntityService<RuleEntityType> getService() {
        return service;
    }

}