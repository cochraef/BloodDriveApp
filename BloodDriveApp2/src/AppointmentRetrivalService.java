import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentRetrivalService {
	
	private DatabaseConnectionService dbService = null;
	
	public AppointmentRetrivalService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public Object[][] getAppointments(String username, Boolean isDonor) {
		
		try {
			String query = "SELECT * FROM GetPersonAppointments(?, ?)";
			PreparedStatement stmt = dbService.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			stmt.setBoolean(2, isDonor);
			ResultSet rs = stmt.executeQuery();
			return parseResults(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private Object[][] parseResults(ResultSet rs) {
		
		try {
			int adindex = rs.findColumn("AppointmentDate");
			int atindex = rs.findColumn("AppointmentTime");
			int st1index = rs.findColumn("StreetLine1");
			int st2index = rs.findColumn("StreetLine2");
			int cityindex = rs.findColumn("City");
			int stindex = rs.findColumn("State");
			int zcindex = rs.findColumn("ZipCode");
			
			ArrayList<Object[]> jank = new ArrayList<Object[]>();
			while(rs.next()) {
				Object[] row = new Object[7];
				row[0] = rs.getString(adindex);
				row[1] = rs.getString(atindex).substring(0, 8);
				row[2] = rs.getString(st1index);
				row[3] = rs.getString(st2index);
				row[4] = rs.getString(cityindex);
				row[5] = rs.getString(stindex);
				row[6] = rs.getString(zcindex);
				jank.add(row);
			}
			
			Object[][] doublejank = new Object[jank.size()][7];
			for(int i = 0; i < jank.size(); i++) {
				doublejank[i] = jank.get(i);
			}
			
			return doublejank;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String[] getDonorUsernames() {
		try {
			String query = "SELECT Username FROM Donor";
			PreparedStatement stmt;
			stmt = dbService.getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			ArrayList<String> usernames = new ArrayList<String>();
			
			int index = rs.findColumn("Username");
			
			while(rs.next()) {
				usernames.add(rs.getString(index));
			}
			
			String[] usernames2 = new String[usernames.size()];
			for(int i = 0; i < usernames.size(); i++) {
				usernames2[i] = usernames.get(i);
			}
			
			return usernames2;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public String[] getEmployeeUsernames() {
		try {
			String query = "SELECT Username FROM Employee";
			PreparedStatement stmt;
			stmt = dbService.getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			ArrayList<String> usernames = new ArrayList<String>();
			
			int index = rs.findColumn("Username");
			
			while(rs.next()) {
				usernames.add(rs.getString(index));
			}
			
			String[] usernames2 = new String[usernames.size()];
			for(int i = 0; i < usernames.size(); i++) {
				usernames2[i] = usernames.get(i);
			}
			
			return usernames2;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
