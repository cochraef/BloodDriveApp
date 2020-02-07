import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriveRetrivalService {
	
	private DatabaseConnectionService dbService;

	public DriveRetrivalService(DatabaseConnectionService db) {
		this.dbService = db;
	}

	public Object[][] getDrives() {
		try {
			String query = "SELECT StreetLine1, StreetLine2, City, State, ZipCode, EventDate, StartTime, EndTime " + 
						   "FROM BloodDriveEvent JOIN Location l ON LocationID = l.ID";
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
			int st1index = rs.findColumn("StreetLine1");
			int st2index = rs.findColumn("StreetLine2");
			int cityindex = rs.findColumn("City");
			int stindex = rs.findColumn("State");
			int zcindex = rs.findColumn("ZipCode");
			int edindex = rs.findColumn("EventDate");
			int t1index = rs.findColumn("StartTime");
			int t2index = rs.findColumn("EndTime");
			
			ArrayList<Object[]> jank = new ArrayList<Object[]>();
			while(rs.next()) {
				Object[] row = new Object[8];
				row[0] = rs.getString(st1index);
				row[1] = rs.getString(st2index);
				row[2] = rs.getString(cityindex);
				row[3] = rs.getString(stindex);
				row[4] = rs.getString(zcindex);
				row[5] = rs.getString(edindex);
				row[6] = rs.getString(t1index).substring(0, 8);
				row[7] = rs.getString(t2index).substring(0, 8);
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

	public ArrayList<Integer> getIds() {
		try {
			String query = "SELECT l.ID " + 
						   "FROM BloodDriveEvent JOIN Location l ON LocationID = l.ID";
			PreparedStatement stmt = dbService.getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			int index = rs.findColumn("ID");
			
			ArrayList<Integer> result = new ArrayList<Integer>();
			while(rs.next()) {
				result.add(rs.getInt(index));
			}
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
