package com.belatrix.app;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;

import com.belatrix.enums.LogOutput;
import com.belatrix.enums.LogType;

public class LoggerAppTest {

	@Test
	public void logToFile() throws IOException, SQLException {
		LoggerApp.logMessage("Printing message on console", LogType.MESSAGE, LogOutput.FILE);
	}

	@Test
	public void logToConsole() throws IOException, SQLException {
		LoggerApp.logMessage("Printing warning on file", LogType.WARNING, LogOutput.CONSOLE);
	}

	@Ignore("Have the database connection ready before running to this test")
	@Test
	public void logToDB() throws IOException, SQLException {
		//LoggerApp.logMessage("Insert error message on database", LogType.ERROR, LogOutput.DATABASE);
	}

}
