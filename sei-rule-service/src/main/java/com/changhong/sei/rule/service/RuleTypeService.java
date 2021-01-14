package com.changhong.sei.rule.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.rule.dao.RuleEntityTypeDao;
import com.changhong.sei.rule.dao.RuleTreeNodeDao;
import com.changhong.sei.rule.dao.RuleTypeDao;
import com.changhong.sei.rule.entity.RuleEntityType;
import com.changhong.sei.rule.entity.RuleType;
import com.changhong.sei.rule.service.utils.PinYinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 规则类型(RuleType)业务逻辑实现类
 *
 * @author sei
 * @since 2021-01-13 16:15:11
 */
@Service("ruleTypeService")
public class RuleTypeService extends BaseEntityService<RuleType> {
    @Autowired
    private RuleTypeDao dao;
    @Autowired
    private RuleTreeNodeDao ruleTreeNodeDao;
    @Autowired
    private RuleEntityTypeDao ruleEntityTypeDao;

    @Override
    protected BaseEntityDao<RuleType> getDao() {
        return dao;
    }

    /**
     * 数据保存操作
     *
     * @param entity 规则类型
     */
    @Override
    public OperateResultWithData<RuleType> save(RuleType entity) {
        // 系统给号:类型代码-名称拼英简写（小写）
        if (StringUtils.isBlank(entity.getCode())) {
            // 获取规则实体类型
            RuleEntityType entityType = ruleEntityTypeDao.findOne(entity.getRuleEntityTypeId());
            if (Objects.isNull(entityType)) {
                // 规则实体类型【{0}】不存在！
                return OperateResultWithData.operationFailure("00019", entity.getRuleEntityTypeId());
            }
            // 生成代码
            String code = StringUtils.lowerCase(entityType.getCode())+"-"+PinYinUtil.getLowerCase(entity.getName(), Boolean.FALSE);
            entity.setCode(code);
        } else {
            // 检查代码，禁止修改
            if (!entity.isNew()) {
                RuleType ruleType = dao.findOne(entity.getId());
                if (Objects.nonNull(ruleType)
                        && !StringUtils.equals(ruleType.getCode(), entity.getCode())) {
                    // 规则类型的代码禁止修改！
                    return OperateResultWithData.operationFailure("00022");
                }
            }
        }
        return super.save(entity);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 规则类型Id
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查规则类型是否配置了规则树
        if (ruleTreeNodeDao.isExistsByProperty("ruleTypeId", id)) {
            // 规则类型已经配置了规则树，禁止删除！
            return OperateResult.operationFailure("00014");
        }
        return super.preDelete(id);
    }
}