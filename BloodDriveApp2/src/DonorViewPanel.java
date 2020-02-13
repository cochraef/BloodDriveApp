import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class DonorViewPanel extends JPanel {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public JTable table;
	public JScrollPane pane;
	
	public JButton button;
	public JComboBox donors;

	public DonorViewPanel() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void CreateDonorViewer(AppointmentRetrivalService ar) {
		
		String[] usernames = ar.getDonorUsernames();
		donors = new JComboBox(usernames);
		button = new JButton("Find Appointments");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pane != null) {
					removeAll();
					table = null;
					pane = null;
				}
				
				String[] columnNames = {"Appointment Date", "Appointment Time", "Street Line 1", "Street Line 2", "City", "State", "Zip Code"};
				String current = (String) donors.getSelectedItem();
				Object[][] data = ar.getAppointments(current, true);
				
				table = new JTable(data, columnNames);
				pane = new JScrollPane(table);
				table.setFillsViewportHeight(true);
				pane.setPreferredSize(new Dimension(800, 400));
				
				if(button != null) {
					add(donors);
					add(button);
				}
				
				add(pane, BorderLayout.SOUTH);
				updateUI();
			}
			
		});
		
		add(donors);
		add(button);
		
	}

}
