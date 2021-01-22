package com.changhong.sei.rule.service.engine;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dao.RuleTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.entity.RuleType;
import com.changhong.sei.rule.sdk.dto.RuleReturnEntity;
import com.changhong.sei.rule.sdk.dto.RuleRunRequest;
import com.changhong.sei.rule.sdk.dto.RuleRunResponse;
import com.changhong.sei.rule.sdk.dto.RuleServiceMethodParam;
import com.changhong.sei.rule.service.RuleTreeNodeService;
import com.changhong.sei.rule.service.bo.RuleChain;
import com.changhong.sei.rule.service.client.RuleServiceMethodClient;
import com.changhong.sei.rule.service.exception.RuleEngineException;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.changhong.sei.rule.service.aviator.AviatorExpressionService.RULE_CHAIN_PARAM_PREFIX;
import static com.changhong.sei.rule.service.aviator.AviatorExpressionService.RULE_TYPE_CODE;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:规则引擎服务
 * @date 2021/01/18 16:48
 */
@Service
public class RuleEngineService {

    @Autowired
    private RuleTypeDao ruleTypeDao;
    @Autowired
    private RuleTreeNodeService ruleTreeNodeService;
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    @Autowired
    private RuleServiceMethodClient ruleServiceMethodClient;


    /**
     * 执行规则
     *
     * @param request 规则执行请求
     * @return 结果返回对象
     */
    public RuleRunResponse run(RuleRunRequest request) throws RuleEngineException {
        RuleRunResponse response = new RuleRunResponse();
        String ruleTypeCode = request.getRuleTypeCode();
        if (StringUtils.isBlank(ruleTypeCode)) {
            //规则类型代码不能为空！
            throw new RuleEngineException("00026");
        }
        RuleType ruleType = ruleTypeDao.findByCodeAndTenantCode(ruleTypeCode, ContextUtil.getTenantCode());
        if (Objects.isNull(ruleType)) {
            //指定规则类型不存在！
            throw new RuleEngineException("00027");
        }
        List<RuleTreeNode> ruleTrees = ruleTreeNodeService.findRootNodes(ruleType.getId());
        if (Objects.isNull(ruleTrees) || ruleTrees.isEmpty()) {
            //指定规则类型规则列表为空！
            throw new RuleEngineException("00028");
        }
        Map<String, Object> env = new HashMap<>();
        Object param;
        try {
            param = JsonUtils.fromJson(request.getRuleEntityJson(), Object.class);
        } catch (RuntimeException e) {
            LogUtil.error("规则引擎转换JSON字符串异常:" + e.getMessage(), e);
            //JSON字符串转换异常，请检查是否为JsonObject格式！
            throw new RuleEngineException("00029");
        }
        env.put(RULE_CHAIN_PARAM_PREFIX, param);
        env.put(RULE_TYPE_CODE, request.getRuleTypeCode());
        //根据优先级依次匹配多个规则
        for (RuleTreeNode ruleTree : ruleTrees) {
            //获得规则链
            List<RuleChain> ruleChains = ruleTreeNodeService.getExpressionByRootNode(ruleTree.getId());
            for (RuleChain ruleChain : ruleChains) {
                //是否匹配成功
                if (ruleChainMatch(env, ruleChain)) {
                    //匹配成功后执行操作
                    matchSuccess(request, response, ruleChain);
                    //匹配上一个直接返回
                    return response;
                }
            }
        }
        //未匹配上 返回默认响应
        return response;
    }

    /**
     * 规则链匹配
     *
     * @param env       匹配参数
     * @param ruleChain 规则链
     * @return 匹配结果
     */
    private boolean ruleChainMatch(Map<String, Object> env, RuleChain ruleChain) {
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(ruleChain.getExpression(), true);
        try {
            return (Boolean) compiledExp.execute(env);
        } catch (NullPointerException e) {
            //00032 = 必要的规则匹配参数未传入,请检查！
            throw new RuleEngineException("00032");
        }

    }

    /**
     * 匹配成功后执行的方法
     *
     * @param request   匹配请求
     * @param response  匹配结果
     * @param ruleChain 规则链
     */
    private void matchSuccess(RuleRunRequest request, RuleRunResponse response, RuleChain ruleChain) {
        //记录日志
        //规则类型[{0}]已匹配上规则节点[{1}]，输入参数:{2}，匹配表达式:[{3}]
        LogUtil.bizLog(ContextUtil.getMessage("00031", request.getRuleTypeCode(), ruleChain.getRuleTreeNodeId(), request.getRuleEntityJson(), ruleChain.getExpression()));
        //设置是否匹配标识
        response.setMatched(true);
        response.setMatchedNodeId(ruleChain.getRuleTreeNodeId());
        //返回对象
        List<RuleReturnEntity> returnEntities = ruleChain.getReturnEntities();
        if (!Objects.isNull(returnEntities) && !returnEntities.isEmpty()) {
            //组装Map key：类名 enties:实体对象列表
            Map<String, List<RuleReturnEntity>> returnEntityMap = new HashMap<>();
            returnEntities.forEach(e -> {
                List<RuleReturnEntity> entries = returnEntityMap.get(e.getClassName());
                if (Objects.isNull(entries)) {
                    entries = new ArrayList<>();
                }
                entries.add(e);
                returnEntityMap.put(e.getClassName(), entries);
            });
            response.setReturnEntityMap(returnEntityMap);
        }
        //执行方法
        RuleServiceMethod method = ruleChain.getRuleServiceMethod();
        if (Objects.nonNull(method)) {
            serviceMethodExecute(request, response, ruleChain, method);
        }
    }

    /**
     * 执行自定义规则执行方法
     *
     * @param request   匹配请求
     * @param response  匹配结果
     * @param ruleChain 规则链
     * @param method    自定义规则方法
     */
    private void serviceMethodExecute(RuleRunRequest request, RuleRunResponse response, RuleChain ruleChain, RuleServiceMethod method) {
        RuleEntityType ruleEntityType = ruleEntityTypeDao.findOne(method.getRuleEntityTypeId());
        if (Objects.isNull(ruleEntityType)) {
            //指定规则业务实体[{0}]不存在！
            throw new RuleEngineException("00030", method.getRuleEntityTypeId());
        }
        try {
            StringBuilder url = new StringBuilder();
            if (method.getPath().endsWith("/")) {
                url.append(method.getPath());
            } else {
                url.append(method.getPath()).append("/");
            }
            url.append(method.getMethod());
            RuleServiceMethodParam methodParam = new RuleServiceMethodParam();
            methodParam.setReturnEntityMap(response.getReturnEntityMap());
            methodParam.setRequest(request);
            ResultData<?> apiResult = ruleServiceMethodClient.post(ruleEntityType.getServiceName(), url.toString(), methodParam, ruleChain.getAsyncExecute());
            if (apiResult.successful()) {
                //设置方法已执行
                response.setExecuted(true);
            } else {
                LogUtil.error("访问外部服务模块返回失败:" + apiResult.getMessage());
                //访问外部服务模块{0}，path={1}自定义方法返回失败:[message={2}]，请查看应用模块[{0}]的异常日志！
                throw new RuleEngineException("00001", ruleEntityType.getServiceName(), url.toString(), apiResult.getMessage());
            }
        } catch (Exception e) {
            LogUtil.error("访问外部服务模块自定义方法异常:" + e.getMessage(), e);
            throw new RuleEngineException("访问外部服务模块自定义方法异常:" + e.getMessage(), e);
        }
    }
}
