import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class AppointmentCancelationService {

	private DatabaseConnectionService dbService = null;

	public AppointmentCancelationService(DatabaseConnectionService db) {
		this.dbService = db;
	}

	public boolean removeAppointment(String username, String date, String time) {
		try {
			String query = "{? = call CancelAppointment([" + username + "], [" + date + "], [" + time + "]) }";
			CallableStatement cs = dbService.getConnection().prepareCall(query);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int errorCode = cs.getInt(1);
			
			if(errorCode == 1) {
				JOptionPane.showMessageDialog(null, "Could not delete entry.");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return true;
	}

}
