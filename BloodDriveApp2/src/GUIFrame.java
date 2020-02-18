import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUIFrame extends JFrame {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	
	public JTabbedPane tabbedpane;
	public ViewPanel panel1;
	public SchedulePanel panel2;
	public EmployeeSchedulePanel panel3;
	public DonorViewPanel panel4;
		
	public DatabaseConnectionService db;
	
	public AppointmentRetrivalService ar;
	public AppointmentCancelationService ac;
	
	public DriveRetrivalService dr;
	public AppointmentSchedulerService as;
	
	public EmployeeSchedulerService es;
	
	public String username;
	
	private boolean hasEmployeeAccess;
	private boolean hasManagerAccess;
	
	public GUIFrame(DatabaseConnectionService db, String user, boolean hasEmployeeAccess, boolean hasManagerAccess) {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Blood Drive Database");
		
		if(db.getConnection() == null) {
			 JOptionPane.showMessageDialog(this, "Failed to connect to the database!");
			 return;
		}
		
		this.db = db;
		
		this.ar = new AppointmentRetrivalService(db);
		this.ac = new AppointmentCancelationService(db);
		
		this.dr = new DriveRetrivalService(db);
		this.as = new AppointmentSchedulerService(db);
		
		this.es = new EmployeeSchedulerService(db);
		
		username = user;
		
		this.hasEmployeeAccess = hasEmployeeAccess;
		this.hasManagerAccess = hasManagerAccess;
		
		panel1 = new ViewPanel(username, hasEmployeeAccess);
		panel2 = new SchedulePanel(username);
		panel3 = new EmployeeSchedulePanel();
		panel4 = new DonorViewPanel();
		
		panel1.CreateAppointmentViewer(ar);
		panel1.CreateAppointmentRemover(ac, ar);
		
		panel2.CreateDriveViewer(dr);
		panel2.CreateAppointmentScheduler(as, panel1, ar);
		
		panel3.CreateEmployeeFinder(ar, es);
		panel3.CreateEmployeeScheduler(ar, es);
		
		panel4.CreateDonorViewer(ar);
		
		CreateJTabFrame(panel1, panel2, panel3, panel4);
		setVisible(true);
	}
	
	private void CreateJTabFrame(JPanel panel1, JPanel panel2, JPanel panel3, JPanel panel4) {
		tabbedpane = new JTabbedPane();
		tabbedpane.addTab("View and Cancel Appointments", panel1);
		tabbedpane.addTab("Schedule Appointments", panel2);
		if(hasManagerAccess) {
			tabbedpane.addTab("Schedule Employees", panel3);
		}
		if(hasEmployeeAccess) {
			tabbedpane.addTab("View Donor Appointments", panel4);
		}
		add(tabbedpane);
	}
	


}
