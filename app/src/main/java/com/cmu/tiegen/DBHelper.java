import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//help manage the database connections

public class DBHelper {
	private static ThreadLocal<Connection> localThread = new ThreadLocal<Connection>();
	private static ComboPooledDataSource cpds = null;
	

	static {
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/autoDB" );
			cpds.setUser( "autoAdmin" );
	        cpds.setPassword( "1234" );
	        cpds.setInitialPoolSize(5);
	        cpds.setMaxPoolSize(20);
			
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//get connections from pool;
	public static Connection getConnection() {
		Connection conn = localThread.get();
		if (conn == null) {
			try {
				conn = cpds.getConnection();
				localThread.set(conn);
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return conn;
	}

	
	public static void trasactionRollback() {
		Connection conn = localThread.get();
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	

	
	public static void trasactionCommit() {
		Connection conn = localThread.get();
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	public static void releaseConn() {
		Connection conn = localThread.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				localThread.remove();
			}
		}

	}
}
