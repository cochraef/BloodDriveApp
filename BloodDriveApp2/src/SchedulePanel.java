import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SchedulePanel extends JPanel {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public JTable table;
	public JScrollPane pane;
	
	public String username;
	
	public SchedulePanel(String user) {
		username = user;
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}

	
	public void CreateDriveViewer(DriveRetrivalService dr) {
		String[] columnNames = {"Street Line 1", "Street Line 2", "City", "State", "Zip Code", "Event Date", "Start Time", "End Time"};
		
		Object[][] data = dr.getDrives();
		
		table = new JTable(data, columnNames);
		pane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		pane.setPreferredSize(new Dimension(800, 400));
		
		add(new JLabel("Upcoming Blood Drive Events"));
		add(pane);
	}
	
	
	
	public void CreateAppointmentScheduler() {
		
	}


}
