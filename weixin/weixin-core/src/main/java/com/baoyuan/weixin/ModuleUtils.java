package com.baoyuan.weixin;

import java.util.HashMap;
import java.util.Map;

public class ModuleUtils {

	private static Map<String, String> modulesMap = new HashMap<String, String>();

	public static void putSign(String moduleId, String moduleSign) {
		modulesMap.put(moduleId, moduleSign);
	}

	public static String getSign(String moduleId) {
		return modulesMap.get(moduleId);
	}

}
