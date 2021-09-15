package com.java4ye.demo.config;

import com.java4ye.demo.model.Plugin;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Slf4j
@Service
public class DefaultPluginFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final Map<String,Advice> CACHE_ADVICE_MAP =new HashMap<>();
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * 激活插件
     * @param pluginId
     */
    public void activePlugin (String pluginId){

        // 获取插件
        Plugin plugin = getPlugin(pluginId);
        plugin.setActive(true);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            String className = plugin.getClazz();

            // 跳过 非 Advised 对象 和 已经增强过的 Advised 对象
            if (bean==this||!(bean instanceof Advised)|| findAdvice((Advised) bean, className)){
                continue;
            }

            // 获取 Advice
            Advice pluginAdvice = getAdvice(plugin, className);

            // 激活插件
            ((Advised) bean).addAdvice(pluginAdvice);

        }

    }

    /**
     * 移除插件
     * @param pluginId
     */
    @SneakyThrows
    public void inactivePlugin(String pluginId){

        // 获取插件
        Plugin plugin = getPlugin(pluginId);

        plugin.setActive(false);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            String className = plugin.getClazz();

            // 跳过 非 Advised 对象
            if (bean==this||!(bean instanceof Advised)){
                continue;
            }

            Advisor[] advisors = ((Advised) bean).getAdvisors();
            for (Advisor advisor : advisors) {
                Advice advice = advisor.getAdvice();
                if (advice.getClass().getName().equals(className)){
                    ((Advised) bean).removeAdvice(advice);
                }
            }

//            // 获取 Advice
//            Advice pluginAdvice = getAdvice(plugin, className);
//
//            ((Advised) bean).removeAdvice(pluginAdvice);

        }
    }

    private Plugin getPlugin(String pluginId) {
        Plugin plugin = PluginConfig.PLUGIN_MAP.get(pluginId);
        if (plugin==null){
            log.error("cannot get plugin with pluginId: {}",pluginId);
            throw new RuntimeException("cannot get plugin with pluginId: "+pluginId);
        }
        return plugin;
    }

    /**
     * 获取 Advice
     * @param plugin
     * @param className
     * @return
     */
    @SneakyThrows
    private Advice getAdvice(Plugin plugin, String className) {
        // 从缓存中获取
        Advice advice = CACHE_ADVICE_MAP.get(className);
        if (advice!=null){
            return advice;
        }

        // 获取 Jar 包路径
        String jarPath = plugin.getJarPath();

        File f = new File(jarPath);
        URL url = f.toURI().toURL();

        ClassLoader classLoader;
        Class<?> pluginClass = classIsPresent(className);
        if (pluginClass==null){
             classLoader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());
             pluginClass = classLoader.loadClass(className);
        }

        // 创建 Advice
        Advice pluginAdvice=(Advice)pluginClass.getDeclaredConstructor(null).newInstance();

//        if (plugin.getCache()){
            // 放到 缓存中
            CACHE_ADVICE_MAP.put(className,pluginAdvice);
//        }

        return pluginAdvice;
    }

    public Class<?> classIsPresent(String name) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    /**
     * 判断是否增强过
     * @param bean
     * @param className
     * @return
     */
    private boolean findAdvice(Advised bean, String className) {
        // 获取 Advisor
        Advisor[] advisors = bean.getAdvisors();
        for (Advisor advisor : advisors) {
            Advice advice = advisor.getAdvice();
            // 是否增强过 Advice
            if (advice.getClass().getName().equals(className)){
                return true;
            }
        }
        return false;
    }

}
