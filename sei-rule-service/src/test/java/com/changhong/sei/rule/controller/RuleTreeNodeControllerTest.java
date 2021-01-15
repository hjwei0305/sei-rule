package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.dto.*;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * 实现功能: 规则树单元测试
 *
 * @author 王锦光 wangjg
 * @version 2021-01-13 16:53
 */
public class RuleTreeNodeControllerTest extends BaseUnitTest {
    @Autowired
    private RuleTreeNodeController controller;

    @Test
    public void findOne() {
        String id = "123";
        ResultData<RuleTreeNodeDto> resultData = controller.findOne(id);
        System.out.println(JsonUtils.toJson(resultData));
        assertTrue(resultData.successful());
    }

    @Test
    public void saveRuleTreeTest() {
        RuleTree ruleTree = new RuleTree();
        ruleTree.setName("保证金规则");
        ruleTree.setRuleTypeId("76E87452-562B-11EB-B2C8-3C6AA7266A51");
        RuleTreeNodeDto ruleTreeNode = new RuleTreeNodeDto();
        ruleTreeNode.setFinished(Boolean.FALSE);
        ruleTree.setTreeNode(ruleTreeNode);
        LogicalExpressionDto expressionDto = new LogicalExpressionDto();
        expressionDto.setComparisonOperator(ComparisonOperator.CONTAIN);
        expressionDto.setComparisonValue("保证金");
        expressionDto.setRuleAttributeId("A68050C5-5633-11EB-8D6E-3C6AA7266A51");
        ruleTreeNode.setExpressionDtos(Collections.singletonList(expressionDto));
        //子节点1
        RuleTreeNodeDto child = new RuleTreeNodeDto();
        child.setName("员工保证金");
        child.setFinished(Boolean.TRUE);
        LogicalExpressionDto childExpressionDto = new LogicalExpressionDto();
        childExpressionDto.setComparisonOperator(ComparisonOperator.CONTAIN);
        childExpressionDto.setComparisonValue("员工保证金");
        childExpressionDto.setRuleAttributeId("A68050C5-5633-11EB-8D6E-3C6AA7266A51");
        child.setExpressionDtos(Collections.singletonList(childExpressionDto));
        NodeReturnResultDto nodeReturnResultDto = new NodeReturnResultDto();
        nodeReturnResultDto.setReturnValueId("123");
        nodeReturnResultDto.setReturnValueName("保证金款项");
        nodeReturnResultDto.setRuleReturnTypeId("EB2FA0E7-5633-11EB-8D6E-3C6AA7266A51");
        child.setNodeReturnResultDtos(Collections.singletonList(nodeReturnResultDto));
        //子节点2
        RuleTreeNodeDto child2 = new RuleTreeNodeDto();
        child2.setName("宿舍保证金");
        child2.setFinished(Boolean.TRUE);
        LogicalExpressionDto child2ExpressionDto = new LogicalExpressionDto();
        child2ExpressionDto.setComparisonOperator(ComparisonOperator.CONTAIN);
        child2ExpressionDto.setComparisonValue("宿舍保证金");
        child2ExpressionDto.setRuleAttributeId("A68050C5-5633-11EB-8D6E-3C6AA7266A51");
        child2.setExpressionDtos(Collections.singletonList(child2ExpressionDto));
        NodeReturnResultDto nodeReturnResultDto2 = new NodeReturnResultDto();
        nodeReturnResultDto2.setReturnValueId("456");
        nodeReturnResultDto2.setReturnValueName("保证金款项");
        nodeReturnResultDto2.setRuleReturnTypeId("EB2FA0E7-5633-11EB-8D6E-3C6AA7266A51");
        child2.setNodeReturnResultDtos(Collections.singletonList(nodeReturnResultDto2));
        ruleTreeNode.setChildren(Arrays.asList(child,child2));
        ResultData resultData = controller.saveRuleTree(ruleTree);
        Assert.assertTrue(resultData.getSuccess());
    }

    @Test
    public void deleteRuleTreeTest() {
        controller.deleteRuleTree("FA8357F6-56F5-11EB-8A46-3C6AA7266A51");
    }
}