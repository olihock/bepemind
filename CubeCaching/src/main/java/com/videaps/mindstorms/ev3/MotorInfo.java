/*
 * (C) Copyright 2016 Videa Project Services GmbH (http://videa-services.com)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.videaps.mindstorms.ev3;


/**
 * 
 * @author Oliver
 *
 * @param <K> Motor Port
 * @param <V> Motor Type
 */
public class MotorInfo<K, V> {
	private K port;
	private V type;

	public MotorInfo(K port, V type) {
		this.port = port;
		this.type = type;
	}

	public K getPort() {
		return this.port;
	}

	public V getType() {
		return this.type;
	}

	public K setPort(K port) {
		return this.port = port;
	}

	public V setType(V type) {
		return this.type = type;
	}
	
}