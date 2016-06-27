package com.videaps.cube.solving.algorithm;


/**
 * 
 * @author Oliver
 *
 */
public class AlgorithmError extends Error {
	private static final long serialVersionUID = 6411795297627547778L;

	public static final int CALCULATION_ERROR = 10;
	
	private int returnCode = 0;
	private String message = "";
	
	
	public AlgorithmError(int returnCode, String message) {
		super(returnCode+": "+message);
		this.returnCode = returnCode;
		this.message = message;
	}

	
	public int getReturnCode() {
		return returnCode;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	
	
}
