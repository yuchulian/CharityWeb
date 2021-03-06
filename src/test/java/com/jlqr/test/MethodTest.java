package com.jlqr.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class MethodTest {

	@Test
	public void testSimpleDateFormat() {
		Date thisDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		System.out.println("yyyy-MM-dd HH:mm:ss:SSS："+sdf.format(thisDate));
		System.out.println("long类型："+thisDate.getTime());
	}

	@Test
	public void testMd5() {
		System.out.println(DigestUtils.md5Hex("admin"));
	}
	
}
