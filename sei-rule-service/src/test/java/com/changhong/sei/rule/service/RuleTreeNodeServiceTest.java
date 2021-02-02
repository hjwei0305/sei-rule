package com.changhong.sei.rule.service;

import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.service.bo.RuleChain;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则树节点测试
 */
public class RuleTreeNodeServiceTest extends BaseUnit5Test {

    @Autowired
    private RuleTreeNodeService service;


    @Test
    public void RuleRunTest(){
        String ruleTypeId = "76E87452-562B-11EB-B2C8-3C6AA7266A51";
        Map<String, Object> env = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        param.put("postscript","44宿舍保证金565");
        env.put("param", param);
        List<RuleTreeNode> nodes =  service.findRuleTreeRootNodes(ruleTypeId);
        for (RuleTreeNode node : nodes) {
            List<RuleChain> ruleChains = service.getExpressionByRootNode(node.getId());
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
        List<RuleChain> ruleChains = service.getExpressionByRootNode("E572FF56-5A29-11EB-82F6-3C6AA7266A51");
        System.out.println(JsonUtils.toJson(ruleChains));
    }

}
