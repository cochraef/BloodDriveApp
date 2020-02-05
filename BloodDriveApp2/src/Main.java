
public class Main {
	public static void main(String[] args) {
		DatabaseConnectionService db = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "BloodDriveDatabase");
		db.connect("SodaBaseUsercochraef20", "Password123");
		new LoginFrame(db);
	}
}
