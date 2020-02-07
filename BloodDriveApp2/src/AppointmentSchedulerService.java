import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class AppointmentSchedulerService {
	private DatabaseConnectionService dbService = null;

	public AppointmentSchedulerService(DatabaseConnectionService db) {
		this.dbService = db;
	}

	public boolean addAppointment(String username, String time, String date, Integer locationID) {
		try {
			String query = "{? = call scheduleAppointment([" + username + "], [" + date + "], [" + time + "], [" + locationID + "])}";
			CallableStatement cs = dbService.getConnection().prepareCall(query);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int errorCode = cs.getInt(1);
			
			if(errorCode == 1) {
				JOptionPane.showMessageDialog(null, "Could not add entry.");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return true;
	}

}
