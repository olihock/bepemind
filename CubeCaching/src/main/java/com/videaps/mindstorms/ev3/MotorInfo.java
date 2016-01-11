package com.videaps.mindstorms.ev3;

import java.util.Map;

public class MotorInfo<K, V> implements Map.Entry<K, V> {
	private K key;
	private V value;

	public MotorInfo(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() { // motor port
		return this.key;
	}

	public V getValue() { // motor type
		return this.value;
	}

	public K setKey(K key) {
		return this.key = key;
	}

	public V setValue(V value) {
		return this.value = value;
	}
}