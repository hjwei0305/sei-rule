package com.changhong.sei.rule.controller.init;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.rule.api.init.InitializeApi;
import com.changhong.sei.rule.dto.init.InitializeTask;
import com.changhong.sei.rule.service.init.InitializeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现功能: 应用初始化服务实现
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 9:19
 */
@RestController
@Api(value = "InitializeApi", tags = "应用初始化服务实现")
@RequestMapping(path = InitializeApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class InitializeController implements InitializeApi {
    @Autowired
    private InitializeService service;

    /**
     * 获取初始化任务清单
     *
     * @return 初始化任务清单
     */
    @Override
    public ResultData<List<InitializeTask>> getInitializeTasks() {
        return ResultData.success(service.getInitializeTasks());
    }

    /**
     * 执行初始化任务
     *
     * @param id 任务标识
     * @return 处理结果
     */
    @Override
    public ResultData<Void> performTask(Integer id) {
        return ResultDataUtil.convertFromOperateResult(service.performTask(id));
    }
}
