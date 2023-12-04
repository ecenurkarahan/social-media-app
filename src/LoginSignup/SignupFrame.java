package LoginSignup;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.*;
import Users.*;
import loggingInfosandErrors.Logger;

import javax.swing.*;

import ProfileandDiscoverPages.ProfilePageUser;

public class SignupFrame extends JFrame implements ActionListener{
	private JButton signupButton = new JButton();
	private JButton loginButton = new JButton();
	private JLabel label1= new JLabel();
	private JPanel panel1= new JPanel();
	private JPanel panel2= new JPanel();
	private JTextArea nickname= new JTextArea();
	private JTextArea password= new JTextArea();
	private JTextArea name= new JTextArea();
	private JTextArea surname= new JTextArea();
	private JTextArea age= new JTextArea();
	private JTextArea email= new JTextArea();
	private JComboBox userType= new JComboBox() ;
	// usernicknameset keeps all the users' nicknames , since nicknames are unique i constructed a set. It is a public and static field
	// thus, these can be called from all packages
	public static Set<String> userNicknameSet= new HashSet<String>();
	// useremailset keeps all the emails of the users
	public static Set<String> userEmailSet= new HashSet<String>();
	// userlist keeps the user objects created in our programm. it will help in log in page
	public static ArrayList<User> userList = new ArrayList<User>();
	// nicknamepasswordmap keeps user's nicknames and passwords and while logging in, we check for true passwords with the help of this field
	public static Map<String,String> nicknamePasswordMap= new HashMap<>();
	public SignupFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setTitle("PhotoCloud");
		this.setLayout(new BorderLayout());
		panel1.setLayout(new GridLayout(7,2,10,10));
		panel1.setSize(400, 600);
		
		label1.setText("Sign Up and Join Us!");
		label1.setFont(new Font("Arial", Font.PLAIN,20));
		
		this.add(label1,BorderLayout.NORTH);
		
		JLabel nicknameL= new JLabel("Nickname: ");
		nicknameL.setSize(new Dimension(100,20));
		nickname.setPreferredSize(new Dimension(100,20));
		panel1.add(nicknameL);
		panel1.add(nickname);
		
		JLabel passwordL= new JLabel("Password: ");
		password.setPreferredSize(new Dimension(100,20));
		panel1.add(passwordL);
		panel1.add(password);
		
		JLabel nameL= new JLabel("Name: ");
		name.setPreferredSize(new Dimension(100,20));
		panel1.add(nameL);
		panel1.add(name);
		
		JLabel surnameL = new JLabel("Surname: ");
		surname.setPreferredSize(new Dimension(100,20));
		panel1.add(surnameL);
		panel1.add(surname);
		
		JLabel ageL = new JLabel("Age: ");
		age.setPreferredSize(new Dimension(100,20));
		panel1.add(ageL);
		panel1.add(age);
		
		JLabel emailL= new JLabel("Email: ");
		email.setPreferredSize(new Dimension(100,20));
		panel1.add(emailL);
		panel1.add(email);
		// I let users to choose their own tier while signing in
		JLabel preference = new JLabel("User tier preference: ");
		String[] tiers= { "Free Tier","HobbyList Tier", "Professional Tier"};
		userType =  new JComboBox(tiers);
		panel1.add(preference);
		panel1.add(userType);
		
		panel2.setLayout(new BorderLayout());
		JLabel label2= new JLabel("If you already have an account--->");
		loginButton.setText("LOG IN");
		loginButton.addActionListener(this);
		
		signupButton.setText("SIGN UP");
		signupButton.addActionListener(this);
		
		panel2.add(signupButton,BorderLayout.NORTH);
		panel2.add(label2, BorderLayout.SOUTH);
		panel2.add(loginButton,BorderLayout.AFTER_LINE_ENDS);
		this.add(panel1, BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
		this.setVisible(true);
		
	}
	// We let users edit their infos, this method is a public static method which checks for validity of the new given inputs
	public static boolean isInfoValidforEditing(String nickname1,String password1,String name1 ,String surname1 , String age1, String email1) {
		if(nickname1.length()==0||password1.length()==0||name1.length()==0||surname1.length()==0||age1.length()==0|| email1.length()==0) {
			JOptionPane.showMessageDialog(null, "All required fields must be filled!", "Attention",JOptionPane.PLAIN_MESSAGE);
			Logger.logError("User didn't fill the required areas while editing infos");
			return false;
		}
		if(password1.length()<8) {
			JOptionPane.showMessageDialog(null, "Password must contain at least 8 characters!", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("User didn't write a password long enough while editing infos");
			return false;
		}
		if(!email1.matches("\\w+[@]ku.edu.tr")) {
			JOptionPane.showMessageDialog(null, "Invalid email format! It should be in the form of <emailadress>@ku.edu.tr", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("User didn't write convenient email adress while editing infos");
			return false;
		}
		try{
			int age2 = Integer.parseInt(age1);	
		}
		catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Age must be an integer!", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("User write a non numeric age while editing infos");
			return false;
		}
		return true;
	}
	// This static method checks if the given inputs are appropriate, warns the user if not. If one info is inappropriate it returns false.
	public static boolean isInfoValid(String nickname1,String password1,String name1 ,String surname1 , String age1, String email1) {
		if(nickname1.length()==0||password1.length()==0||name1.length()==0||surname1.length()==0||age1.length()==0|| email1.length()==0) {
			JOptionPane.showMessageDialog(null, "All required fields must be filled!", "Attention",JOptionPane.PLAIN_MESSAGE);
			Logger.logError("User didn't fill the required areas while signing in");
			return false;
		}
		if(password1.length()<8) {
			JOptionPane.showMessageDialog(null, "Password must contain at least 8 characters!", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("User didn't write a password long enough while signing in");
			return false;
		}
		if(!email1.matches("\\w+[@]ku.edu.tr")) {
			JOptionPane.showMessageDialog(null, "Invalid email format! It should be in the form of <emailadress>@ku.edu.tr", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("User didn't write convenient email adress while signing in");
			return false;
		}
		if(userNicknameSet.contains(nickname1)) {
			JOptionPane.showMessageDialog(null, "This nickname is taken by someone before, please enter an unique nickname", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("Given nickname is taken by someone else before (sign in page)");
			return false;
		}
		if(userEmailSet.contains(email1)) {
			JOptionPane.showMessageDialog(null, "This email is used by someone before, please enter an unique email", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("Given email is taken by someone else before (sign in page)");
			return false;
		}
		try{
			int age2 = Integer.parseInt(age1);	
		}
		catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, "Age must be an integer!", "Attention",JOptionPane.ERROR_MESSAGE);
			Logger.logError("User write a non numeric age while signing in");
			return false;
		}
		return true;
	}
	// This method goes through the userlist and find the user with the given nickname, it not found it returns null
	public static User  findUser(String nickname) {
		for(User user: userList) {
			if(user.getNickName().equals(nickname)) {
				return user;
			}
		}
		return null;
	}
	// when a user object is created, this method adds the user to all the lists such that we can log out and log in the same users account repeatedly
	public static void addUserToLists(User user) {
		userNicknameSet.add(user.getNickName());
		userEmailSet.add(user.getEmail());
		nicknamePasswordMap.put(user.getNickName(), user.getPassword());
		userList.add(user);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// If user clicks on sign up button, we handle the event with this action listener
		// It checks if the given info is valid, if not it warns the user with right statements to fill the form again
		//If info is valid, a new user is created based on its tier and added to lists to be remembered again
		if(e.getSource()==signupButton) {
			String nickname1= nickname.getText();
			String password1 = password.getText();
			String name1 = name.getText();
			String surname1 = surname.getText();
			String age1 = age.getText();
			String email1 = email.getText();
			String userPreference= (String) userType.getSelectedItem();
			if(isInfoValid(nickname1, password1, name1, surname1, age1, email1)) {
				int age2 = Integer.parseInt(age1);
				if(userPreference.equals("Free Tier") ) {
					FreeTierUser newUser = new FreeTierUser(nickname1,password1,name1,surname1,email1,age2);
					addUserToLists(newUser);
					Logger.logInfo("Free tier user is created succesfully!");
					this.dispose();
					new ProfilePageUser(newUser);
				}
				else if(userPreference.equals("HobbyList Tier")) {
					HobbyListTier newUser = new HobbyListTier(nickname1,password1,name1,surname1,email1,age2);
					addUserToLists(newUser);
					Logger.logInfo("HobbyList tier user is created succesfully!");
					this.dispose();
					new ProfilePageUser(newUser);
				}
				else if (userPreference.equals("Professional Tier")) {
					ProfessionalTier newUser = new ProfessionalTier(nickname1,password1,name1,surname1,email1,age2);
					addUserToLists(newUser);
					Logger.logInfo("Professional tier user is created succesfully!");
					this.dispose();
					new ProfilePageUser(newUser);
				}
				
			}
		
			
		}
		//If user clicks to the log in button in the sign up page, sign up page is closed and user is redirected to the login page
		if(e.getSource()==loginButton) {
			Logger.logInfo("User is redirected to the login page from the sign up page!");
			this.dispose();
			new LoginFrame();
			
		}
		
	}
	

}
