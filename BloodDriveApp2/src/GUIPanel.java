import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GUIPanel extends JPanel {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public JTable dtable;
	public JScrollPane dpane;
	
	public JTable etable;
	public JScrollPane epane;
	
	public JButton deleteButton;
	
	public String username;
	
	public GUIPanel(String user) {
		username = user;
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void CreateAppointmentSearcher(AppointmentRetrivalService ar) {
		
		if(dpane != null) {
			removeAll();
			dtable = null;
			dpane = null;
			etable = null;
			epane = null;
		}
				
		String[] columnNames = {"Appointment Date", "Appointment Time", "Street Line 1", "Street Line 2", "City", "State", "Zip Code"};
		
		Object[][] ddata = ar.getAppointments(username, true);
		
		Object[][] edata = ar.getAppointments(username, false);
		
		dtable = new JTable(ddata, columnNames);
		dpane = new JScrollPane(dtable);
		dtable.setFillsViewportHeight(true);
		dpane.setPreferredSize(new Dimension(800, 400));
		
		etable = new JTable(edata, columnNames);
		epane = new JScrollPane(etable);
		etable.setFillsViewportHeight(true);
		epane.setPreferredSize(new Dimension(800, 400));
		
		add(new JLabel("Upcoming Donor Appointments"));
		add(dpane);
		add(new JLabel("Upcoming Employee Appointments"));
		add(epane);
		
		if(deleteButton != null) {
			add(deleteButton);
		}
		
		updateUI();
		
	}

	public void CreateAppointmentRemover(AppointmentCancelationService ac, AppointmentRetrivalService ar) {
		deleteButton = new JButton("Cancel Scheduled Appointmets");
		
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int[] dcurrentRows = dtable.getSelectedRows();
				int[] ecurrentRows = etable.getSelectedRows();
				
				if(dcurrentRows.length == 0 && ecurrentRows.length == 0) {
					 JOptionPane.showMessageDialog(null, "Please select at least one appointment.");
					 return;
				}
				
				for(int row : dcurrentRows) {
					String date = (String) dtable.getModel().getValueAt(row, 0);
					String time = (String) dtable.getModel().getValueAt(row, 1);
					
					ac.removeAppointment(username, date, time);
				}
				
				for(int row : ecurrentRows) {
					String date = (String) etable.getModel().getValueAt(row, 0);
					String time = (String) etable.getModel().getValueAt(row, 1);
					
					ac.removeAppointment(username, date, time);
				}
				
				CreateAppointmentSearcher(ar);
			}
			
		});
		
		add(deleteButton);
		
	}
	
}
