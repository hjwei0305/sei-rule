package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.stereotype.Component;

/**
 * 实现功能: 任务执行器基类
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:40
 */
@Component
public class BasePerformer implements TaskPerformer {
    /**
     * 执行任务
     *
     * @return 执行结果
     */
    @Override
    public OperateResult performTask() {
        return OperateResult.operationSuccess("任务执行器基类执行完成！");
    }
}
