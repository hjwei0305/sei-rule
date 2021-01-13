package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.rule.api.MatchingRuleApi;
import com.changhong.sei.rule.dto.MatchingRuleDto;
import com.changhong.sei.rule.dto.RuleProperty;
import com.changhong.sei.rule.dto.RuleTree;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.RuleCategory;
import com.changhong.sei.rule.entity.MatchingRule;
import com.changhong.sei.rule.service.MatchingRuleService;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * 业务匹配规则(MatchingRule)控制类
 *
 * @author sei
 * @since 2020-08-14 09:22:20
 */
@RestController
@Api(value = "MatchingRuleApi", tags = "业务匹配规则服务")
@RequestMapping(path = "matchingRule", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchingRuleController extends BaseTreeController<MatchingRule, MatchingRuleDto>
        implements MatchingRuleApi {
    /**
     * 业务匹配规则服务对象
     */
    @Autowired
    private MatchingRuleService service;

    @Override
    public BaseTreeService<MatchingRule> getService() {
        return service;
    }

    /**
     * 获取业务匹配规则分类枚举值
     *
     * @return 枚举值清单
     */
    @Override
    public ResultData<List<EnumUtils.EnumEntity>> getRuleCategoryEnum() {
        return ResultDataUtil.getEnumEntities(RuleCategory.class);
    }

    /**
     * 获取比较运算符枚举值
     *
     * @return 枚举值清单
     */
    @Override
    public ResultData<List<EnumUtils.EnumEntity>> getComparisonOperatorEnum() {
        return ResultDataUtil.getEnumEntities(ComparisonOperator.class);
    }

    /**
     * 通过分类获取所有规则树
     *
     * @param category 规则分类
     * @return 规则树集合
     */
    @Override
    public ResultData<List<MatchingRuleDto>> getRuleTrees(RuleCategory category) {
        return ResultData.success(convertToDtos(service.getRuleTrees(category)));
    }

    /**
     * 通过公司和分类获取规则根节点
     *
     * @param category 规则分类
     * @param corpCode 公司代码
     * @return 规则根节点
     */
    @Override
    public ResultData<List<MatchingRuleDto>> getRuleRootNodes(RuleCategory category, String corpCode) {
        return ResultData.success(convertToDtos(service.getRuleRootNodes(category, corpCode)));
    }

    /**
     * 保存业务规则树
     *
     * @param ruleTree 业务规则树
     * @return 处理结果
     */
    @Override
    public ResultData<?> saveRuleTree(RuleTree ruleTree) {
        // 为根节点赋值
        MatchingRuleDto treeNode = ruleTree.getTreeNode();
        treeNode.setName(ruleTree.getName());
        treeNode.setRank(ruleTree.getRank());
        treeNode.setRuleCategory(ruleTree.getRuleCategory());
        MatchingRule ruleNode = convertToEntity(treeNode);
        return ResultDataUtil.convertFromOperateResult(service.saveRuleTree(ruleNode));
    }

    /**
     * 删除业务规则树
     *
     * @param rootId 根节点Id
     * @return 处理结果
     */
    @Override
    public ResultData<?> deleteRuleTree(String rootId) {
        service.deleteRuleTree(rootId);
        // 业务规则树删除成功！
        return ResultDataUtil.success("00152");
    }

    /**
     * 获取可以使用的规则匹配属性
     *
     * @return 属性清单
     */
    @Override
    public ResultData<List<RuleProperty>> getRuleProperties() {
        List<RuleProperty> properties = new LinkedList<>();
        properties.add(new RuleProperty("erpCode", "ERP公司代码"));
        properties.add(new RuleProperty("bankAccount", "我方账户"));
        properties.add(new RuleProperty("postscript", "附言"));
        properties.add(new RuleProperty("bankCategoryCode", "银行行别代码"));
        // 二开扩展点
        return ResultData.success(properties);
    }

}