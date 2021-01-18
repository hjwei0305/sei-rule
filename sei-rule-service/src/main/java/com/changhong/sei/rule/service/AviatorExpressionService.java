package com.changhong.sei.rule.service;

import com.changhong.sei.rule.api.MatchingRuleComparator;
import com.changhong.sei.rule.dao.RuleAttributeDao;
import com.changhong.sei.rule.dao.RuleComparatorDao;
import com.changhong.sei.rule.dto.enums.ComparisonOperator;
import com.changhong.sei.rule.dto.enums.RuleAttributeType;
import com.changhong.sei.rule.entity.LogicalExpression;
import com.changhong.sei.rule.entity.RuleAttribute;
import com.changhong.sei.rule.entity.RuleComparator;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.service.exception.MatchingRuleComparatorException;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.changhong.sei.util.DateUtils.DEFAULT_DATE_FORMAT;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:aviator表达式服务
 * @date 2021/01/18 14:17
 */
@Service
public class AviatorExpressionService {

    /**
     * 表达式前缀
     */
    public static final String RULE_CHAIN_PARAM_PREFIX = "param.";
    /**
     * 或表达式
     */
    private static final String OR_EXPRESSION = " || ";

    @Autowired
    private RuleAttributeDao ruleAttributeDao;
    @Autowired
    private RuleComparatorDao ruleComparatorDao;

    /**
     * 根据规则树节点转换为对应表达式
     *
     * @param ruleNode 规则树节点
     * @return 表达式
     */
    public String convertToExpression(RuleTreeNode ruleNode) {
        //真节点跳过
        if (ruleNode.getTrueNode()) {
            return "";
        }
        //查询逻辑表达式
        List<LogicalExpression> expressions = ruleNode.getExpressions();
        StringBuilder expression = new StringBuilder("(");
        expressions.forEach(ex -> expression.append(convertToExpression(ex)).append(OR_EXPRESSION));
        //去除最后一个||
        if (expression.toString().endsWith(OR_EXPRESSION)) {
            int expressionLength = expression.toString().length();
            expression.delete(expressionLength - OR_EXPRESSION.length(), expressionLength);
        }
        expression.append(")");
        return expression.toString();
    }

    /**
     * 根据逻辑表达式返回对应的表达式
     *
     * @param expression 逻辑表达式
     * @return 表达式
     */
    private String convertToExpression(LogicalExpression expression) {
        ComparisonOperator operator = expression.getComparisonOperator();
        RuleAttribute ruleAttribute = ruleAttributeDao.findOne(expression.getRuleAttributeId());
        String propertyCode = ruleAttribute.getAttribute();
        String comparisonValue = expression.getComparisonValue();
        RuleAttributeType ruleAttributeType = ruleAttribute.getRuleAttributeType();
        switch (ruleAttributeType) {
            case STRING:
                //字符串类型需要在两侧加单引号
                comparisonValue = "'" + comparisonValue + "'";
                break;
            case DATETIME:
                //日期类型需要转化为yyyy-MM-dd HH:mm:ss:SS 格式 在两侧加单引号
                Date date = DateUtils.parseDate(comparisonValue, DEFAULT_DATE_FORMAT);
                comparisonValue = "'" + DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss:SS") + "'";
                break;
            default:
                break;
        }
        //需要在参数上加上前缀
        propertyCode = RULE_CHAIN_PARAM_PREFIX + propertyCode;
        StringBuilder builder = new StringBuilder();
        switch (operator) {
            case EQUAL:
                builder.append(propertyCode).append("==").append(comparisonValue);
                break;
            case NOTEQUAL:
                builder.append(propertyCode).append("!=").append(comparisonValue);
                break;
            case LESS:
                builder.append(propertyCode).append("<").append(comparisonValue);
                break;
            case GREATER:
                builder.append(propertyCode).append(">").append(comparisonValue);
                break;
            case LESS_EQUAL:
                builder.append(propertyCode).append("<=").append(comparisonValue);
                break;
            case GREATER_EQUAL:
                builder.append(propertyCode).append(">=").append(comparisonValue);
                break;
            case CONTAIN:
                builder.append("string.contains(").append(propertyCode).append(",").append(comparisonValue).append(")");
                break;
            case MATCH:
                builder.append(propertyCode).append("=~").append(comparisonValue);
                break;
            case COMPARER:
                if (StringUtils.isBlank(comparisonValue)) {
                    throw new MatchingRuleComparatorException("匹配规则计算接口实现Id为空");
                }
                RuleComparator comparator = ruleComparatorDao.findOne(comparisonValue);
                if (Objects.isNull(comparator)) {
                    throw new MatchingRuleComparatorException("匹配规则计算接口实现[" + comparisonValue + "]不存在");
                }
                String module = comparator.getRuleEntityType().getServiceName();
                StringBuilder url = new StringBuilder();
                if (comparator.getPath().endsWith("/")){
                    url.append(comparator.getPath());
                }else {
                    url.append(comparator.getPath()).append("/");
                }
                url.append(comparator.getMethod());
                builder.append("MatchRuleComparator('").append(module).append("','").append(url.toString()).append("')");
                break;
            default:
                break;
        }
        return builder.toString();
    }

}
