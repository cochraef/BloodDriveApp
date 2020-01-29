import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class GUIFrame extends JFrame {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	
	public GUIFrame() {
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
	}

}
