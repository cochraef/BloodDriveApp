import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SchedulePanel extends JPanel {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public JTable table;
	public JScrollPane pane;
	
	public String username;
	public ArrayList<Integer> locationIds;
	
	public SchedulePanel(String user) {
		username = user;
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}

	
	public void CreateDriveViewer(DriveRetrivalService dr) {
		String[] columnNames = {"Street Line 1", "Street Line 2", "City", "State", "Zip Code", "Event Date", "Start Time", "End Time"};
		
		Object[][] data = dr.getDrives();
		
		locationIds = dr.getIds();
		System.out.println(locationIds);
		
		table = new JTable(data, columnNames);
		pane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		pane.setPreferredSize(new Dimension(800, 400));
		
		add(new JLabel("Upcoming Blood Drive Events"));
		add(pane);
	}
	
	
	
	public void CreateAppointmentScheduler(AppointmentSchedulerService as, ViewPanel panel1, AppointmentRetrivalService ar) {
		
		JButton scheduleButton = new JButton("Schedule Appointment");
		
		JTextField timeField = new JTextField();
		timeField.setPreferredSize(new Dimension(200, 25));
		
		
		scheduleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int[] currentRows = table.getSelectedRows();
				
				if(currentRows.length != 1) {
					 JOptionPane.showMessageDialog(null, "Please select exactly one drive");
					 return;
				}
				
				String time = timeField.getText();
				String date = (String) table.getModel().getValueAt(currentRows[0], 5);
				Integer locationID = locationIds.get(currentRows[0]);
				
				as.addAppointment(username, time, date, locationID);
				
				panel1.CreateAppointmentViewer(ar);
				
			}
			
		});
		
		add(new JLabel("Type a time"));
		add(timeField);
		add(scheduleButton);
		
		
	}
		

		
		
}



