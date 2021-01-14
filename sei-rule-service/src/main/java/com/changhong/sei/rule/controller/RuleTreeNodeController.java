package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.rule.api.RuleTreeNodeApi;
import com.changhong.sei.rule.dto.RuleTree;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.service.RuleTreeNodeService;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规则树节点(RuleTreeNode)控制类
 *
 * @author sei
 * @since 2021-01-13 16:29:55
 */
@RestController
@Api(value = "RuleTreeNodeApi", tags = "规则树节点服务")
@RequestMapping(path = "ruleTreeNode", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleTreeNodeController extends BaseTreeController<RuleTreeNode, RuleTreeNodeDto>
        implements RuleTreeNodeApi {
    /**
     * 规则树节点服务对象
     */
    @Autowired
    private RuleTreeNodeService service;

    @Override
    public BaseTreeService<RuleTreeNode> getService() {
        return service;
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
     * 通过规则类型ID获取所有规则树
     *
     * @param ruleTypeId 规则类型ID
     * @return 规则树集合
     */
    @Override
    public ResultData<List<RuleTreeNodeDto>> getRuleTrees(String ruleTypeId) {
        return ResultData.success(convertToDtos(service.getRuleTrees(ruleTypeId)));
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
        RuleTreeNodeDto treeNode = ruleTree.getTreeNode();
        treeNode.setName(ruleTree.getName());
        treeNode.setRank(ruleTree.getRank());
        RuleTreeNode ruleNode = convertToEntity(treeNode);
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
        return ResultDataUtil.success("00018");
    }

}