

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

public class LoggerTest {

	@Before
	public void configureLogger() {
		System.setProperty( "java.util.logging.config.file", "logging.properties" );
		
	}
	
	
	@Test
	public void test() {
		Logger logger =  Logger.getLogger("com.videaps.cube");
		logger.finest("Finest enabled");
	}

}
