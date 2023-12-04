package LoginSignup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import ProfileandDiscoverPages.*;
import Users.*;
import loggingInfosandErrors.Logger;

public class LoginFrame extends JFrame implements ActionListener{
	private JLabel titleLabel= new JLabel(); 
	private JPanel panel1 =new JPanel();
	private JPanel panel2= new JPanel();
	private JTextField nickname = new JTextField();
	private JTextField password= new JTextField();
	private JLabel signUpLabel= new JLabel();
	private JButton loginButton= new JButton();
	private JButton signupButton = new JButton();
	public LoginFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setTitle("PhotoCloud");
		this.setLayout(new BorderLayout());
		titleLabel.setText("Log In");
		titleLabel.setFont(new Font("Arial", Font.PLAIN,20));
		
		this.add(titleLabel, BorderLayout.NORTH);
		
		panel1.setLayout(new GridLayout(2,2,10,10));
		JLabel nicknameL= new JLabel("Nickname: ");
		nicknameL.setSize(new Dimension(100,20));
		nicknameL.setFont(new Font("Arial", Font.PLAIN,15));
		nickname.setPreferredSize(new Dimension(50,20));
		panel1.add(nicknameL);
		panel1.add(nickname);
		
		JLabel passwordL= new JLabel("Password: ");
		passwordL.setFont(new Font("Arial", Font.PLAIN,15));
		password.setPreferredSize(new Dimension(100,20));
		panel1.add(passwordL);
		panel1.add(password);
		panel1.setPreferredSize(new Dimension(500,400));
		this.add(panel1, BorderLayout.CENTER);
		
		signUpLabel.setText("If you haven't signed up yet -->");
		signUpLabel.setFont(new Font("Arial", Font.PLAIN,15));
		loginButton.setText("LOG IN");
		loginButton.setFont(new Font("Arial", Font.BOLD,15));
		signupButton.setText("SIGN UP");
		signupButton.setFont(new Font("Arial", Font.BOLD,15));
		loginButton.addActionListener(this);
		signupButton.addActionListener(this);
		panel2.setLayout(new BorderLayout());
		panel2.add(loginButton, BorderLayout.NORTH);
		panel2.add(signUpLabel,BorderLayout.SOUTH);
		panel2.add(signupButton,BorderLayout.AFTER_LINE_ENDS);
		this.add(panel1,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== loginButton) {
			// Will check for validity
			String nicknamegiven= nickname.getText();
			String passwordgiven = password.getText();
			// will check for empty spaces
			if(nicknamegiven.length()==0||passwordgiven.length()==0) {
			JOptionPane.showMessageDialog(null, "All required fields must be filled!", "Attention",JOptionPane.PLAIN_MESSAGE);
			Logger.logError("User didn't fill the required areas while loggin in");
	}
	// if both nickname and password area is filled, it will look for if there is a user with given nickname
	else {
	 Map<String,String> nicknamePasswords = SignupFrame.nicknamePasswordMap;
	boolean userFound= false;
						
     for(String nickname2: nicknamePasswords.keySet()) {
        if(nicknamegiven.equals(nickname2)) {
		userFound= true;
		String password2= nicknamePasswords.get(nickname2);
		// will check it the user with given nickname and it's password matches, if they match it will lead to the profile page
		if(passwordgiven.equals(password2)) {
		 User ourUser= SignupFrame.findUser(nicknamegiven);
		Logger.logInfo("User with nickname :"+ nicknamegiven+" succesfully logged into application!");
		this.dispose();
		new ProfilePageUser(ourUser);}
	// if the password is wrong it will give a warning to the user  and logs this action to the error file
	else {
		JOptionPane.showMessageDialog(null, "Password is wrong!", "Attention",JOptionPane.PLAIN_MESSAGE);
		Logger.logError("Password is written wrong by the user with nickname:"+nicknamegiven);
		} // if user is found, no need to keep looking for 
		break;}
	}
	// if user with given nickname doesn't exists at all, it gives a warning
	if(!userFound) {
	JOptionPane.showMessageDialog(null, "There is no such user with this nickname, please check!", "Attention",JOptionPane.PLAIN_MESSAGE);
	Logger.logError("There is no user with nickname"+nicknamegiven);
		}
		}
	}
		// If person doesn't have an account yet it will lead to the sign up frame
		if(e.getSource()== signupButton) {
			this.dispose();
			new SignupFrame();
		}
		
}
}
	
		
	

