package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.api.RuleAttributeApi;
import com.changhong.sei.rule.dto.RuleAttributeDto;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.service.RuleAttributeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规则属性定义(RuleAttribute)控制类
 *
 * @author sei
 * @since 2021-01-14 08:46:27
 */
@RestController
@Api(value = "RuleAttributeApi", tags = "规则属性定义服务")
@RequestMapping(path = "ruleAttribute", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleAttributeController extends BaseEntityController<RuleAttribute, RuleAttributeDto>
        implements RuleAttributeApi {
    /**
     * 规则属性定义服务对象
     */
    @Autowired
    private RuleAttributeService service;

    @Override
    public BaseEntityService<RuleAttribute> getService() {
        return service;
    }

    /**
     * 获取规则业务实体配置的属性清单
     *
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 属性清单
     */
    @Override
    public ResultData<List<RuleAttributeDto>> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return ResultData.success(convertToDtos(service.findByRuleEntityTypeId(ruleEntityTypeId)));
    }
}