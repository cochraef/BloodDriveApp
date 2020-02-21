import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;
	
	private String serverName;
	private String databaseName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;;
	}

	public boolean connect(String user, String pass) {
		String connectionUrl = SampleURL.replaceFirst("\\$\\{[a-zA-Z]*\\}", serverName);
		connectionUrl = connectionUrl.replaceFirst("\\$\\{[a-zA-Z]*\\}", databaseName);
		connectionUrl = connectionUrl.replaceFirst("\\$\\{[a-zA-Z]*\\}", user);
		connectionUrl = connectionUrl.replaceFirst("\\$\\{[a-zA-Z]*\\}", pass);
		
		try {
			connection = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			//return false;
		}
		
		return true;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
