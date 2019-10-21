package com.belatrix.enums;

/**
 * Enumerator containing the available log types
 * 
 * @author Darwin Sernaque
 *
 */
public enum LogType {
	MESSAGE("MESSAGE", 1), ERROR("ERROR", 2), WARNING("WARNING", 3);

	private String value = "";
	private int valueDB = 0;

	private LogType(String value, int valueDB) {
		this.value = value;
		this.valueDB = valueDB;
	}

	public String getValue() {
		return value;
	}

	public int getValueDB() {
		return valueDB;
	}
}
