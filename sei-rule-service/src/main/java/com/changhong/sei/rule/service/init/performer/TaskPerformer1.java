package com.changhong.sei.rule.service.init.performer;

import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.service.RuleEntityTypeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 实现功能: 初始化规则主体
 *
 * @author 王锦光 wangjg
 * @version 2022-02-25 10:45
 */
@Component
public class TaskPerformer1 extends BasePerformer {
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;
    public static final String EBILL_INVOICE_CHECK = "ebill-invoice_check";
    public static final String SOMS_ALLOT_WORK_STRATEGY = "soms-AllotWorkStrategy";
    /**
     * 定义初始化业务实体
     */
    private static final List<RuleEntityType> initEntities;

    static {
        initEntities = new LinkedList<>();
        initEntities.add(new RuleEntityType(EBILL_INVOICE_CHECK, "我的票据合规性检查", "ebill"));
        initEntities.add(new RuleEntityType(SOMS_ALLOT_WORK_STRATEGY, "共享运营派工策略", "soms-v6"));
    }

    /**
     * 检查业务实体是否已经存在
     *
     * @param entity 业务实体
     * @return 已经存在
     */
    private boolean checkExists(RuleEntityType entity) {
        RuleEntityType entityType = ruleEntityTypeDao.findByCode(entity.getCode());
        if (Objects.nonNull(entityType)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 执行任务
     *
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResult performTask() {
        initEntities.forEach( entity -> {
            // 判断是否已经存在
            if (!checkExists(entity)) {
                ruleEntityTypeDao.save(entity);
            }
        });
        // 规则主体初始化完毕！
        return OperateResult.operationSuccess("00048");
    }
}
