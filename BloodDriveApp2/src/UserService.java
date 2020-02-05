
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class UserService {
	private DatabaseConnectionService dbService;

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

}
