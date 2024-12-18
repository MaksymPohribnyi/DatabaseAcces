package ua.com.pohribnyi.jdbcpractise.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtils {

	private static final String PROPERTIES_PATH = "src/main/resources/application.properties";
	private static Connection connection = null;

	public static Connection getCurrentConnection() {
		return connection;
	}

	public static Connection getConnection() {

		if (connection == null) {

			Properties properties = new Properties();

			try {

				properties.load(new FileInputStream(PROPERTIES_PATH));

				Class.forName(properties.getProperty("db.driver"));

				String dbURL = properties.getProperty("db.url");
				String username = properties.getProperty("db.username");
				String password = properties.getProperty("db.password");

				connection = DriverManager.getConnection(dbURL, username, password);

			} catch (ClassNotFoundException | SQLException | IOException e) {

				System.out.println("There was a problem getting a connection: ");
				e.printStackTrace();

				System.exit(1);

			}
		}

		return connection;

	}

	public static PreparedStatement getPreparedStatement(String SQL) throws SQLException {
		return getConnection().prepareStatement(SQL);
	}

	public static PreparedStatement getPreparedStatementWithKeys(String SQL) throws SQLException {
		return getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	}

}
