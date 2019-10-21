package com.belatrix.util;

import java.util.logging.Level;

public class Constants {
	public static final String INVALID_PARAMS = "Invalid parameters found";
	public static final String INVALID_MESSAGE = "The message to be logged cannot be empty";
	
	public static final String LOG_PATH = System.getProperty("user.home");
	public static final String LOG_FILENAME = "logApp.xml";
	public static final Level LOG_LEVEL = Level.INFO;
}
