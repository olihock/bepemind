/*
 Copyright (c) 2016 Videa Project Services GmbH

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 and associated documentation files (the "Software"), to deal in the Software without restriction, 
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell copies of the Software,and to permit persons to whom the Software is furnished to do so, 
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial 
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
 NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.videaps.mindstorms.ev3;

import java.rmi.RemoteException;

import org.junit.Test;

import com.videaps.mindstorms.models.CubeFinder;

import lejos.utility.Delay;


public class CubeFinderTest {

	@Test
	public void scan() throws RemoteException {
		CubeFinder cubeFinder = new CubeFinder();
		for(int i = 0 ; i < 25; i++) {
			float distance = cubeFinder.scan();
			Delay.msDelay(1000);
			System.out.println("distance="+distance);
		}
		cubeFinder.shutdown();
	}
	
	
	@Test
	public void searchCube() throws RemoteException {
		CubeFinder cubeFinder = new CubeFinder();
		
		cubeFinder.moveForward();
		
		float distance = Float.MAX_VALUE;
		while(distance >= 0.3f) {
			distance = cubeFinder.scan();
			Delay.msDelay(250);
			System.out.println("distance="+distance);
		}
		
		cubeFinder.stop();
		cubeFinder.shutdown();
	}
	
	
}
