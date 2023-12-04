/**
 * 
 */
package photoReviewandShare;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ProfileandDiscoverPages.*;
import Users.User;
import loggingInfosandErrors.Logger;
// this is to 
/**
 * @author karah
 *
 */
public class ReviewPhoto extends JFrame implements ActionListener {
	private JPanel commentandNickname =new JPanel();
	private JPanel photoPanel= new JPanel();
	private ImageandComment imageandComment;
	private User viewerUser;
	private JButton goBacktoDiscover= new JButton("Go back");
	private JButton nicknameButton= new JButton();
	private JButton userPbutton= new JButton();
	/**
	 * 
	 * @param imageandComment is an object that takes the image owner, picture and the comment as parameters 
	 * @param viewerUser is the user who views the photo, unless viewer user is an admin, it doesn't have right to remove photo
	 */
public ReviewPhoto(ImageandComment imageandComment,User viewerUser) {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(1000, 800);
	this.setTitle("PhotoCloud");
	this.setLayout(new BorderLayout());
	this.imageandComment= imageandComment;
	this.viewerUser= viewerUser;
	JLabel photoLabel = imageandComment.geticon();
	photoPanel.add(photoLabel);
	User sharingUser= imageandComment.getImageOwner();
	String nickname= sharingUser.getNickName();
	JLabel userProfilePhoto = sharingUser.getProfilePhoto();
	nicknameButton.setText(nickname);
	userPbutton.setIcon(userProfilePhoto.getIcon());
	commentandNickname.setLayout(new GridLayout(1,4));
	commentandNickname.add(userPbutton);
	commentandNickname.add(nicknameButton);
	String comment= imageandComment.getComment();
	JLabel commentLabel = new JLabel(comment);
	commentandNickname.add(commentLabel);
	commentandNickname.add(goBacktoDiscover);
	goBacktoDiscover.addActionListener(this);
	nicknameButton.addActionListener(this);
	userPbutton.addActionListener(this);
	this.add(photoLabel,BorderLayout.CENTER);
	this.add(commentandNickname,BorderLayout.SOUTH);	
	this.setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e) {
	
	// Will redirect the viewer user to the discover page from photo viewing page
	if(e.getSource()== goBacktoDiscover) {
		Logger.logInfo("User is redirected to discover page from reviewing photo page!");
		this.dispose();
	}
	// will open the image owner's profile page when clicked the nickname button
	if(e.getSource()==nicknameButton) {
		this.dispose();
		Logger.logInfo("User is redirected to the profile page of the user who shared the photo from reviewing photo page!");
		new ProfilePagetoOthers(viewerUser,this.imageandComment.getImageOwner());
	}
	// will open the image owner's profile page when clicked the profile picture button, our viewer user will stay as viewer.
	if(e.getSource()== userPbutton) {
		Logger.logInfo("User is redirected to the profile page of the user who shared the photo from reviewing photo page!");
		this.dispose();
		new ProfilePagetoOthers(viewerUser,this.imageandComment.getImageOwner());
	}
}
}
