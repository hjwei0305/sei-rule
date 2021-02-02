package com.changhong.sei.rule.service.engine;

import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.dto.ruletree.RuleTreeRoot;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.service.RuleTreeNodeService;
import com.changhong.sei.rule.service.bo.RuleChain;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2021-02-02 13:52
 */
class RuleChainServiceTest extends BaseUnit5Test {
    @Autowired
    private RuleChainService service;
    @Autowired
    private RuleTreeNodeService ruleTreeNodeService;

    @Test
    public void RuleRunTest(){
        String ruleTypeId = "76E87452-562B-11EB-B2C8-3C6AA7266A51";
        Map<String, Object> env = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        param.put("postscript","44宿舍保证金565");
        env.put("param", param);
        List<RuleTreeRoot> roots =  ruleTreeNodeService.findRootNodes(ruleTypeId);
        for (RuleTreeRoot root : roots) {
            // 获取规则树
            RuleTreeNode tree = ruleTreeNodeService.getRuleTree(root.getId());
            List<RuleChain> ruleChains = service.getExpressionByTree(tree);
            for (RuleChain ruleChain : ruleChains) {
                // 编译表达式
                Expression compiledExp = AviatorEvaluator.compile(ruleChain.getExpression(), true);
                Boolean result = (Boolean) compiledExp.execute(env);
                System.out.println(result);
            }
        }
    }

    @Test
    void getExpressionByRootNode() {
        String rootNodeId = "E572FF56-5A29-11EB-82F6-3C6AA7266A51";
        // 获取规则树
        RuleTreeNode tree = ruleTreeNodeService.getRuleTree(rootNodeId);
        Assertions.assertNotNull(tree);
        List<RuleChain> ruleChains = service.getExpressionByTree(tree);
        System.out.println(JsonUtils.toJson(ruleChains));
    }
}