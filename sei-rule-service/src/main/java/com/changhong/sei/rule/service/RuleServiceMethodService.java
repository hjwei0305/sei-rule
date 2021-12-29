package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.rule.dao.RuleServiceMethodDao;
import com.changhong.sei.rule.dao.RuleTreeNodeDao;
import com.changhong.sei.rule.entity.RuleServiceMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 服务方法定义(RuleServiceMethod)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-14 09:12:00
 */
@Service("ruleServiceMethodService")
public class RuleServiceMethodService extends BaseEntityService<RuleServiceMethod> {
    @Autowired
    private RuleServiceMethodDao dao;
    @Autowired
    private RuleTreeNodeDao ruleTreeNodeDao;

    @Override
    protected BaseEntityDao<RuleServiceMethod> getDao() {
        return dao;
    }

    /**
     * 数据保存操作
     *
     * @param entity 服务方法定义
     */
    @Override
    public OperateResultWithData<RuleServiceMethod> save(RuleServiceMethod entity) {
        // 检查唯一索引：实体类型Id+路径+方法
        RuleServiceMethod ruleServiceMethod = dao.findByRuleEntityTypeIdAndPathAndMethod(entity.getRuleEntityTypeId(), entity.getPath(), entity.getMethod());
        if (Objects.nonNull(ruleServiceMethod) && !StringUtils.equals(ruleServiceMethod.getId(), entity.getId())) {
            // 业务实体类型已经配置了服务方法【{0}/{1}】！
            return OperateResultWithData.operationFailure("00035", entity.getPath(), entity.getMethod());
        }
        return super.save(entity);
    }

    /**
     * 获取规则业务实体配置的服务方法清单
     * @param ruleEntityTypeId 规则业务实体类型Id
     * @return 服务方法清单
     */
    public List<RuleServiceMethod> findByRuleEntityTypeId(String ruleEntityTypeId) {
        return dao.findByRuleEntityTypeId(ruleEntityTypeId);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 服务方法定义Id
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查规则树节点是否配置了服务方法
        if (ruleTreeNodeDao.isExistsByProperty("ruleServiceMethodId", id)) {
            // 规则树节点已经配置了服务方法，禁止删除！
            return OperateResult.operationFailure("00011");
        }
        return super.preDelete(id);
    }
}