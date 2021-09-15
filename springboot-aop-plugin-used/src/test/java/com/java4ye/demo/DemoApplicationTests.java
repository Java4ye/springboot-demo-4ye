package com.java4ye.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java4ye.demo.model.Plugin;
import com.java4ye.demo.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;

//@SpringBootTest
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

	public void getJarName(String jarFile) throws Exception {

		try{
			//通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
			File f = new File(jarFile);
			URL url = f.toURI().toURL();
			URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());
			URL[] urLs = myClassLoader.getURLs();
			for (URL urL : urLs) {
				System.out.println(urL);
			}

			Class<?> aClass = myClassLoader.loadClass("com.java4ye.demo.advice.MethodCountingTimesPlugin");
			System.out.println(aClass);

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testClassLoader() throws Exception {

		getJarName("I:/java4ye-aop-plugin.jar");



	}



}
