package com.java4ye.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4ye.demo.model.Plugin;
import com.java4ye.demo.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {

		ObjectMapper objectMapper=new ObjectMapper();

		String body = FileUtil.getContentDefault("plugins.json");
		System.out.println(body);

		JsonNode rootNode = objectMapper.readTree(body);
		JsonNode plugins = rootNode.path("plugins");

		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Plugin.class);
        List<Plugin> pluginsList = objectMapper.readValue(plugins.toString(), javaType);
		for (Plugin plugin : pluginsList) {
			System.out.println(plugin);
		}



	}

}
