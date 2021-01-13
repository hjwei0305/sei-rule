package com.changhong.sei.rule.controller;

import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.rule.api.RuleTreeNodeApi;
import com.changhong.sei.rule.dto.RuleTreeNodeDto;
import com.changhong.sei.rule.entity.RuleTreeNode;
import com.changhong.sei.rule.service.RuleTreeNodeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规则树节点(RuleTreeNode)控制类
 *
 * @author sei
 * @since 2021-01-13 16:29:55
 */
@RestController
@Api(value = "RuleTreeNodeApi", tags = "$tool.trim($!{tableInfo.comment})服务")
@RequestMapping(path = "ruleTreeNode", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleTreeNodeController extends BaseTreeController<RuleTreeNode, RuleTreeNodeDto>
        implements RuleTreeNodeApi {
    /**
     * 规则树节点服务对象
     */
    @Autowired
    private RuleTreeNodeService service;

    @Override
    public BaseTreeService<RuleTreeNode> getService() {
        return service;
    }

}