import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class RegInfoFrame extends JFrame {
	
	private UserService us;
	public String username;
	
	public RegInfoFrame(DatabaseConnectionService db, String username) {
		this.username = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(600, 400);
		setMinimumSize(new Dimension(450, 300));
		setResizable(false);
		setTitle("User Registration");
		us = new UserService(db);
		
		addFrames(db, username);
		
		setVisible(true);
	}

	private void addFrames(DatabaseConnectionService db, String username) {
		JPanel panel = new JPanel(new GridLayout(0, 2,10,10));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//First Name
		JLabel fNameLabel = new JLabel("First Name");
		JTextField fNameText = new JTextField();
		fNameText.setPreferredSize(new Dimension(250, 20));
		panel.add(fNameLabel);
		panel.add(fNameText);
		
		//Last Name
		JLabel lNameLabel = new JLabel("Last Name");
		JTextField lNameText = new JTextField();
		lNameText.setPreferredSize(new Dimension(250, 20));
		panel.add(lNameLabel);
		panel.add(lNameText);
		
		//Birthdate
		JLabel birthdateLabel = new JLabel("Birthdate (i.e. YYYY-MM-DD)");
		JTextField birthdateText = new JTextField();
		birthdateText.setPreferredSize(new Dimension(250, 20));
		panel.add(birthdateLabel);
		panel.add(birthdateText);
		
		//Address
		JLabel addressLabel = new JLabel("Address");
		JTextField addressText = new JTextField();
		addressText.setPreferredSize(new Dimension(250, 20));
		panel.add(addressLabel);
		panel.add(addressText);
		
		//Phone Number
		JLabel phoneLabel = new JLabel("Phone Number (i.e. (123) 555 4567)");		
		JTextField phoneText = new JTextField();
		addressText.setPreferredSize(new Dimension(250, 20));
		panel.add(phoneLabel);
		panel.add(phoneText);
		
		add(panel);

		//Register Button
		JPanel buttonpanel = new JPanel();
		buttonpanel.setPreferredSize(new Dimension(200, 40));
		JButton registerButton = new JButton("Register");
		
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
					
				CallableStatement cs;
				try {
					cs = db.getConnection().prepareCall("{ ? = call addNewPerson([" + username + "], [" + fNameText.getText() + "], [" + lNameText.getText() + "], [" + birthdateText.getText() + "], [" + addressText.getText() + "], [" + phoneText.getText() + "])}");
					cs.setString(1, username);
					cs.registerOutParameter(1, Types.INTEGER);
					cs.execute();
					int errorCode = cs.getInt(1);
					
					if(errorCode == 1) {
						JOptionPane.showMessageDialog(null, "THIS IS BAD");
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Please ensure all fields are non-empty and phone numbers and birthdays are valid.");
					return;
				}
				
				new GUIFrame(db, username, false, false);
				setVisible(false);

			}
			
		});
		
		registerButton.setPreferredSize(new Dimension(100, 30));
		
		buttonpanel.add(registerButton);
		
		add(buttonpanel, BorderLayout.SOUTH);
		
	}
}
