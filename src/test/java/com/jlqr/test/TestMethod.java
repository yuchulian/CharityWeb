package com.jlqr.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestMethod {

	@Test
	public void testSimpleDateFormat() {
		Date thisDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		System.out.println("yyyy-MM-dd HH:mm:ss:SSS："+sdf.format(thisDate));
		System.out.println("long类型："+thisDate.getTime());
	}
	
}
