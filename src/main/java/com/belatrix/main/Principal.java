package com.belatrix.main;

import java.io.IOException;
import java.sql.SQLException;

import com.belatrix.app.LoggerApp;
import com.belatrix.enums.LogOutput;
import com.belatrix.enums.LogType;

public class Principal {

	public static void main(String[] args) throws IOException, SQLException {
		LoggerApp.logMessage("Testing message log", LogType.MESSAGE, LogOutput.CONSOLE);
	}
}
