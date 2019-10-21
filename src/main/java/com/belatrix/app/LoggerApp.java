package com.belatrix.app;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.belatrix.enums.LogOutput;
import com.belatrix.enums.LogType;
import com.belatrix.util.ConnectionDB;
import com.belatrix.util.Constants;

/**
 * 
 * @author Darwin Sernaque
 *
 */
public class LoggerApp {
	private static final Logger logger = Logger.getLogger("MyLog");

	private LoggerApp() {
	}

	public static void logMessage(String messageText, LogType logType, LogOutput logOutput)
			throws IOException, SQLException {
		if (messageText == null || logType == null || logOutput == null) {
			throw new IllegalArgumentException(Constants.INVALID_PARAMS);
		}
		messageText = messageText.trim();

		if (messageText.isEmpty()) {
			throw new IllegalArgumentException(Constants.INVALID_MESSAGE);
		}

		if (logOutput == LogOutput.FILE) {
			String logFilePath = Constants.LOG_PATH + File.separator + Constants.LOG_FILENAME;
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			putLogInFileOrConsole(messageText, Constants.LOG_LEVEL, new FileHandler(logFilePath));
		} else if (logOutput == LogOutput.CONSOLE) {
			putLogInFileOrConsole(messageText, Constants.LOG_LEVEL, new ConsoleHandler());
		} else if (logOutput == LogOutput.DATABASE) {
			String formattedMessage = logMessageFormat(messageText, logType);
			putLogInDB(formattedMessage, logType);
		}
	}

	private static void putLogInFileOrConsole(String message, Level logLevel, Handler logHandler) {
		LogManager.getLogManager().reset();
		logger.addHandler(logHandler);
		logger.log(logLevel, message);
	}

	private static String logMessageFormat(String message, LogType logType) {
		return String.format("%s %s %s", logType.getValue(),
				DateFormat.getDateInstance(DateFormat.LONG).format(new Date()), message);
	}

	private static void putLogInDB(String formattedMessage, LogType logType) throws SQLException {
		ConnectionDB dbConnector = ConnectionDB.getInstance();
		Connection connection = dbConnector.getConnection();

		PreparedStatement ps = connection.prepareStatement("insert into logsApp (type, message) values (?, ?)");
		ps.setInt(1, logType.getValueDB());
		ps.setString(2, formattedMessage);
		ps.execute();
	}
}
