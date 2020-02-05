
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UserService {
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		CallableStatement cs = null;
		try {
			cs = this.dbService.getConnection().prepareCall("SELECT perpassword FROM [Person] WHERE ( username = ?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			cs.setString(1, username);
			ResultSet rs = cs.executeQuery();
			rs.next();
			if(password.compareTo(rs.getString("perpassword")) == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
