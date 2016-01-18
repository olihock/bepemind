package com.videaps.mindstorms.ev3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Test;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;


public class SingletonEV3Test {

	@Test
	public void test() throws NotBoundException, RemoteException, MalformedURLException {
		String host = "192.168.173.31";
		
		RMIRegulatedMotor motorB = SingletonEV3.intitialise(host).createRegulatedMotor("B", 'L');
		RMIRegulatedMotor motorC = SingletonEV3.intitialise(host).createRegulatedMotor("C", 'L');

		motorB.rotate(360, true);
		motorC.rotate(360);

		motorB.close();
		motorC.close();
	}

	@Test
	public void testTwoMotors() throws RemoteException, MalformedURLException, NotBoundException {
		
		RemoteEV3 ev3 = new RemoteEV3("192.168.173.31");
		System.out.println(ev3);
		System.out.println("host="+ev3.getHost());
		
		
		RMIRegulatedMotor motorB = ev3.createRegulatedMotor("B", 'L');
		RMIRegulatedMotor motorC = ev3.createRegulatedMotor("C", 'L');

		motorB.rotate(360, true);
		motorC.rotate(360);

		motorB.close();
		motorC.close();
		
		ev3 = new RemoteEV3("192.168.173.31");
		System.out.println(ev3);
	}

}
