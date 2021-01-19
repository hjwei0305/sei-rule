package com.changhong.sei.rule.service;

import com.changhong.sei.apitemplate.ApiTemplate;
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
import com.changhong.sei.rule.service.bo.RuleChain;
import com.changhong.sei.rule.service.exception.RuleEngineException;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.changhong.sei.rule.service.aviator.AviatorExpressionService.RULE_CHAIN_PARAM_PREFIX;

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
    private ApiTemplate apiTemplate;

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
        Object param = null;
        try {
            param = JsonUtils.fromJson(request.getRuleEntityJson(), Object.class);
        } catch (RuntimeException e) {
            LogUtil.error("规则引擎转换JSON字符串异常:" + e.getMessage(), e);
            //JSON字符串转换异常，请检查是否为JsonObject格式！
            throw new RuleEngineException("00029");
        }
        env.put(RULE_CHAIN_PARAM_PREFIX, param);
        for (RuleTreeNode ruleTree : ruleTrees) {
            //获得
            List<RuleChain> ruleChains = ruleTreeNodeService.getExpressionByRootNode(ruleTree.getId());
            for (RuleChain ruleChain : ruleChains) {
                // 编译表达式
                Expression compiledExp = AviatorEvaluator.compile(ruleChain.getExpression(), true);
                Boolean result = (Boolean) compiledExp.execute(env);
                //匹配成功
                if (result) {
                    //设置是否匹配标识
                    response.setMatched(true);
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
                        RuleEntityType ruleEntityType = ruleEntityTypeDao.findOne(method.getRuleEntityTypeId());
                        if (Objects.isNull(ruleEntityType)) {
                            //
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
                            ResultData<Boolean> apiResult = apiTemplate.postByAppModuleCode(ruleEntityType.getServiceName(), url.toString(), ResultData.class, env);
                            if (apiResult.successful()) {
                                //设置方法已执行
                                response.setExecuted(true);
                            } else {
                                //访问外部服务模块{0}，path={1}返回失败，请查看应用模块[{0}]的异常日志！
                                throw new RuleEngineException("00001", ruleEntityType.getServiceName(), url.toString());
                            }
                        } catch (Exception e) {
                            LogUtil.error("访问外部服务模块自定义方法异常:" + e.getMessage(), e);
                            throw new RuleEngineException(e.getMessage(), e);
                        }
                    }
                    break;
                }
            }
        }
        return response;
    }
}
