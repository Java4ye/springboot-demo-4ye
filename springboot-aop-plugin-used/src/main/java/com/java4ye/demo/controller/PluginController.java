package com.java4ye.demo.controller;

import com.java4ye.demo.config.DefaultPluginFactory;
import com.java4ye.demo.config.PluginConfig;
import com.java4ye.demo.model.Plugin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController
@RequestMapping("plugin")
@RequiredArgsConstructor
public class PluginController {

    private final DefaultPluginFactory pluginFactory;

    @GetMapping("list")
    public Collection<Plugin> listPlugins(){
        Map<String, Plugin> pluginMap = PluginConfig.PLUGIN_MAP;
        Collection<Plugin> values = pluginMap.values();
        System.out.println(values);
        return values;
    }

    @GetMapping("/{id}/active")
    public String activePlugin(@PathVariable("id") String id ){
        pluginFactory.activePlugin(id);
        return "Success active plugin";
    }

    @GetMapping("/{id}/inactive")
    public String inactivePlugin(@PathVariable("id") String id ){
        pluginFactory.inactivePlugin(id);
        return "Success inactive plugin";

    }


}
