package com.changhong.sei.rule.service;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.rule.dao.RuleTreeNodeDao;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import junit.runner.BaseTestRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则树节点测试
 */
public class RuleTreeNodeServiceTest extends BaseUnitTest {

    @Autowired
    private RuleTreeNodeService service;


    @Test
    public void RuleRunTest(){
        String ruleTypeId = "76E87452-562B-11EB-B2C8-3C6AA7266A51";
        Map<String, Object> env = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        param.put("postscript","44保证金565");
        env.put("param", param);
        List<RuleTreeNode> nodes =  service.findRootNodes(ruleTypeId);
        for (RuleTreeNode node : nodes) {
            List<String> expressions = service.getExpressionByRootNode(node.getId());
            for (String expression : expressions) {
                // 编译表达式
                Expression compiledExp = AviatorEvaluator.compile(expression, true);
                Boolean result = (Boolean) compiledExp.execute(env);
                System.out.println(result);
            }
        }
    }

}
