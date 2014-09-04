package com.baoyuan;

import org.junit.Test;

public class ReplaceTest {

	@Test
	public void replaceTest(){
		String temp = "&lt;div style='text-align:center;'&gt;	&lt;span style='line-height:1.5;'&gt;fff&lt;/span&gt;&lt;/div&gt;";
		temp = temp.replace("&lt;" ,"<");
		temp = temp.replace("&gt;", ">");
		System.out.println("ReplaceTest temp:"+temp);
	}
}
