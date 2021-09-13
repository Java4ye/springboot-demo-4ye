package com.java4ye.demo.config;

import com.java4ye.demo.model.Plugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
public class DefaultPluginFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void activePlugin(Integer pluginId){

        Plugin plugin = PuginConfig.PLUGIN_MAP.get(pluginId);
        if (plugin==null){
            log.error("cannot get plugin with pluginId: {}",pluginId);
            return;
        }

        plugin.setActive(true);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            if (bean==this){
                continue;
            }

        }
    }

}
