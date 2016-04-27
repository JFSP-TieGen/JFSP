package db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Db {

	protected Connection connection;
	protected Properties dbProps;

	public Db() {
		try {
			Connection c1 = null;

			String ddlFile = "ddl.properties";
			Properties dbProps = new Properties();
			dbProps.load(new FileReader(ddlFile));

			String url1 = dbProps.getProperty("jdbc_url");
			String user = dbProps.getProperty("user_name");
			String password = dbProps.getProperty("password");

			c1 = DriverManager.getConnection(url1, user, password);
			if (c1 != null) {
				System.out.println("Connected to tiegen database");
			}
			System.out.println("Creating a table in the database...");

			Statement stmt = c1.createStatement();
			String sqlUserInfo = dbProps.getProperty("userinfo_ddl");
			stmt.executeUpdate(sqlUserInfo);
			stmt.close();

			stmt = c1.createStatement();
			String sqlSPInfo = dbProps.getProperty("serviceprovider_info_ddl");
			stmt.executeUpdate(sqlSPInfo);
			stmt.close();

			stmt = c1.createStatement();
			String sqlBookings = dbProps.getProperty("bookings_ddl");
			stmt.executeUpdate(sqlBookings);
			stmt.close();
			
			stmt = c1.createStatement();
			String sqlRatings = dbProps.getProperty("ratings_ddl");
			stmt.executeUpdate(sqlRatings);
			stmt.close();
			
			stmt = c1.createStatement();
			String sqlBookMarks = dbProps.getProperty("bookmarks_ddl");
			stmt.executeUpdate(sqlBookMarks);
			stmt.close();

			this.connection = c1;
			this.dbProps = dbProps;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}



