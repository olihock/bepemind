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
package com.videaps.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModuloTest {

	@Test
	public void modulo() {
		int modulo = 3 % 2;
		assertEquals(1, modulo);
	}
	
	@Test 
	public void calculate() {
		int x = 70, d = 20;
		int t = x / d;
		System.out.println("t="+t);
		int m = x % d;
		System.out.println("m="+m);
		int n = t + m;
		assertEquals(3, n);
		System.out.println("n="+n);
	}
	@Test
	public void roundPlusModulo() {
		int n = calculateIterationCount(9, 2);
		assertEquals(5, n);
	}

	@Test
	public void calculateSearchAreaIterationCount() {
		int n = calculateIterationCount(60, 20);
		assertEquals(3, n);
	}

	private int calculateIterationCount(int x, int d) {
		int r = Math.floorDiv(x, d);
		int m = x % d;
		int n = r + m;
		return n;
	}

}
