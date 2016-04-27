package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDao extends Db {

	public void create(String userName, String password, String location) throws SQLException {
		// TODO: userinfo_insert
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("userinfo_insert"));
		stmt.setString(1, userName);
		stmt.setString(2, password);
		stmt.setString(3, location);
		stmt.executeUpdate();
		stmt.close();
	}

	public int getUserId(String userName) throws SQLException {
		// TODO: userinfo_get_id
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("userinfo_get_id"));
		stmt.setString(1, userName);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int autoId = rs.getInt(1);
		stmt.close();
		return autoId;
	}

	public void delete(String userName) throws SQLException {
		// TODO: userinfo_delete
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("userinfo_delete"));
		stmt.setString(1, userName);
		stmt.executeUpdate();
		stmt.close();
	}

	public void updatePassword(String userName, String password) throws SQLException {
		// TODO: userinfo_update_password
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("userinfo_update_password"));
		stmt.setString(1, userName);
		stmt.setString(2, password);
		stmt.executeUpdate();
		stmt.close();
	}
	public void updateLocation(String userName, String location) throws SQLException {
		// TODO: userinfo_update_location
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("userinfo_update_location"));
		stmt.setString(1, userName);
		stmt.setString(2, location);
		stmt.executeUpdate();
		stmt.close();
	}
}
