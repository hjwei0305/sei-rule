package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.api.RuleComparatorApi;
import com.changhong.sei.rule.dto.RuleAttributeDto;
import com.changhong.sei.rule.dto.RuleComparatorDto;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.entity.RuleComparator;
import com.changhong.sei.rule.service.RuleComparatorService;
import io.swagger.annotations.Api;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 比较器定义(RuleComparator)控制类
 *
 * @author sei
 * @since 2021-01-14 09:19:19
 */
@RestController
@Api(value = "RuleComparatorApi", tags = "比较器定义服务")
@RequestMapping(path = "ruleComparator", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleComparatorController extends BaseEntityController<RuleComparator, RuleComparatorDto>
        implements RuleComparatorApi {
    /**
     * 比较器定义服务对象
     */
    @Autowired
    private RuleComparatorService service;

    @Override
    public BaseEntityService<RuleComparator> getService() {
        return service;
    }

    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<RuleComparator, RuleComparatorDto> propertyMap = new PropertyMap<RuleComparator, RuleComparatorDto>() {
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
     * 获取规则业务实体配置的比较器清单
     *
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 比较器清单
     */
    @Override
    public ResultData<List<RuleComparatorDto>> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return ResultData.success(convertToDtos(service.findByRuleEntityTypeId(ruleEntityTypeId)));
    }
}