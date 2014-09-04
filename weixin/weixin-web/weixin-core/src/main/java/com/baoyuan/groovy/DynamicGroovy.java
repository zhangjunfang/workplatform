package com.baoyuan.groovy;

import java.io.File;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DynamicGroovy {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private GroovyObject groovyObject;
	
	private GroovyClassLoader loader;
	
	public DynamicGroovy(){
		loader = new GroovyClassLoader();
	}
	
	public Object invokeScriptMethod(String scriptName, String methodName,
			Object args) {
		try {
			Class<?> groovyClass = loader.parseClass(new File(scriptName));
			groovyObject = (GroovyObject) groovyClass.newInstance();
			return groovyObject.invokeMethod(methodName, args);
		} catch (Exception e) {
			logger.error("DynamicGroovy Exception",e);
			return null;
		}
	}
	
	public Object getProperty(String name) {
		return groovyObject.getProperty(name);
	}
}
