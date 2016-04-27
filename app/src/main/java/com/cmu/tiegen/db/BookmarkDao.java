package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookmarkDao extends Db {

	public void create(int userId, int serviceProviderId) throws SQLException {
		// TODO: bookmark_insert
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("bookmark_insert"));
		stmt.setInt(1, userId);
		stmt.setInt(2, serviceProviderId);
		stmt.executeUpdate();
		stmt.close();
	}

	public void delete(int bookmarkId) throws SQLException {
		// TODO: bookmark_delete
		PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("bookmark_delete"));
		stmt.setInt(1, bookmarkId);
		stmt.executeUpdate();
		stmt.close();
	}

}
