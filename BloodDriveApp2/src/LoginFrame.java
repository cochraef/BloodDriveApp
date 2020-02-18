import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	
	private UserService us;
	private AccessService as;
	
	public LoginFrame(DatabaseConnectionService db) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(600, 400);
		setMinimumSize(new Dimension(420, 150));
		setResizable(false);
		
		us = new UserService(db);
		as = new AccessService(db);
		
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
		JPanel buttonpanel = new JPanel();
		
		username.setPreferredSize(new Dimension(250, 20));
		password.setPreferredSize(new Dimension(250, 20));
		
		usernamepanel.setPreferredSize(new Dimension(200, 40));
		passwordpanel.setPreferredSize(new Dimension(200, 40));
		buttonpanel.setPreferredSize(new Dimension(200, 40));
		
		usernamepanel.add(lusername);
		usernamepanel.add(username);
		passwordpanel.add(lpassword);
		passwordpanel.add(password);

		
		add(usernamepanel, BorderLayout.NORTH);
		add(passwordpanel, BorderLayout.CENTER);
		
		JButton loginButton = new JButton("Login");
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean ea = as.getEmployeeAccess(username.getText());
				boolean ma = as.getManagerAccess(username.getText());
				
				if(ma) ea = ma;
				
				if(us.login(username.getText(), password.getText())) {
					new GUIFrame(db, username.getText(), ea, ma);
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password.");
				}
			}
			
		});
		
		loginButton.setPreferredSize(new Dimension(100, 30));
		
		JButton registerButton = new JButton("Register");
		
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean ea = as.getEmployeeAccess(username.getText());
				boolean ma = as.getManagerAccess(username.getText());
				
				if(us.register(username.getText(), password.getText())) {
					new RegInfoFrame(db, username.getText());
					setVisible(false);
				} else {
					return;
				}
			}
			
		});
		
		registerButton.setPreferredSize(new Dimension(100, 30));
		
		buttonpanel.add(loginButton);
		buttonpanel.add(registerButton);
		
		add(buttonpanel, BorderLayout.SOUTH);
		
	}
}
