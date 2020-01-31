import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUIPanel extends JPanel {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public JScrollPane pane;
	
	public GUIPanel() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void CreateAppointmentSearcher(AppointmentRetrivalService ar) {
		JTextField usernameInput = new JTextField();
		usernameInput.setPreferredSize(new Dimension(250, 20));
		
		JButton searchButton = new JButton("Search for appointments");
		
		JCheckBox donorBox = new JCheckBox("Donor?");
		
		searchButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(pane != null) {
						remove(pane);
						pane = null;
					}
					
					String[] columnNames = {"Appointment Date", "Appointment Time", "Street Line 1", "Street Line 2", "City", "State", "Zip Code"};
					
					Object[][] data = ar.getAppointments(usernameInput.getText(), donorBox.isSelected());
					
					JTable table = new JTable(data, columnNames);
					
					pane = new JScrollPane(table);
					table.setFillsViewportHeight(true);
					pane.setPreferredSize(new Dimension(800, 800));
					
					add(pane);
					
					updateUI();
				}
				
			});
		
		add(searchButton);
		add(donorBox);
		add(usernameInput);
	}
	
}
