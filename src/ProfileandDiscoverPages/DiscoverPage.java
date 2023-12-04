package ProfileandDiscoverPages;

import java.awt.event.ActionListener;
import java.util.*;

import Users.*;
import loggingInfosandErrors.Logger;
import photoReviewandShare.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import LoginSignup.SignupFrame;

public class DiscoverPage extends JFrame implements ActionListener{
	private JPanel panel1 =new JPanel();
	private JPanel photosPanel= new JPanel();
	private User user;
	JButton addPhotoButton= new JButton("Add Photo");
	JButton myProfileButton = new JButton("My Profile");
	JButton photoButton= new JButton();
	/**
	 * 
	 * @param user is the user who logged in the application. photos will be seen from his/ her account.
	 */
	public DiscoverPage(User user) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setTitle("PhotoCloud");
		this.setLayout(new BorderLayout());
		this.user= user;
		JLabel title = new JLabel("Discover The World!");
		// Here, i take the user list which i created earlier, this for loop iterates over all users
		ArrayList<User> allUsers= SignupFrame.userList;
		photosPanel.setLayout(new GridLayout(3,3,10,10));
		for(User usertemp: allUsers) {
			ArrayList<ImageandComment> allSharedPhotos= usertemp.getUsersallPhotos();
			//inner for loop iterates over an individual users photos, photos are the members of the imageandcomment class since shared photos also 
			// may contain comment by sharing user.
			for(ImageandComment photo: allSharedPhotos) {
				JLabel photoLabel= photo.geticon();
				ImageIcon image= (ImageIcon) photoLabel.getIcon();
				photoButton= new JButton(image);
				photoButton.setPreferredSize(new Dimension(100,100));
				photosPanel.add(photoButton);
				// I made every image as a button so when a user clicks on the photo, photo review page will open
				photoButton.addActionListener(new ActionListener() {
					// as explained before, app opens different review photo pages for admin and normal user
					@Override
					public void actionPerformed(ActionEvent e) {
						if(user.isAdmin()) {
							new AdministatorReviewPhoto(photo,user);
							Logger.logInfo("Admin user is redirected to the shared photo of the other user");
						}
						else {
						new ReviewPhoto(photo, user);
						Logger.logInfo("User is redirected to the shared photo of the other user");
						}
					}
					
				});
			}
		}
		
		panel1.setLayout(new GridLayout(1,2));
		panel1.add(addPhotoButton);
		panel1.add(myProfileButton);
		addPhotoButton.addActionListener(this);
		
		myProfileButton.addActionListener(this);
		
		this.add(title,BorderLayout.BEFORE_FIRST_LINE);
		this.add(photosPanel,BorderLayout.CENTER);
		this.add(panel1,BorderLayout.SOUTH);
		
		
		
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// User can also add photo from the discover page, this action listener
		// redirects user to share photo page
		if(e.getSource()== addPhotoButton) {
			Logger.logInfo("User is redirected to the sharing page from discover page!");
			this.dispose();
			new SharePhotoFrame(user);
		}
		// this action listener redirects user to her/his profile page
		if(e.getSource()== myProfileButton) {
			Logger.logInfo("User is redirected to the his/her profile page!");
			this.dispose();
			new ProfilePageUser(user);
		}
	}

}
