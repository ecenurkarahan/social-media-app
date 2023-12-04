package ProfileandDiscoverPages;

import javax.swing.*;

import LoginSignup.LoginFrame;
import LoginSignup.SignupFrame;
import Users.*;
import loggingInfosandErrors.Logger;
import photoReviewandShare.AdministatorReviewPhoto;
import photoReviewandShare.ImageandComment;
import photoReviewandShare.ReviewPhoto;
import photoReviewandShare.SharePhotoFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProfilePageUser extends JFrame implements ActionListener {
	private JPanel upperPanel =new JPanel();
	private JPanel photoGrid= new JPanel();
	private JLabel profileLabel= new JLabel();
	private JButton remove= new JButton();
	private User user;
	private JButton edit= new JButton("Edit Profile");
	private JButton changeProfileButton = new JButton("Change Profile Photo");
	private JButton discoverpageButton = new JButton("Discover Page");
	private JButton addPhotoButton = new JButton("Add Photo");
	private JButton logout= new JButton("Log Out");
	/**
	 * 
	 * @param user : User is the owner of this profile page who has access to editing her/his own information
	 */
	public ProfilePageUser(User user)  {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setTitle("PhotoCloud");
		this.setLayout(null);
		this.user= user;
		
		String password = user.getPassword();
		JLabel passwordL1= new JLabel("Password:");
		JLabel passwordL2= new JLabel(password);
		
		String email = user.getEmail();
		JLabel emailL1= new JLabel("Email:");
		JLabel emailL2= new JLabel(email);
		
		String nickname = user.getNickName();
		JLabel nicknameL1= new JLabel("Nickname:");
		JLabel nicknameL2= new JLabel(nickname);
		
		String name = user.getRealName();
		JLabel nameL1= new JLabel("Name:");
		JLabel nameL2= new JLabel(name);
		
		String surname = user.getSurname();
		JLabel surnameL1= new JLabel("Surname:");
		JLabel surnameL2= new JLabel(surname);
		
		int age = user.getAge();
		JLabel ageL1= new JLabel("Age:");
		JLabel ageL2= new JLabel(Integer.toString(age));
		
		upperPanel.setLayout(new BorderLayout());
		profileLabel.setIcon( user.getProfilePhoto().getIcon());
		profileLabel.setPreferredSize(new Dimension(200,200));
		JPanel profilePanel= new JPanel();
		profilePanel.setLayout(new BorderLayout());
		profilePanel.add(changeProfileButton,BorderLayout.SOUTH);
		changeProfileButton.addActionListener(new ActionListener() {
			// This is anonymous action listener. By clicking to the change profile, user is redirected to a photo selection frame.
			// I used JFileChooser here which is a gui component helps us to retrieve files from our personal computer.
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooseFile = new JFileChooser();
		        int result = chooseFile.showOpenDialog(ProfilePageUser.this);
		        // If user selects a image , profile picture will be changed.
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = chooseFile.getSelectedFile();
		            String absolutePathofImage = selectedFile.getAbsolutePath();
		            ImageIcon profile = new ImageIcon(absolutePathofImage);
		            Logger.logInfo("User's profile picture is changed.");
		            profileLabel.setIcon(profile);
		            user.setProfilePhoto(profileLabel);
		        }
			}
		});
		profilePanel.add(profileLabel,BorderLayout.CENTER);
		upperPanel.add(profilePanel, BorderLayout.WEST);
		JPanel editableInfos= new JPanel();
		editableInfos.setLayout(new GridLayout(7,2,10,10));
		editableInfos.add(emailL1);
		editableInfos.add(emailL2);
		editableInfos.add(nicknameL1);
		editableInfos.add(nicknameL2);
		editableInfos.add(passwordL1);
		editableInfos.add(passwordL2);
		editableInfos.add(nameL1);
		editableInfos.add(nameL2);
		editableInfos.add(surnameL1);
		editableInfos.add(surnameL2);
		editableInfos.add(ageL1);
		editableInfos.add(ageL2);
		editableInfos.add(edit);
		upperPanel.add(editableInfos,BorderLayout.CENTER);
		edit.addActionListener(this);
		upperPanel.add(discoverpageButton,BorderLayout.LINE_END);
		discoverpageButton.addActionListener(this);
		// will add all the photos of the user to her/his profile page with a see photo and remove photo button for every photo
		for(ImageandComment imageandComment : user.getUsersallPhotos()) {
			this.remove= new JButton("remove");
			JLabel photo = imageandComment.geticon();
			JButton seePhoto= new JButton("See photo");
			JPanel mypanel = new JPanel();
			mypanel.setLayout(new BorderLayout());
			mypanel.add(seePhoto,BorderLayout.NORTH);
			mypanel.add(photo,BorderLayout.CENTER);
			mypanel.add(remove,BorderLayout.SOUTH);
			photoGrid.add(mypanel);
			seePhoto.addActionListener(new ActionListener(){
				// anonymous action listener which leads user to review photo frame for user to see her/his own photo
				public void actionPerformed(ActionEvent e) {
					new ReviewPhoto(imageandComment,user);
					Logger.logInfo("User is redirected review page of his/her own photo.");}
				
			});
			remove.addActionListener(new ActionListener(){
				//anonymous action listener which removes the photo from the whole application
				public void actionPerformed(ActionEvent e) {
					Container parent= mypanel.getParent();
					parent.remove(mypanel);
					parent.validate();
					parent.repaint();
					Logger.logInfo("User's photo is removed from his/her profile page.");
					user.removePhoto(imageandComment);
				}
			});
			
		}
		photoGrid.setLayout(new GridLayout(3,3,10,10));
		upperPanel.setBounds(0, 0, 1000, 200);
		photoGrid.setBounds(10, 210, 1000, 700);
		addPhotoButton.addActionListener(this);
		addPhotoButton.setBounds(500, 710,100, 50);
		logout.setBounds(650,710 , 100, 50);
		logout.addActionListener(this);
		this.add(logout);
		this.add(addPhotoButton);
		this.add(upperPanel);
		this.add(photoGrid);
		
		
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// will redirect user to the discover page
		if(e.getSource()== discoverpageButton) {
			Logger.logInfo("User is redirected to the discover page from his/her profile page.");
			this.dispose();
			new DiscoverPage(user);
		}
		// will let user to add photo from her/his own profile page
		if(e.getSource()==addPhotoButton) {
			Logger.logInfo("User is redirected to the add photo page from his/her profile page.");
			this.dispose();
			new SharePhotoFrame(user);
		}
		// will log out from the user's account and redirect to a new log in frame 
		if( e.getSource()== logout) {
			Logger.logInfo("User with nickname "+ user.getNickName()+" logged out from the app");
			this.dispose();
			new LoginFrame();
		}
		// will open a information editing page. editable informations are : password, name, nickname,age
		if(e.getSource()== edit) {
			Logger.logInfo("User is redirected to the edit info from his/her profile page.");
			this.dispose();
			new EditProfile(user);
		}
	}
	
}
