package com.java4ye.demo.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4ye.demo.model.Plugin;
import com.java4ye.demo.utils.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */

@Configuration
@Slf4j
public class PluginConfig {

    @Value("${plugin.path}")
    private String path;

    private List<Plugin> plugins=null;

    public static final Map<String,Plugin> PLUGIN_MAP =new HashMap<>();

    // TODO 刷缓存，

    @PostConstruct
    public void initPlugins(){
        List<Plugin> plugins = getPlugins();
        if (plugins==null){
            log.info("plugins is null");
            return;
        }
        for (Plugin plugin : plugins) {
            String id = plugin.getId();
            PLUGIN_MAP.putIfAbsent(id, plugin);
        }
    }

    public List<Plugin> getPlugins(){
        if (plugins!=null){
            return plugins;
        }

        String body = "";
        if (path.isEmpty()){
            path="plugins.json";
            body = FileUtil.getContentDefault("plugins.json");
        }else{
            body = FileUtil.getContent(path);
        }

        parseByJaskson(body);

        return plugins;

    }

    @SneakyThrows
    private void parseByJaskson(String body) {
        ObjectMapper objectMapper=new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(body);
        JsonNode pluginsNode = rootNode.path("plugins");

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Plugin.class);
        plugins = objectMapper.readValue(pluginsNode.toString(), javaType);
    }


}
