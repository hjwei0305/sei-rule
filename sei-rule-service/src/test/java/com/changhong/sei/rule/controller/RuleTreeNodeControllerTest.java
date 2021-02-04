package com.changhong.sei.rule.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.LogicalExpressionDto;
import com.changhong.sei.rule.dto.NodeReturnResultDto;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.ruletree.RuleTree;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * 实现功能: 规则树单元测试
 *
 * @author 王锦光 wangjg
 * @version 2021-01-13 16:53
 */
public class RuleTreeNodeControllerTest extends BaseUnit5Test {
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
    public void saveRuleTreeTest() throws InterruptedException {
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
        ruleTreeNode.setLogicalExpressions(Collections.singletonList(expressionDto));
        //子节点1
        RuleTreeNodeDto child = new RuleTreeNodeDto();
        child.setName("员工保证金");
        child.setFinished(Boolean.TRUE);
        LogicalExpressionDto childExpressionDto = new LogicalExpressionDto();
        childExpressionDto.setComparisonOperator(ComparisonOperator.CONTAIN);
        childExpressionDto.setComparisonValue("员工保证金");
        childExpressionDto.setRuleAttributeId("A68050C5-5633-11EB-8D6E-3C6AA7266A51");
        child.setLogicalExpressions(Collections.singletonList(childExpressionDto));
        NodeReturnResultDto nodeReturnResultDto = new NodeReturnResultDto();
        nodeReturnResultDto.setReturnValueId("123");
        nodeReturnResultDto.setReturnValueName("保证金款项");
        nodeReturnResultDto.setRuleReturnTypeId("EB2FA0E7-5633-11EB-8D6E-3C6AA7266A51");
        child.setNodeReturnResults(Collections.singletonList(nodeReturnResultDto));
        //子节点2
        RuleTreeNodeDto child2 = new RuleTreeNodeDto();
        child2.setName("宿舍保证金");
        child2.setFinished(Boolean.TRUE);
        LogicalExpressionDto child2ExpressionDto = new LogicalExpressionDto();
        child2ExpressionDto.setComparisonOperator(ComparisonOperator.CONTAIN);
        child2ExpressionDto.setComparisonValue("宿舍保证金");
        child2ExpressionDto.setRuleAttributeId("A68050C5-5633-11EB-8D6E-3C6AA7266A51");
        child2.setLogicalExpressions(Collections.singletonList(child2ExpressionDto));
        NodeReturnResultDto nodeReturnResultDto2 = new NodeReturnResultDto();
        nodeReturnResultDto2.setReturnValueId("456");
        nodeReturnResultDto2.setReturnValueName("保证金款项");
        nodeReturnResultDto2.setRuleReturnTypeId("EB2FA0E7-5633-11EB-8D6E-3C6AA7266A51");
        child2.setNodeReturnResults(Collections.singletonList(nodeReturnResultDto2));
        child2.setRuleServiceMethodId("90BB80A7-5968-11EB-94B4-3C6AA7266A52");
        ruleTreeNode.setChildren(Arrays.asList(child, child2));
        System.out.println(JsonUtils.toJson(ruleTree));
        //ResultData resultData = controller.saveRuleTree(ruleTree);
        //等待建立缓存
        Thread.sleep(5000);
        //Assertions.assertTrue(resultData.getSuccess());
    }

    @Test
    public void deleteRuleTreeTest() {
        controller.deleteRuleTree("E572FF56-5A29-11EB-82F6-3C6AA7266A51");
    }

    @Test
    void findRootNodes() {
        String ruleTypeId = "76E87452-562B-11EB-B2C8-3C6AA7266A51";
        ResultData<List<RuleTreeRoot>> resultData = controller.findRootNodes(ruleTypeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void updateRootNode() {
        RuleTreeRoot root = new RuleTreeRoot();
        root.setId("E4395A52-5648-11EB-9D6E-3C6AA7266A51");
        root.setName("保证金认款规则");
        root.setRank(1);
        root.setEnabled(Boolean.TRUE);
        ResultData<?> resultData = controller.updateRootNode(root);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void getRuleTree() {
        String rootId = "D63735C5-56F9-11EB-8B50-3C6AA7266A51";
        ResultData<?> resultData = controller.getRuleTree(rootId);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void createRootNode() {
        RuleTreeRoot root = new RuleTreeRoot();
        root.setRuleTypeId("76E87452-562B-11EB-B2C8-3C6AA7266A51");
        root.setName("TEST保证金认款规则");
        root.setRank(2);
        root.setEnabled(Boolean.FALSE);
        ResultData<?> resultData = controller.createRootNode(root);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void deleteNode() {
        String nodeId = "D63735C5-56F9-11EB-8B50-3C6AA7266A51";
        ResultData<?> resultData = controller.deleteNode(nodeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }

    @Test
    void getNodeSynthesisExpressions() {
        String nodeId = "EA0E0C95-5AE2-11EB-81CA-3C6AA7266A52";
        ResultData<?> resultData = controller.getNodeSynthesisExpressions(nodeId);
        System.out.println(JsonUtils.toJson(resultData));
        Assertions.assertTrue(resultData.successful());
    }
}