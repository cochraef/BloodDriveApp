
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
			cs = this.dbService.getConnection().prepareCall("SELECT perpassword FROM [Person] WHERE (username = ?)");
			cs.setString(1, username);
			ResultSet rs = cs.executeQuery();
			rs.next();
			if(password.compareTo(rs.getString("perpassword")) == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if(e.getMessage().equals("The result set has no current row.")) {
				return false;
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register(String username, String password) {
		CallableStatement cs = null;
		if(username == null || username.equals("")) {
			JOptionPane.showMessageDialog(null, "Username cannot be null or empty.");
			return false;
		}
		try {
			cs = this.dbService.getConnection().prepareCall("{ ? = call Register([" + username + "], [" + password + "])}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
			if(returnValue == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Username already exists.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
