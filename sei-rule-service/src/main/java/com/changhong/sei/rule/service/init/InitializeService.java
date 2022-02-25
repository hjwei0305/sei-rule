package com.changhong.sei.rule.service.init;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.rule.dto.init.InitializeTask;
import com.changhong.sei.rule.service.init.performer.TaskPerformer;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 实现功能: 应用初始化业务逻辑实现
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 9:22
 */
@Service
public class InitializeService {
    /**
     * 获取初始化任务清单
     *
     * @return 初始化任务清单
     */
    public List<InitializeTask> getInitializeTasks() {
        List<InitializeTask> tasks = new LinkedList<>();
        tasks.add(new InitializeTask(1, "初始化规则主体"));
        tasks.add(new InitializeTask(2, "初始化规则主体配置"));
        tasks.add(new InitializeTask(3, "初始化必要的规则定义"));
        return tasks;
    }

    /**
     * 执行初始化任务
     *
     * @param id 任务标识
     * @return 处理结果
     */
    public OperateResult performTask(Integer id) {
        String performerBeanId = "taskPerformer" + id;
        TaskPerformer performer;
        try {
            performer = ContextUtil.getBean(performerBeanId);
        } catch (Exception e) {
            String extBeanIdError = "初始化任务执行器[" + performerBeanId + "]" + "没有实现！";
            LogUtil.error(extBeanIdError,e);
            // 初始化任务执行器【{0}】没有实现！
            return OperateResult.operationFailure("00049", performerBeanId);
        }
        return performer.performTask();
    }
}
