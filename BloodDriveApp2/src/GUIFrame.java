import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
	
	public JScrollPane pane;
	public JTextField textbox;
	
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
		
		JPanel panel1 = new JPanel(), panel2 = new JPanel(), panel3 = new JPanel();
		
		CreateAppointmentSearcher(panel1);
		
		
		panel2.setMinimumSize(new Dimension(WIDTH/2, HEIGHT/2));
		panel3.setMinimumSize(new Dimension(WIDTH/2, HEIGHT/2));
		CreateJTabFrame(panel1, panel2, panel3);
		setVisible(true);
	}
	
	private void CreateJTabFrame(JPanel panel1, JPanel panel2, JPanel panel3) {
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Search For Appointments", panel1);
		pane.addTab("Schedule Appointments", panel2);
		pane.addTab("Delete Appointments", panel3);
		add(pane);
	}
	
	private void CreateAppointmentSearcher(JPanel panel) {
		panel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		JTextField usernameInput = new JTextField();
		usernameInput.setPreferredSize(new Dimension(250, 20));
		
		JButton searchButton = new JButton("Search for appointments");
		
		searchButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(pane != null) {
						panel.remove(pane);
						pane = null;
					}
					
					String[] columnNames = {"Appointment Date", "Appointment Time", "Street Line 1", "Street Line 2", "City", "State", "Zip Code"};
					
					Object[][] data = ar.getAppointments(usernameInput.getText());
					
					JTable table = new JTable(data, columnNames);
					
					pane = new JScrollPane(table);
					table.setFillsViewportHeight(true);
					pane.setPreferredSize(new Dimension(800, 800));
					
					panel.add(pane);
					
					panel.updateUI();
				}
				
			});
		
		panel.add(searchButton);
		panel.add(usernameInput);
		
		add(panel);
		
//		DefaultTableModel model = new DefaultTableModel();
////		model.addColumn(columnNames);
//		table = new JTable();
//		table.setModel(model); 
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		table.setFillsViewportHeight(true);
//		JScrollPane scroll = new JScrollPane(table);
//		scroll.setHorizontalScrollBarPolicy(
//		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		scroll.setVerticalScrollBarPolicy(
//		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
//		table.addColumn(columnNames);
//		panel.add(table);
//		add(panel);

	}

}
