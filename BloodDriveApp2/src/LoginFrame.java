import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	
	private UserService us;
	
	public LoginFrame(DatabaseConnectionService db) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(600, 400);
		setMinimumSize(new Dimension(400, 150));
		
		us = new UserService(db);
		
		addFrames(db);
		
		setVisible(true);
	}

	private void addFrames(DatabaseConnectionService db) {
		JLabel lusername = new JLabel("Username");
		JLabel lpassword = new JLabel("Password");
		JTextField username = new JTextField();
		JTextField password = new JTextField();
		JPanel usernamepanel = new JPanel();
		JPanel passwordpanel = new JPanel();
		
		username.setPreferredSize(new Dimension(250, 20));
		password.setPreferredSize(new Dimension(250, 20));
		
		usernamepanel.setPreferredSize(new Dimension(200, 40));
		passwordpanel.setPreferredSize(new Dimension(200, 40));
		
		usernamepanel.add(username);
		usernamepanel.add(lusername, BorderLayout.WEST);
		passwordpanel.add(password);
		passwordpanel.add(lpassword, BorderLayout.WEST);
		
		add(usernamepanel, BorderLayout.NORTH);
		add(passwordpanel, BorderLayout.CENTER);
		
		JButton loginButton = new JButton("Login");
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(us.login(username.getText(), password.getText())) {
					new GUIFrame(db, username.getText());
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password.");
				}
			}
			
		});
		
		loginButton.setPreferredSize(new Dimension(50, 30));
		
		JButton registerButton = new JButton("Register");
		registerButton.setPreferredSize(new Dimension(50, 30));
		
		add(loginButton, BorderLayout.SOUTH);
		add(registerButton, BorderLayout.SOUTH);
		
	}
}
