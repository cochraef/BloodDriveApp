import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GUIFrame extends JFrame {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	
	public JTable table;
	public JTextField textbox;
	
	public GUIFrame(DatabaseConnectionService db) {
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(db.getConnection() == null) {
			 JOptionPane.showMessageDialog(null, "Failed to connect to the database!");
		}
		
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
		panel.setMinimumSize(new Dimension(WIDTH/2, HEIGHT/2));
		
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
		
		Object[][] data = {
			    {"Kathy", "Smith",
			     "Snowboarding", new Integer(5), new Boolean(false)},
			    {"John", "Doe",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Sue", "Black",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Jane", "White",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Joe", "Brown",
			     "Pool", new Integer(10), new Boolean(false)}
			};
		
		table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		panel.add(scrollPane);
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
