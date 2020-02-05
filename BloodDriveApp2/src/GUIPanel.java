import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUIPanel extends JPanel {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public JTable table;
	public JScrollPane pane;
	
	public GUIPanel() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void CreateAppointmentSearcher(AppointmentRetrivalService ar, String username) {
		
		JButton searchButton = new JButton("Search for appointments");		
		JCheckBox donorBox = new JCheckBox("Donor?");
		
		searchButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(pane != null) {
						remove(pane);
					}
					
					String[] columnNames = {"Appointment Date", "Appointment Time", "Street Line 1", "Street Line 2", "City", "State", "Zip Code"};
					
					Object[][] data = ar.getAppointments(username, donorBox.isSelected());
					
					System.out.println(username);
					
					table = new JTable(data, columnNames);
					
					pane = new JScrollPane(table);
					table.setFillsViewportHeight(true);
					pane.setPreferredSize(new Dimension(800, 800));
					
					add(pane);
					
					updateUI();
				}
				
			});
				
		JButton deleteButton = new JButton("Cancel Scheduled Appointment");
		
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int currentRow = table.getSelectedRow();
				
				if(currentRow == -1) {
					 JOptionPane.showMessageDialog(null, "Please select an appointment.");
					 return;
				}
				
				String date = (String) table.getModel().getValueAt(currentRow, 0);
				String time = (String) table.getModel().getValueAt(currentRow, 1);
				
				System.out.println(date);
				System.out.println(time);
			}
			
		});
		
		add(searchButton);
		add(donorBox);
		add(deleteButton);
		
		searchButton.doClick();
	}
	
}
