package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.rule.api.RuleAttributeApi;
import com.changhong.sei.rule.dto.RuleAttributeDto;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.dto.engine.CanUseOperator;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.service.RuleAttributeService;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.modelmapper.PropertyMap;
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
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<RuleAttribute, RuleAttributeDto> propertyMap = new PropertyMap<RuleAttribute, RuleAttributeDto>() {
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
     * 获取属性类型枚举值
     *
     * @return 枚举值清单
     */
    @Override
    public ResultData<List<EnumUtils.EnumEntity>> getRuleAttributeTypeEnum() {
        return ResultDataUtil.getEnumEntities(RuleAttributeType.class);
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

    /**
     * 获取指定属性可以进行比较的其他属性
     *
     * @param ruleAttributeId 规则属性Id
     * @return 属性清单
     */
    @Override
    public ResultData<List<RuleAttributeDto>> findByRuleAttributeId(String ruleAttributeId) {
        return ResultData.success(convertToDtos(service.findByRuleAttributeId(ruleAttributeId)));
    }

    /**
     * 通过规则属性获取可使用的运算符
     *
     * @param ruleAttributeId 规则属性Id
     * @return 可使用的运算符
     */
    @Override
    public ResultData<List<CanUseOperator>> getCanUseOperators(String ruleAttributeId) {
        return ResultData.success(service.getCanUseOperators(ruleAttributeId));
    }
}