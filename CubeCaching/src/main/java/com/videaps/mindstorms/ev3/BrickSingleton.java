package com.videaps.mindstorms.ev3;

import java.util.HashMap;
import java.util.Map;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;


public class BrickSingleton {
	private static BrickSingleton instance = null;
	
	private Map<String, RegulatedMotor> motors = new HashMap<String, RegulatedMotor>();

	
	private BrickSingleton() {
	}
	
	private BrickSingleton(MotorInfo<String, String> motorInfo) {
		String motorPort = motorInfo.getKey();
		if(!motors.containsKey(motorPort)) {
			RegulatedMotor motor = createRegulatedMotor(motorInfo);
			motors.put(motorPort, motor);
		}
	}
	
	
	public static BrickSingleton getInstance(MotorInfo<String, String> motorInfo) {
		if(instance == null) {
			instance = new BrickSingleton();
		}
		return instance;
	}

	
	public RegulatedMotor getRegulatedMotor(String motorPort) {
		RegulatedMotor motor = motors.get(motorPort);
		return motor;
	}
	
	
	private Port toMotorPort(String motorPort) {
		if("A".equals(motorPort)) {
			return MotorPort.A;
		} else if("B".equals(motorPort)) {
			return MotorPort.B;
		} else if("C".equals(motorPort)) {
			return MotorPort.C;
		} else if("D".equals(motorPort)) {
			return MotorPort.D;
		} else {
			throw new IllegalArgumentException("Unknown motor port: "+motorPort);
		}
	}
	
	
	private RegulatedMotor createRegulatedMotor(MotorInfo<String, String> motorInfo) {
		String motorPort = motorInfo.getKey();
		String motorType = motorInfo.getValue();
		
		if("L".equals(motorType)) {
			return new EV3LargeRegulatedMotor(toMotorPort(motorPort));
		} else if("M".equals(motorType)) {
			return new EV3MediumRegulatedMotor(toMotorPort(motorPort));
		} else {
			throw new IllegalArgumentException("Unknown motor type: "+motorType);
		}
	}

}
