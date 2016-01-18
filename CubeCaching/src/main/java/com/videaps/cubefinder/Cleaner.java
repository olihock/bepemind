package com.videaps.cubefinder;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.videaps.mindstorms.ev3.SingletonEV3;

public class Cleaner implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		
		while(SingletonEV3.getInstance().getRegulatedMotor("A").isMoving()) {
		}
		SingletonEV3.getInstance().getRegulatedMotor("A").close();
		
		while(SingletonEV3.getInstance().getRegulatedMotor("B").isMoving()) {
		}
		SingletonEV3.getInstance().getRegulatedMotor("B").close();

		while(SingletonEV3.getInstance().getRegulatedMotor("C").isMoving()) {
		}
		SingletonEV3.getInstance().getRegulatedMotor("C").close();
	}

}
