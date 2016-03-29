package de.rwth.i5.hiwi.DBUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private static ConnectionManager instance = null;

	private Connection conn = null;



	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	private boolean openConnection() {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("DBConfig.properties");
			properties.load(inputStream);
			conn = DriverManager.getConnection(properties.getProperty("URL"), properties.getProperty("USER"),
					properties.getProperty("PASSWORD"));
			return true;
		} catch (SQLException e) {
			System.err.println("Can not connected!");
		} catch (FileNotFoundException e) {
			System.err.println("DBConfig File Cannot Found!");
		} catch (IOException e) {
			System.err.println("Properties Error!");
		}
		return false;
	}

	public Connection getConnection() {

		if (conn == null) {
			if (openConnection()) {
				System.out.println("Connection opened");
				return conn;
			} else {
				System.err.println("Whops! Database Not found!");
				return null;
			}
		}
		return conn;
	}

	public void close(String promote) {
		System.out.println(promote);
		try {
			conn.close();
			conn = null;
		} catch (Exception e) {
		}
	}
}
