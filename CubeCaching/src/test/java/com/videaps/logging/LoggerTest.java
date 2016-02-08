package com.videaps.logging;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

public class LoggerTest {

	@Before
	public void prepare() {
	}
	
	
	@Test
	public void test() {
		Logger logger =  Logger.getLogger("com.videaps.cube");
		logger.finest("Finest enabled");
	}

}
