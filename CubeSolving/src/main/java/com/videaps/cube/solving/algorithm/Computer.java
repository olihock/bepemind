package com.videaps.cube.solving.algorithm;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import ch.randelshofer.gui.*;
import ch.randelshofer.rubik.parser.*;
import ch.randelshofer.rubik.solver.CubeParser;
import ch.randelshofer.rubik.solver.FaceletCube;
import ch.randelshofer.rubik.solver.KociembaCube;
import ch.randelshofer.rubik.solver.Solver;


public class Computer {
	private static final Logger LOGGER = Logger.getLogger("com.videaps.cube.solving.algorithm.Computer");
	
	
	public Computer() {
	}
	
	
	public String solveCube(List<String> cubeColorState) throws IOException {
		assert(cubeColorState != null);
		
		String[] cubeColorStateArray = cubeColorState.toArray(new String[cubeColorState.size()]);
		String specification = buildFaceSpecification(cubeColorStateArray);
		Solver solver = calculate(specification);
		String algorithm = solver.getSolution().toString(new DefaultNotation(), null);
		
		return algorithm;
	}
	
	
	private String buildFaceSpecification(String[] faces) {
		assert(faces != null);
		
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < faces.length; i++) {
            if (i > 0) {
                buf.append(' ');
            }
            buf.append(faces[i]);
        }
        String faceSpecification = buf.toString();
        
        return faceSpecification;
	}
	
	private Solver calculate(String faceSpecification) {
        // The FaceletCube represents the cube by the markings
        FaceletCube faceletCube = new FaceletCube();
        CubeParser cubeParser = new CubeParser();
        int status;
        if ((status = cubeParser.parseInput(faceSpecification, faceletCube)) != CubeParser.VALID) {
        	String message = "Error parsing input: " + cubeParser.getErrorText(status);
        	throw new AlgorithmError(AlgorithmError.CALCULATION_ERROR, message);
        }

        // Validate the facelet representation in terms  of
        //  legal cubie markings, permutation, and parity
        //   and initialize a "standard" cube.  The standard
        //   cube represents the cube state in terms of cubie
        //   permutation and parity.
        KociembaCube cube = new KociembaCube();
        if ((status = faceletCube.validate(cube)) != FaceletCube.VALID) {
            String message = "Error validating cube: " + faceletCube.getErrorText(status);
        	throw new AlgorithmError(AlgorithmError.CALCULATION_ERROR, message);
        }

        ProgressObserver progressMonitor = new ProgressPrinter();

        // Create a solver, initialize the move mapping and
        //   pruning tables, and invoke the search for a
        //   solution.  Since the cube is in a valid configuration
        //   at this point, a solution should always be found.
        Solver solver = new Solver();
        Solver.initializeTables(progressMonitor);

        Notation notation = new DefaultNotation();
        int solveResult = solver.solve(progressMonitor, cube, notation);
        
        LOGGER.info(solveResult + "-" + mapResult(solveResult));
		return solver;
	}
	
	private String mapResult(int code) {
        String result;
        switch (code) {
        case Solver.ABORT:
            result = "Solver aborted";
            break;
        case Solver.FOUND:
        	result = "Solution found";
            break;
        case Solver.NOT_FOUND:
        	result = "No solution found";
            break;
        case Solver.OPTIMUM_FOUND:
        	result = "Optimal Solution found";
            break;
        default:
        	result = "Unknown error";
        	break;
        }
        return result;
	}
	

}
