package ProfileandDiscoverPages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import LoginSignup.SignupFrame;
import Users.User;
import loggingInfosandErrors.Logger;
// it does not change the users' password , when logging in it still wants the old password
public class EditProfile extends JFrame implements ActionListener {
	private User user;
	private String password;
	private String email;
	private String nickname;
	private String name;
	private String surname;
	private JTextArea nameChange= new JTextArea(name);
	private JTextArea surnameChange= new JTextArea(surname);
	private JTextArea ageChange= new JTextArea();
	private JTextArea passwordChange= new JTextArea(password);
	private int age;
	private JButton submit = new JButton("Submit");
	private JPanel neweditableInfosPanel= new JPanel();
	/**
	 * 
	 * @param user is the owner of the editable infos
	 */
	 public EditProfile(User user) {
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(500, 400);
			this.setTitle("Edit Infos");
			this.setLayout(null);
			this.user= user;
		    password = user.getPassword();
			JLabel passwordL1= new JLabel("Password:");
			JLabel passwordL2= new JLabel(password);
			
			 email = user.getEmail();
			JLabel emailL1= new JLabel("Email:");
			JLabel emailL2= new JLabel(email);
			
			nickname = user.getNickName();
			JLabel nicknameL1= new JLabel("Nickname:");
			JLabel nicknameL2= new JLabel(nickname);
			
			name = user.getRealName();
			JLabel nameL1= new JLabel("Name:");
			JLabel nameL2= new JLabel(name);
			
			surname = user.getSurname();
			JLabel surnameL1= new JLabel("Surname:");
			JLabel surnameL2= new JLabel(surname);
			
			int age = user.getAge();
			neweditableInfosPanel.setLayout(new GridLayout(7,2,10,10));
			JLabel ageL1= new JLabel("Age:");
			JLabel ageL2= new JLabel(Integer.toString(age));;
			neweditableInfosPanel.add(emailL1);
			neweditableInfosPanel.add(emailL2);
			neweditableInfosPanel.add(nicknameL1);
			neweditableInfosPanel.add(nicknameL2);
			nameChange= new JTextArea(name);
			surnameChange= new JTextArea(surname);
			ageChange= new JTextArea(ageL2.getText());
			passwordChange= new JTextArea(password);
			neweditableInfosPanel.add(passwordL1);
			neweditableInfosPanel.add(passwordChange);
			neweditableInfosPanel.add(nameL1);
			neweditableInfosPanel.add(nameChange);
			neweditableInfosPanel.add(surnameL1);
			neweditableInfosPanel.add(surnameChange);
			neweditableInfosPanel.add(ageL1);
			neweditableInfosPanel.add(ageChange);
			neweditableInfosPanel.add(submit);
			submit.addActionListener(this);
			neweditableInfosPanel.setBounds(0, 0, 450, 350);
			this.add(neweditableInfosPanel);
			this.setVisible(true);
	 }
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// when user clicks submit, app checks if the given information valid for editing or not
		// app does this with the help of isInfoValidforEditing static method of signup frame
		if(e.getSource()== submit) {
			if(SignupFrame.isInfoValidforEditing(nickname,passwordChange.getText(), nameChange.getText(),surnameChange.getText(),ageChange.getText(),email)) {
				user.setRealName(nameChange.getText());
				user.setPassword(passwordChange.getText());
				// if given new infos are valid, the infos of the user will be updated.
				//here i used a map method with a lambra expression. it changes the value of the nickname in the map
				SignupFrame.nicknamePasswordMap.computeIfPresent(nickname, (k, v) -> v=passwordChange.getText());
				user.setSurname(surnameChange.getText());
				int ageNumber= Integer.parseInt(ageChange.getText());
				user.setAge(ageNumber);
				Logger.logInfo("User succesfully changed his/her editable  infos");
				this.dispose();
				new ProfilePageUser(user);
		}
	}
		}

}
