package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.api.RuleServiceMethodApi;
import com.changhong.sei.rule.dto.RuleReturnTypeDto;
import com.changhong.sei.rule.dto.RuleServiceMethodDto;
import com.changhong.sei.rule.entity.RuleReturnType;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import com.changhong.sei.rule.service.RuleServiceMethodService;
import io.swagger.annotations.Api;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 服务方法定义(RuleServiceMethod)控制类
 *
 * @author sei
 * @since 2021-01-14 09:12:04
 */
@RestController
@Api(value = "RuleServiceMethodApi", tags = "服务方法定义服务")
@RequestMapping(path = "ruleServiceMethod", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleServiceMethodController extends BaseEntityController<RuleServiceMethod, RuleServiceMethodDto>
        implements RuleServiceMethodApi {
    /**
     * 服务方法定义服务对象
     */
    @Autowired
    private RuleServiceMethodService service;

    @Override
    public BaseEntityService<RuleServiceMethod> getService() {
        return service;
    }

    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<RuleServiceMethod, RuleServiceMethodDto> propertyMap = new PropertyMap<RuleServiceMethod, RuleServiceMethodDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setRuleEntityTypeId(source.getRuleEntityTypeId());
            }
        };
        // 添加映射器
        dtoModelMapper.addMappings(propertyMap);
    }

    /**
     * 获取规则业务实体配置的服务方法清单
     *
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 服务方法清单
     */
    @Override
    public ResultData<List<RuleServiceMethodDto>> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return ResultData.success(convertToDtos(service.findByRuleEntityTypeId(ruleEntityTypeId)));
    }
}