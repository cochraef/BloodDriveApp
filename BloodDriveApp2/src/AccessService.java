import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccessService {
	private DatabaseConnectionService dbService = null;

	public AccessService(DatabaseConnectionService db) {
		this.dbService = db;
	}

	public boolean getEmployeeAccess(String username) {
		try {
			String query = "SELECT dbo.getEmployeeAccess(?)";
			PreparedStatement s = dbService.getConnection().prepareStatement(query);
			s.setString(1, username);
			ResultSet rs = s.executeQuery();
			rs.next();
			return rs.getBoolean(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean getManagerAccess(String username) {
		try {
			String query = "SELECT dbo.getManagerAccess(?)";
			PreparedStatement s = dbService.getConnection().prepareStatement(query);
			s.setString(1, username);
			ResultSet rs = s.executeQuery();
			rs.next();
			return rs.getBoolean(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
