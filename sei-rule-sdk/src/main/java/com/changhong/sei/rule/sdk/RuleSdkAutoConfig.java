package com.changhong.sei.rule.sdk;

import com.changhong.sei.rule.sdk.manager.RuleEngineManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现功能: 规则引擎SDK自动配置类
 *
 * @author 王锦光 wangjg
 * @version 2021-01-17 15:39
 */
@Configuration
public class RuleSdkAutoConfig {
    @Bean
    public RuleEngineManager ruleEngineManager(){
        return new RuleEngineManager();
    }
}
