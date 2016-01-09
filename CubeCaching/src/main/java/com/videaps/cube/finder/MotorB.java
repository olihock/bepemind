package com.videaps.cube.finder;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class MotorB implements JavaDelegate {
	private static final Logger logger = Logger.getLogger("com.videaps.cube.finder");
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		logger.finest("delegateExecution="+delegateExecution);
		
		EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.B);
		motor.forward();
		Delay.msDelay(500);
		motor.stop(true);

		motor.close();
	}

}
