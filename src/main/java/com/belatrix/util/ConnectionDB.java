package com.belatrix.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDB {

	private static final String DBDATA_FILENAME = "config.properties";

	private static ConnectionDB conectionDB;
	private Connection connection;

	/**
	 * Initialize the DB connection
	 */
	private ConnectionDB() {
		Properties properties = this.getDBData();

		String driver = properties.getProperty("jdbc.driver");
		String url = properties.getProperty("jdbc.url");
		String username = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");

		try {
			Class.forName(driver).newInstance();
			this.connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * validate that there is only one instance created of ConnectionDB
	 * 
	 * @return ConnectionDB conectionDB
	 */
	public static synchronized ConnectionDB getInstance() {
		if (conectionDB == null) {
			conectionDB = new ConnectionDB();
		}
		return conectionDB;
	}

	/**
	 * Get connection
	 * 
	 * @return Connection connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Get the connection data to the BD from the properties file
	 * 
	 * @return Properties properties
	 */
	private Properties getDBData() {
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = ConnectionDB.class.getClassLoader().getResourceAsStream(DBDATA_FILENAME);
			if (is != null) {
				properties.load(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
}
