package com.belatrix.app;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger {
	// Many attributes to assign the log type and the output of the log, this can be handled with 2 enumerators
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;	
	
	private boolean initialized; //Atribute is not used
	private static Map dbParams; // Remove and use a properties file for conection data
	private static Logger logger;

	//The constructor method has many parameters
	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
		//Assignments to unnecessary parameters
		logger = Logger.getLogger("MyLog");
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}

	//The method name must start with a lowercase 
	//Unnecessary parameters: message, warning and error
	public static void LogMessage(String messageText, boolean message, boolean warning, boolean error)
			throws Exception {
		messageText.trim(); //There may be a NullPointerException, check null before calling the trim method
		if (messageText == null || messageText.length() == 0) {
			return; // Throws an exception indicating that the message is required 
		}
		
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		
		//Innecesary validation for mesage, warning and error parameters, because at this point the message type is already stored
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}

		//Handle the connection to a database in a separate class
		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
				+ ":" + dbParams.get("portNumber") + "/", connectionProps);

		int t = 0; //Improve variable name, this does not describe anything
		
		//Work with an enumerator that has the type of message to avoid these validations
		if (message && logMessage) {
			t = 1;
		}

		if (error && logError) {
			t = 2;
		}

		if (warning && logWarning) {
			t = 3;
		}

		Statement stmt = connection.createStatement(); // Use Prepared Statement to avoid SQL Injection

		String l = null; //Improve variable name, this does not describe anything
		
		//Store the name of the log and the folder in a properties file or in constants
		File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}

		FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
		ConsoleHandler ch = new ConsoleHandler();
		
		//Unnecessary validation block since variable "l" is not used later
		//If the variable "l" is necessary, work with an enumerator that has the type of message to avoid these validations
		//The date is obtained many times, it should be obtained only once at this point		
		if (error && logError) {
			l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}

		if (warning && logWarning) {
			l = l + "warning " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}

		if (message && logMessage) {
			l = l + "message " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}
		
		//Improve validations, this contains duplicate code
		if (logToFile) {
			logger.addHandler(fh);
			logger.log(Level.INFO, messageText);
		}

		if (logToConsole) {
			logger.addHandler(ch);
			logger.log(Level.INFO, messageText);
		}
		
		if (logToDatabase) {
			//In the query you could replace the variable "t" with the proposed type of message enumerator
			stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t) + ")");
		}
	}

}
