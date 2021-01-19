package com.changhong.sei.rule.service.client;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.utils.AsyncRunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:调用外部API客户端
 * @date 2021/01/18 16:48
 */

@Component
public class RuleServiceMethodClient {

    @Autowired
    private ApiTemplate apiTemplate;
    @Autowired
    private AsyncRunUtil asyncRunUtil;

    /**
     * 同步调用外部方法
     *
     * @param appModuleCode 模块名
     * @param path          路径
     * @param params        参数
     * @return 调用结果
     */
    public ResultData<?> post(String appModuleCode, String path, Object params, Boolean asyncExecute) {
        if (asyncExecute) {
            return postAsync(appModuleCode, path, params);
        } else {
            return post(appModuleCode, path, params);
        }
    }

    /**
     * 同步调用外部方法
     *
     * @param appModuleCode 模块名
     * @param path          路径
     * @param params        参数
     * @return 调用结果
     */
    private ResultData<?> post(String appModuleCode, String path, Object params) {
        return apiTemplate.postByAppModuleCode(appModuleCode, path, ResultData.class, params);
    }

    /**
     * 异步同步调用外部方法
     *
     * @param appModuleCode 模块名
     * @param path          路径
     * @param params        参数
     */
    private ResultData<?> postAsync(String appModuleCode, String path, Object params) {
        asyncRunUtil.runAsync(() -> apiTemplate.postByAppModuleCode(appModuleCode, path, ResultData.class, params));
        return ResultData.success();
    }
}
