import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class EmployeeSchedulerService {
	private DatabaseConnectionService dbService = null;

	public EmployeeSchedulerService(DatabaseConnectionService db) {
		this.dbService  = db;
	}
	
	public Object[][] getDonorsWithNoEmployeeAssigned() {
		try {
			String query = "SELECT * FROM GetUnmatchedDonorAppointments()";
			PreparedStatement stmt = dbService.getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			return parseResults(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private Object[][] parseResults(ResultSet rs) {
		try {
			int uindex = rs.findColumn("Username");
			int adindex = rs.findColumn("AppointmentDate");
			int atindex = rs.findColumn("AppointmentTime");
			int st1index = rs.findColumn("StreetLine1");
			int st2index = rs.findColumn("StreetLine2");
			int cityindex = rs.findColumn("City");
			int stindex = rs.findColumn("State");
			int zcindex = rs.findColumn("ZipCode");
			
			ArrayList<Object[]> jank = new ArrayList<Object[]>();
			while(rs.next()) {
				Object[] row = new Object[8];
				row[0] = rs.getString(uindex);
				row[1] = rs.getString(adindex);
				row[2] = rs.getString(atindex).substring(0, 8);
				row[3] = rs.getString(st1index);
				row[4] = rs.getString(st2index);
				row[5] = rs.getString(cityindex);
				row[6] = rs.getString(stindex);
				row[7] = rs.getString(zcindex);
				jank.add(row);
			}
			
			Object[][] doublejank = new Object[jank.size()][8];
			for(int i = 0; i < jank.size(); i++) {
				doublejank[i] = jank.get(i);
			}
			
			return doublejank;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean scheduleAppointment(String eusername, String dusername, String date, String time) {
		try {
			String query0 = "SELECT a.LocationID, StartTime, EndTime " + 
					"FROM BloodDriveEvent b " + 
					"JOIN Appointment a ON a.LocationID = b.LocationID " + 
					"JOIN GoesTo ON AppointmentID = a.ID " + 
					"WHERE PersonUsername = ? AND ApptTime = ? AND ApptDate = ?";
			PreparedStatement stmt = dbService.getConnection().prepareStatement(query0);
			stmt.setString(1, dusername);
			stmt.setString(2, time);
			stmt.setString(3, date);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) {
				JOptionPane.showMessageDialog(null, ":(");
				return false;
			}
			
			String locationID = rs.getString("LocationID");
			String startTime = rs.getString("StartTime");
			String endTime = rs.getString("EndTime");
			
			String query = "{? = call scheduleAppointment([" + eusername + "], [" + date + "], [" + time + "], [" + locationID + "], [" + startTime + "], [" + endTime + "], 0)}";
			CallableStatement cs = dbService.getConnection().prepareCall(query);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int errorCode = cs.getInt(1);
			
			if(errorCode == 1) {
				JOptionPane.showMessageDialog(null, "Employee already has an appointment scheduled for that time");
				return false;
			}
			
			if(errorCode == 2) {
				JOptionPane.showMessageDialog(null, "Drive is not scheduled for this time"); // Should be impossible in this case
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return true;
	}

}
