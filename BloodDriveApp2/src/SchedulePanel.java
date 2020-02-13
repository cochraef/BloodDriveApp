import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
		
		table = new JTable(data, columnNames);
		pane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		pane.setPreferredSize(new Dimension(800, 400));
		
		add(new JLabel("Upcoming Blood Drive Events"));
		add(pane);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void CreateAppointmentScheduler(AppointmentSchedulerService as, ViewPanel panel1, AppointmentRetrivalService ar) {
		
		JButton scheduleButton = new JButton("Schedule Appointment");
		
		String[] times = { 
				"1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM", "5:00 AM",
				"7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM",
				"12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM",
				"7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM", "11:00 PM", "12:00 AM"
		};
		
		JComboBox timeField = new JComboBox(times);
		
		timeField.setPreferredSize(new Dimension(200, 25));
		
		
		scheduleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int[] currentRows = table.getSelectedRows();
				
				if(currentRows.length != 1) {
					 JOptionPane.showMessageDialog(null, "Please select exactly one drive");
					 return;
				}
				
				String time = (String) timeField.getSelectedItem();
				String date = (String) table.getModel().getValueAt(currentRows[0], 5);
				String startTime = (String) table.getModel().getValueAt(currentRows[0], 6);
				String endTime = (String) table.getModel().getValueAt(currentRows[0], 7);
				Integer locationID = locationIds.get(currentRows[0]);
				
				if(date == null | date.equals("")) {
					JOptionPane.showMessageDialog(null, "Please specify a time.");
				}
				
				as.addAppointment(username, time, date, locationID, startTime, endTime);
				
				panel1.CreateAppointmentViewer(ar);
				
			}
			
		});
		
		add(new JLabel("Select a time"));
		add(timeField);
		add(scheduleButton);
		
		
	}
		

		
		
}



