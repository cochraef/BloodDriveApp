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
	public GUIPanel panel1;
	public GUIPanel panel2;
	public GUIPanel panel3;
		
	public DatabaseConnectionService db;
	public AppointmentRetrivalService ar;
	
	public GUIFrame(DatabaseConnectionService db) {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(db.getConnection() == null) {
			 JOptionPane.showMessageDialog(this, "Failed to connect to the database!");
			 return;
		}
		
		this.db = db;
		this.ar = new AppointmentRetrivalService(db);
		
		panel1 = new GUIPanel();
		panel2 = new GUIPanel();
		panel3 = new GUIPanel();
		
		panel1.CreateAppointmentSearcher(ar);
		CreateJTabFrame(panel1, panel2, panel3);
		
		setVisible(true);
	}
	
	private void CreateJTabFrame(JPanel panel1, JPanel panel2, JPanel panel3) {
		tabbedpane = new JTabbedPane();
		tabbedpane.addTab("Cancel Appointments", panel1);
		tabbedpane.addTab("Schedule Appointments", panel2);
		tabbedpane.addTab("Schedule Employees", panel3);
		add(tabbedpane);
	}
	


}
