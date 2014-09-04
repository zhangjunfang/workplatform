package com.baoyuan.util;

import java.util.Arrays;
import java.util.List;

public class StringUtil {
	
	/**
	 * 判断某个字符串是否存在于数组中
	 * 
	 * @param stringArray
	 *            原数组
	 * @param source
	 *            查找的字符串
	 * @return 是否找到
	 */
	public static boolean contains(String[] stringArray, String source) {
		// 转换为list
		List<String> tempList = Arrays.asList(stringArray);
		// 利用list的包含方法，进行判断
		if (tempList.contains(source)) {
			return true;
		}
		return false;
	}
	
}
