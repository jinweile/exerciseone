package com.reg.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reg.console1;

public class console1Test {

	@Before
	public void setUp() throws Exception {
		System.err.println("setup");
	}

	@After
	public void tearDown() throws Exception {
		System.err.println("after");
	}

	@Test
	public void testPrint() {
		console1.print();
	}
	
	//@Test
	public void testCrc32() throws IOException {
		console1.crc32();
	}
	
	//@Test
	public void testcattest() throws Exception {
		console1.cattest();
	}

}
