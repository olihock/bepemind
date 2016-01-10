package com.videaps.cube.finder;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;

import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.RegulatedMotor;


public class Motor implements JavaDelegate {
	private static final Logger logger = Logger.getLogger("com.videaps.cube.finder");
	
	private FixedValue motorPort;
	private FixedValue motorType;
	private FixedValue rotationCount;
	

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		logger.finest("delegateExecution="+delegateExecution);
		
		String hostVar = delegateExecution.getVariable("host", String.class);
		logger.finest("hostVar="+hostVar);
				
		String motorPortValue = ""+motorPort.getValue(delegateExecution);
		logger.fine("motorPortValue="+motorPortValue);
		
		char motorTypeValue = (""+motorType.getValue(delegateExecution)).toCharArray()[0];
		logger.fine("motorTypeValue="+motorTypeValue);

		int rotationCountValue = Integer.valueOf(""+rotationCount.getValue(delegateExecution));
		logger.fine("rotationCountValue="+rotationCountValue);
		
		RemoteRequestEV3 brick = new RemoteRequestEV3(hostVar);
		RegulatedMotor motor = brick.createRegulatedMotor(motorPortValue, motorTypeValue); 

		motor.rotate(rotationCountValue*360, true);

//		motor.stop();
//		motor.close();

		
		logger.finest("return");
	}


}
