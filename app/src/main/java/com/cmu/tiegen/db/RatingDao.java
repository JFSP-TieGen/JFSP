package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingDao {
	public static class CreateRating extends Db {
		public void create(int userId, int serviceProviderId, int bookingId, float rating, String review) throws SQLException {
			// TODO: rating_insert
			PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("rating_insert"));
			stmt.setInt(1, userId);
			stmt.setInt(2, serviceProviderId);
			stmt.setInt(3, bookingId);
			stmt.setFloat(4, rating);
			stmt.setString(5, review);
			stmt.executeUpdate();
			stmt.close();
		}
		
	}

	public static class DeleteRating extends Db {
		public void delete(int ratingId) throws SQLException {
			// TODO: rating_delete
			PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("rating_delete"));
			stmt.setInt(1, ratingId);
			stmt.executeUpdate();
			stmt.close();
		}
	}

	public static class UpdateRating extends Db {
		public void updateRating(int ratingId, float rating, String review) throws SQLException {
			// TODO: rating_update
			PreparedStatement stmt = this.connection.prepareStatement(dbProps.getProperty("rating_update"));
			stmt.setInt(1, ratingId);
			stmt.setFloat(2, rating);
			stmt.setString(3, review);
			stmt.executeUpdate();
			stmt.close();
		}
	}
}
