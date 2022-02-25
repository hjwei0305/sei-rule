package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.service.bo.OperateResult;
import org.springframework.stereotype.Component;

/**
 * 实现功能: 初始化规则主体
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer1 extends BasePerformer {
    /**
     * 执行任务
     *
     * @return 执行结果
     */
    @Override
    public OperateResult performTask() {
        // 规则主体初始化完毕！
        return OperateResult.operationSuccess("00048");
    }
}
