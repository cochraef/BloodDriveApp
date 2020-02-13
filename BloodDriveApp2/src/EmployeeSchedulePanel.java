import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class EmployeeSchedulePanel extends JPanel {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public JTable table;
	public JScrollPane pane;
	
	public JButton button;
	public JComboBox employees;
	
	public EmployeeSchedulePanel() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void CreateEmployeeFinder(AppointmentRetrivalService ar, EmployeeSchedulerService es) {
		String[] usernames = ar.getEmployeeUsernames();
		employees = new JComboBox(usernames);
		
		if(pane != null) {
			removeAll();
		    table = null;
			pane = null;
		}
				
		String[] columnNames = {"Donor Username", "Appointment Date", "Appointment Time", "Street Line 1", "Street Line 2", "City", "State", "Zip Code"};
		
		Object[][] data = es.getDonorsWithNoEmployeeAssigned();
		
		table = new JTable(data, columnNames);
		pane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		pane.setPreferredSize(new Dimension(600, 600));
		
		add(pane, BorderLayout.NORTH);
		
		add(employees, BorderLayout.SOUTH);
		if(button != null) {
			add(button, BorderLayout.SOUTH);
		}
		
			
		updateUI();
		

	}

	public void CreateEmployeeScheduler(AppointmentRetrivalService ar, EmployeeSchedulerService es) {
		button = new JButton("Assign Employee to Appointment");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int[] currentRows = table.getSelectedRows();
				
				if(currentRows.length == 0) {
					 JOptionPane.showMessageDialog(null, "Please select at least one appointment.");
					 return;
				}
				
				String eusername = (String) employees.getSelectedItem();
				
				for(int row : currentRows) {
					
					String dusername = (String) table.getModel().getValueAt(row, 0);
					String date = (String) table.getModel().getValueAt(row, 1);
					String time = (String) table.getModel().getValueAt(row, 2);
					
					es.scheduleAppointment(eusername, dusername, date, time);
				}
				
				CreateEmployeeFinder(ar, es);
			}
			
		});
		
		add(button, BorderLayout.SOUTH);
	}
	
	
	
	
}
