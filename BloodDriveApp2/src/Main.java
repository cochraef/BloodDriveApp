
public class Main {
	public static void main(String[] args) {
		DatabaseConnectionService db = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "BloodDriveDatabase");
		System.out.println(db.connect("SodaBaseUsercochraef20", "Password123"));
		new GUIFrame();
	}
}
