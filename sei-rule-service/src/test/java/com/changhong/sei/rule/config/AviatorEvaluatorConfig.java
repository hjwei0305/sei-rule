package com.changhong.sei.rule.config;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.spring.SpringContextFunctionLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.assertj.ApplicationContextAssertProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能:aviator配置类
 * @date 2020/12/25 15:02
 */
@Configuration
public class AviatorEvaluatorConfig implements ApplicationContextAware {

   /* @Autowired
    ApplicationContext applicationContext;
*/
    /**
     * 从spring 容器加载自定义AviatorFunction
     *
     * @param applicationContext spring容器
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextFunctionLoader loader = new SpringContextFunctionLoader(applicationContext);
        //注册函数
        AviatorEvaluator.addFunctionLoader(loader);
    }

    // //@Override
    // public void afterPropertiesSet() throws Exception {
    //     SpringContextFunctionLoader loader = new SpringContextFunctionLoader(applicationContext);
    //     //注册函数
    //     AviatorEvaluator.addFunctionLoader(loader);
    // }
}
