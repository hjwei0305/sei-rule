package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.rule.api.RuleTypeApi;
import com.changhong.sei.rule.dto.RuleTypeDto;
import com.changhong.sei.rule.dto.ruletree.RuleTypeTree;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleType;
import com.changhong.sei.rule.service.RuleEntityTypeService;
import com.changhong.sei.rule.service.RuleTypeService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

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
    @Autowired
    private RuleEntityTypeService ruleEntityTypeService;

    @Override
    public BaseEntityService<RuleType> getService() {
        return service;
    }

    /**
     * 获取规则类型树清单
     *
     * @return 规则类型树清单
     */
    @Override
    public ResultData<List<RuleTypeTree>> getRuleTypeTrees() {
        List<RuleTypeTree> ruleTypeTrees = new LinkedList<>();
        // 获取业务实体类型清单
        List<RuleEntityType> entityTypes = ruleEntityTypeService.findAll();
        if (CollectionUtils.isEmpty(entityTypes)) {
            return ResultData.success(ruleTypeTrees);
        }
        // 循环获取规则类型
        entityTypes.forEach( entityType -> {
            // 构建根节点
            RuleTypeTree ruleTypeTree = new RuleTypeTree();
            ruleTypeTree.setId(entityType.getId());
            ruleTypeTree.setCode(entityType.getCode());
            ruleTypeTree.setName(entityType.getName());
            ruleTypeTrees.add(ruleTypeTree);
            List<RuleType> ruleTypes = service.findByRuleEntityTypeId(entityType.getId());
            List<RuleTypeDto> ruleTypeDtos = convertToDtos(ruleTypes);
            if (CollectionUtils.isNotEmpty(ruleTypeDtos)) {
                // 添加子节点
                List<RuleTypeTree> children = new LinkedList<>();
                ruleTypeDtos.forEach(ruleTypeDto -> {
                    children.add(new RuleTypeTree(ruleTypeDto));
                });
                ruleTypeTree.setChildren(children);
            }
        });
        return ResultData.success(ruleTypeTrees);
    }
}