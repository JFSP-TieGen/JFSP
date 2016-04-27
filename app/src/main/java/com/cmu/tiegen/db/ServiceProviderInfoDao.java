package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceProviderInfoDao extends Db {

	public void create(String serviceProviderName, String serviceProviderType, String serviceProviderLocation, String serviceProviderWebsite) throws SQLException {
		// TODO: spinfo_insert
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("spinfo_insert"));
		stmt.setString(1, serviceProviderName);
		stmt.setString(2, serviceProviderType);
		stmt.setString(3, serviceProviderLocation);
		stmt.setString(3, serviceProviderWebsite);
		stmt.executeUpdate();
		stmt.close();
	}

	public int getServiceProviderId(String serviceProviderName) throws SQLException {
		// TODO: spinfo_get_id
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("spinfo_get_id"));
		stmt.setString(1, serviceProviderName);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int autoId = rs.getInt(1);
		stmt.close();
		return autoId;
	}

	public void delete(String serviceProviderName) throws SQLException {
		// TODO: spinfo_delete
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("spinfo_delete"));
		stmt.setString(1, serviceProviderName);
		stmt.executeUpdate();
		stmt.close();
	}

	public void updateWebsite(String serviceProviderName, String website) throws SQLException {
		// TODO: spinfo_update_website
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("spinfo_update_website"));
		stmt.setString(1, serviceProviderName);
		stmt.setString(2, website);
		stmt.executeUpdate();
		stmt.close();
	}

	public void updateLocation(String serviceProviderName, String location) throws SQLException {
		// TODO: spinfo_update_location
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("spinfo_update_location"));
		stmt.setString(1, serviceProviderName);
		stmt.setString(2, location);
		stmt.executeUpdate();
		stmt.close();
	}
}
