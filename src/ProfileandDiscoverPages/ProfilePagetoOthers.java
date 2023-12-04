package ProfileandDiscoverPages;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Users.User;
import loggingInfosandErrors.Logger;
import photoReviewandShare.AdministatorReviewPhoto;
import photoReviewandShare.ImageandComment;
import photoReviewandShare.ReviewPhoto;
// this page should be implemented after doing the discove page and share photo method
public class ProfilePagetoOthers extends JFrame implements ActionListener {

	private JLabel titleLabel= new JLabel(); ;
	private JPanel profilePanel =new JPanel();
	private JPanel infoPanel= new JPanel();
	private JPanel photoGrid= new JPanel();
	private JButton goback = new JButton("Go Back");
	private JLabel profileLabel= new JLabel();
	private JButton remove= new JButton();
	private User viewerUser;
	private User actualUser;
	/**
	 * 
	 * @param viewerUser: is the user who views the profile, when clicked any button the actions are viewer user's actions.
	 * @param actualUser: actual user is the person whose profile is seen by the viewer user. viewer user doesn't have right to edit actual user's infos
	 */
	public ProfilePagetoOthers(User viewerUser, User actualUser)  {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setTitle("PhotoCloud");
		this.setLayout(null);
		this.viewerUser= viewerUser;
		this.actualUser = actualUser;
			
		String nickname = actualUser.getNickName();
		JLabel nicknameL1= new JLabel("Nickname:");
		JLabel nicknameL2= new JLabel(nickname);
		
		String name = actualUser.getRealName();
		JLabel nameL1= new JLabel("Name:");
		JLabel nameL2= new JLabel(name);
		
		String surname = actualUser.getSurname();
		JLabel surnameL1= new JLabel("Surname:");
		JLabel surnameL2= new JLabel(surname);
		
		int age = actualUser.getAge();
		JLabel ageL1= new JLabel("Age:");
		JLabel ageL2= new JLabel(Integer.toString(age));
		profilePanel.setLayout(new BorderLayout());
		infoPanel.setLayout(new GridLayout(4,2,10,10));
		photoGrid.setLayout(new GridLayout(3,3,10,10));
		profileLabel.setIcon( actualUser.getProfilePhoto().getIcon());
		profilePanel.add(profileLabel, BorderLayout.WEST);
		infoPanel.add(nicknameL1);
		infoPanel.add(nicknameL2);
		infoPanel.add(nameL1);
		infoPanel.add(nameL2);
		infoPanel.add(surnameL1);
		infoPanel.add(surnameL2);
		infoPanel.add(ageL1);
		infoPanel.add(ageL2);
		profilePanel.add(infoPanel,BorderLayout.CENTER);
		// it adds the actual user's all photos to photo grid, there is no remove button if the viewer user is not the type administrator
		for(ImageandComment photo: actualUser.getUsersallPhotos()) {
			JPanel photoPanel = new JPanel();
			photoPanel.setLayout(new BorderLayout());
			JButton seePhoto= new JButton("See Photo");
			JLabel image= photo.geticon();
			photoPanel.add(image,BorderLayout.CENTER);
			photoPanel.add(seePhoto,BorderLayout.SOUTH);
			photoGrid.add(photoPanel);
			seePhoto.addActionListener(new ActionListener() {
				// anonymous action listener for the see photo button which checks if the viewer user is admin or not
				public void actionPerformed(ActionEvent e) {
					// if viewer user is admin, admin is redirected to administrator review photo frame
					if(viewerUser.isAdmin()) {
						new AdministatorReviewPhoto(photo,viewerUser);
						Logger.logInfo("Admin user is redirected to the shared photo of the other user");
					} 
					// if viewer user is a normal user, it will redirected to review photo frame
					else {
					new ReviewPhoto(photo,viewerUser);
					Logger.logInfo("User is redirected to the shared photo of the some other user");}
				}
			});
		}
		goback.addActionListener(this);
		profilePanel.setBounds(50, 20, 1000, 200);
		goback.setBounds(0, 700,1000 , 50);
		photoGrid.setBounds(0, 230, 1000,750);
		this.add(goback);
		this.add(profilePanel);
		this.add(photoGrid);
		
		
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// will go back to viewer user's discover page. This frame will be closed.
		if(e.getSource()== goback) {
			Logger.logInfo("Profile page of the other user is closed and user is redirected to the discover page ");
			this.dispose();
			
		}
	}
	
}
