package com.reg.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reg.http;

public class httpTest {

	@Before
	public void setUp() throws Exception {
		System.err.println("start...");
	}

	@After
	public void tearDown() throws Exception {
		System.err.print("end.");
	}

	@Test
	public void testDownload() throws Exception {
		String url = "http://news.sohu.com/s2005/shishi.shtml";
		http.download(url,"gbk");
	}

}
