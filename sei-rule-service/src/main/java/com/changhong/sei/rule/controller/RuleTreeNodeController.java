package com.changhong.sei.rule.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.rule.api.RuleTreeNodeApi;
import com.changhong.sei.rule.dto.LogicalExpressionDto;
import com.changhong.sei.rule.dto.NodeReturnResultDto;
import com.changhong.sei.rule.dto.ruletree.RuleTree;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import com.changhong.sei.rule.entity.LogicalExpression;
import com.changhong.sei.rule.entity.NodeReturnResult;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.service.RuleTreeNodeService;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    /**
     * 定义通用的严格匹配实体转换器
     */
    private static final ModelMapper strictModelMapper;
    // 初始化静态属性
    static {
        // 初始化转换器
        strictModelMapper = new ModelMapper();
        // 设置为严格匹配
        strictModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public BaseTreeService<RuleTreeNode> getService() {
        return service;
    }

    /**
     * 获取规则类型下配置的所有规则树
     *
     * @param ruleTypeId 规则类型Id
     * @return 规则树根节点清单
     */
    @Override
    public ResultData<List<RuleTreeRoot>> findRootNodes(String ruleTypeId) {
        List<RuleTreeNode> nodes = service.findRootNodes(ruleTypeId, ContextUtil.getTenantCode());
        List<RuleTreeRoot> roots = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(nodes)) {
            nodes.forEach(node -> {
                RuleTreeRoot root = strictModelMapper.map(node, RuleTreeRoot.class);
                if (Objects.nonNull(root)) {
                    roots.add(root);
                }
            });
        }
        return ResultData.success(roots);
    }

    /**
     * 更新规则树根节点信息
     *
     * @param ruleTreeRoot 规则树根节点
     * @return 处理结果
     */
    @Override
    public ResultData<?> updateRootNode(RuleTreeRoot ruleTreeRoot) {
        return ResultDataUtil.convertFromOperateResult(service.updateRootNode(ruleTreeRoot));
    }

    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<RuleTreeNode, RuleTreeNodeDto> propertyMap = new PropertyMap<RuleTreeNode, RuleTreeNodeDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则
                map().setRuleTypeId(source.getRuleTypeId());
            }
        };
        // 添加映射器
        dtoModelMapper.addMappings(propertyMap);
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
        treeNode.setCode(ruleTree.getCode());
        treeNode.setName(ruleTree.getName());
        treeNode.setTrueNode(ruleTree.getTrueNode());
        treeNode.setRuleTypeId(ruleTree.getRuleTypeId());
        treeNode.setEnabled(ruleTree.getEnabled());
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

    @Override
    public RuleTreeNode convertToEntity(RuleTreeNodeDto dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        RuleTreeNode ruleTreeNode = entityModelMapper.map(dto, getEntityClass());
        //设置表达式
        List<LogicalExpressionDto> expressionDtos = dto.getLogicalExpressionDtos();
        if (Objects.nonNull(expressionDtos)) {
            List<LogicalExpression> expressions = new ArrayList<>();
            expressionDtos.forEach(ex -> {
                expressions.add(entityModelMapper.map(ex, LogicalExpression.class));
            });
            ruleTreeNode.setExpressions(expressions);
        }
        //设置结果
        List<NodeReturnResultDto> returnResultDtos = dto.getNodeReturnResultDtos();
        if (Objects.nonNull(returnResultDtos)) {
            List<NodeReturnResult> returnResults = new ArrayList<>();
            returnResultDtos.forEach(result -> {
                returnResults.add(entityModelMapper.map(result, NodeReturnResult.class));
            });
            ruleTreeNode.setNodeReturnResults(returnResults);
        }
        return ruleTreeNode;
    }
}