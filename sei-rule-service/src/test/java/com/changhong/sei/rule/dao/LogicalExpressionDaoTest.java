package com.changhong.sei.rule.dao;

import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.rule.BaseUnit5Test;
import com.changhong.sei.rule.entity.LogicalExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 实现功能: 逻辑表达式
 *
 * @author 王锦光 wangjg
 * @version 2021-01-14 15:51
 */
class LogicalExpressionDaoTest extends BaseUnit5Test {
    @Autowired
    private LogicalExpressionDao dao;

    @Test
    void findByRuleTreeNodeId() {
        String nodeId = "111";
        List<LogicalExpression> expressions = dao.findByRuleTreeNodeId(nodeId);
        Assertions.assertNotNull(expressions);
    }
}