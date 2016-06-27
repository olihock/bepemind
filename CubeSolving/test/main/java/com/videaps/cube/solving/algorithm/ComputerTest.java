/**
 * 
 */
package com.videaps.cube.solving.algorithm;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Oliver
 *
 */
public class ComputerTest {
	private static final Logger logger = Logger.getLogger("CubeSolverTest");
	
	private Computer rubiksCube = new Computer();
	
	String[] initialState = { "L:WWGROOYWG", "R:BROBROYBB", "U:RRWYWWWRR", "D:BOBGBGWOG", "F:OYYYYYOWO", "B:YGGBGBRGR" };
	String[] cubeState = { "L:RYWRRBGBB", "R:YWWGOGGGG", "U:YGRRGROOO", "D:WOYWBWOOY", "F:BBBYWORWO", "B:GYBRYBRYW" };
	public static final String[] SINGLE_LAYER_D = { "L:GGGGGGRRR", "R:BBBBBBOOO", "U:WWWWWWWWW", "D:YYYYYYYYY", "F:RRRRRRBBB", "B:OOOOOOGGG" };
	public static final String[] SINGLE_LAYER_D2 = { "L:GGGGGGBBB", "R:BBBBBBGGG", "U:WWWWWWWWW", "D:YYYYYYYYY", "F:RRRRRROOO", "B:OOOOOORRR" };
	public static final String[] SINGLE_LAYER_Rprime = { "L:GGGGGGGGG", "R:BBBBBBBBB", "U:WWRWWRWWR", "D:YYOYYOYYO", "F:RRYRRYRRY", "B:WOOWOOWOO" };
	public static final String[] SINGLE_LAYER_L = { "L:GGGGGGGGG", "R:BBBBBBBBB", "U:RWWRWWRWW", "D:OYYOYYOYY", "F:YRRYRRYRR", "B:OOWOOWOOW" };
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void singleLayer_D() throws IOException {
		String algorithm = runSolver(SINGLE_LAYER_D);
		logger.info("algorithm: "+algorithm);
	}

	@Test
	public void singleLayer_D2() throws IOException {
		String algorithm = runSolver(SINGLE_LAYER_D2);
		logger.info("algorithm: "+algorithm);
	}

	@Test
	public void singleLayer_Rprime() throws IOException {
		String algorithm = runSolver(SINGLE_LAYER_Rprime);
		logger.info("algorithm: "+algorithm);
	}

	@Test
	public void singleLayer_L() throws IOException {
		String algorithm = runSolver(SINGLE_LAYER_L);
		logger.info("algorithm: "+algorithm);
	}

	private String runSolver(String[] cubeState) throws IOException {
		String algorithm = rubiksCube.solveCube(Arrays.asList(cubeState));
		return algorithm;
	}
	
	
	/**
	 * L:WWGROOYWG R:BROBROYBB U:RRWYWWWRR D:BOBGBGWOG F:OYYYYYOWO B:YGGBGBRGR
	 * @throws IOException 
	 */
	@Test
	public void test() throws IOException {
		String algorithm = rubiksCube.solveCube(Arrays.asList(cubeState));
		logger.info("algorithm: "+algorithm);
	}

	
	@Test
	public void firstGo() throws IOException {
		String[] cubeState = { "L:OOOOOOOOO", "R:RRRRRRRRR", "U:WWGWWGWWG", "D:YYBYYBYYB", "F:GGYGGYGGY", "B:WBBWBBWBB" };
		String algorithm = rubiksCube.solveCube(Arrays.asList(cubeState));
		logger.info("algorithm: "+algorithm);
	}
	
	
	@Test
	public void secondGo() throws IOException {
		String[] cubeState = { "L:OBBWBBWBB", "R:GGRGGYGGY", "U:GGGWWOWWO", "D:YYRYYRBBB", "F:OOYOOYOOY", "B:WWWRRRRRR" };
		String algorithm = rubiksCube.solveCube(Arrays.asList(cubeState));
		logger.info("algorithm: "+algorithm);
		
	}
	
	
	@Test
	public void solveCube() throws InterruptedException, IOException {
		String[] cubeState = { "L:BYRGBOGGG", "R:BBRBGRRYW", "U:WOGRRYYBY", "D:WYBOOOYGB", "F:GWOBWROGY", "B:WWRWYWORO" };
		List<String> cubeStateList = Arrays.asList(cubeState);
		
		String algorithm = new Computer().solveCube(cubeStateList);
		System.out.println("algorithm: "+algorithm);
	}

	
	@Test 
	public void findValidState() throws IOException {
		String[] cubeState = { "U:WWOWWOWWO", "F:OOYOOYOOY", "D:YYRYYRYYR", "B:WRRWRRWRR", "L:BBBBBBBBB", "R:GGGGGGGGG" };
		List<String> cubeStateList = Arrays.asList(cubeState);
		
		String algorithm = new Computer().solveCube(cubeStateList);
		System.out.println("algorithm: "+algorithm);
	}
	
}
