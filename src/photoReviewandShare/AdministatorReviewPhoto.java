package photoReviewandShare;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ProfileandDiscoverPages.ProfilePagetoOthers;
import Users.User;
import loggingInfosandErrors.Logger;

public class  AdministatorReviewPhoto extends JFrame implements ActionListener {
		private JPanel commentandNickname =new JPanel();
		private JPanel parentofPhotoP=new JPanel();
		private JPanel photoPanel= new JPanel();
		private ImageandComment imageandComment;
		private User viewerUser;
		private JButton goBacktoDiscover= new JButton("Go back");
		private JButton nicknameButton= new JButton();
		private JButton userPbutton= new JButton();
		private JButton removePhoto = new JButton("Remove Photo");
		/**
		 * 
		 * @param imageandComment is an object that takes the image owner, picture and the comment as parameters 
		 * @param viewerUser is the user who views the photo, in this frame viewer user is type admin, thus it has access to removing photo
		 */
	public AdministatorReviewPhoto(ImageandComment imageandComment,User viewerUser) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setTitle("PhotoCloud");
		this.setLayout(new BorderLayout());
		this.imageandComment= imageandComment;
		this.viewerUser= viewerUser;
		JLabel photoLabel = imageandComment.geticon();
		parentofPhotoP.add(photoPanel);
		photoPanel.add(photoLabel);
		User sharingUser= imageandComment.getImageOwner();
		String nickname= sharingUser.getNickName();
		JLabel userProfilePhoto = sharingUser.getProfilePhoto();
		nicknameButton.setText(nickname);
		userPbutton.setIcon(userProfilePhoto.getIcon());
		commentandNickname.setLayout(new GridLayout(1,5));
		commentandNickname.add(userPbutton);
		commentandNickname.add(nicknameButton);
		String comment= imageandComment.getComment();
		JLabel commentLabel = new JLabel(comment);
		commentandNickname.add(commentLabel);
		commentandNickname.add(removePhoto);
		// Admin user can click on remove button and the photo will be removed from the whole app
		// We need to re-open the discover page and the users' profile because the photo list should be updated afterwards!
		removePhoto.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Container parent= photoPanel.getParent();
					parent.remove(photoPanel);
					parent.validate();
					parent.repaint();
					Logger.logInfo("User's photo is removed from the whole app by administator.");
					sharingUser.removePhoto(imageandComment);
				}
			});
		commentandNickname.add(goBacktoDiscover);
		goBacktoDiscover.addActionListener(this);
		nicknameButton.addActionListener(this);
		userPbutton.addActionListener(this);
		this.add(parentofPhotoP,BorderLayout.CENTER);
		this.add(commentandNickname,BorderLayout.SOUTH);	
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// This part is the same as review photo class.
		if(e.getSource()== goBacktoDiscover) {
			Logger.logInfo("Admin is redirected to discover page from reviewing photo page!");
			this.dispose();
		}
		
		if(e.getSource()==nicknameButton) {
			this.dispose();
			Logger.logInfo("Admin is redirected to the profile page of the user who shared the photo from reviewing photo page!");
			new ProfilePagetoOthers(viewerUser,this.imageandComment.getImageOwner());
		}
		if(e.getSource()== userPbutton) {
			Logger.logInfo("Admin is redirected to the profile page of the user who shared the photo from reviewing photo page!");
			this.dispose();
			new ProfilePagetoOthers(viewerUser,this.imageandComment.getImageOwner());
		}
	}
	}

