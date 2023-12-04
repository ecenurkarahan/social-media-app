package photoReviewandShare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.*;

import javax.swing.*;

import ProfileandDiscoverPages.DiscoverPage;
import ProfileandDiscoverPages.ProfilePageUser;
import Users.*;
import loggingInfosandErrors.Logger;

public class SharePhotoFrame extends JFrame implements ActionListener{
	private User user;
	private JButton blurring= new JButton("Blurring");
	private JButton sharpening= new JButton("Sharpening");
	private JButton brightness= new JButton("Brightness");
	private JButton contrast= new JButton("Contrast");
	private JButton grayscale= new JButton("Grayscale");
	private JButton edgeDetection= new JButton("Edge detection");
	private JButton share = new JButton("Share");
	private JButton imply= new JButton("Set the level");
	private JTextArea filterLevel= new JTextArea();
	private JTextArea comment= new JTextArea(" ");
	private JLabel levelLabel= new JLabel("Filter Level (%)");
	private JPanel lowerPanel = new JPanel();
	private JLabel commentLabel= new JLabel("Comment:");
	private JPanel commentPanel = new JPanel();
	private JPanel effectsPanel = new JPanel();
	private JButton selectPhoto= new JButton("Select Photo");
	private JButton goBack= new JButton("Go Back");
	private JPanel photoPanel = new JPanel();
	private JLabel photoLabel = new JLabel();
	/**
	 * 
	 * @param user is the user who will share the photo
	 */
	// user first need to click on imply and then select the filter type!!!
	public SharePhotoFrame(User user) {
		this.user= user;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		this.setTitle("PhotoCloud");
		this.setLayout(null);
		commentPanel.setLayout(new BorderLayout());
		commentPanel.add(commentLabel,BorderLayout.WEST);
		commentPanel.add(comment,BorderLayout.CENTER);
		effectsPanel.setLayout(new GridLayout(1,6,10,10));
		effectsPanel.add(blurring);
		effectsPanel.add(sharpening);
		effectsPanel.add(brightness);
		effectsPanel.add(contrast);
		effectsPanel.add(grayscale);
		effectsPanel.add(edgeDetection);
		lowerPanel.setLayout(new BorderLayout());
		JPanel levelPanel= new JPanel();
		levelPanel.setLayout(new GridLayout(1,3,10,10));
		levelPanel.add(levelLabel);
		levelPanel.add(filterLevel);
		levelPanel.add(imply);
		lowerPanel.add(levelPanel, BorderLayout.WEST);
		lowerPanel.add(goBack, BorderLayout.EAST);
		lowerPanel.add(share,BorderLayout.CENTER);
		photoPanel.add(photoLabel);
		photoPanel.add(selectPhoto);
		goBack.addActionListener(this);
		// to share the image, first we retrieve an image from the pc
		// I took the images from only one directory to not get file io exceptions. 
		// directory is under the imageretrievingandprocessing class namely image
		selectPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooseFile = new JFileChooser();
		        int result = chooseFile.showOpenDialog(SharePhotoFrame.this);
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = chooseFile.getSelectedFile();
		            String absolutePathofImage = selectedFile.getAbsolutePath();
		            ImageIcon profile = new ImageIcon(absolutePathofImage);
		            photoLabel.setLayout(new GridLayout(2,1));
		            photoLabel.setIcon(profile);
		            selectPhoto.setEnabled(false);// user will not be able to select another photo
		            // to apply filters on the images, i needed to obtain the absolute path of the image
		            // because of that, i passed its path with the image to use later
		            photoLabel.setText(absolutePathofImage);
		        }
			}
		});
		// Here app checks if the given filter degree is valid, if valid value is given user can't change the value afterwards
		// if not valid, user is warned
		imply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean trueValueGiven = true;
				if(filterLevel.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Please enter a filter degree!!", "Attention",JOptionPane.PLAIN_MESSAGE);
					Logger.logError("Error in setting the filter level: user didnt entered a filter level");
					trueValueGiven = false;
				}
				try {
					int level= Integer.parseInt(filterLevel.getText());
				}
				catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(null, "Filter degree must be an integer!!", "Attention",JOptionPane.ERROR_MESSAGE);
					Logger.logError("Error in setting the filter level: user didnt entered a convenient filter level");
					trueValueGiven = false;
				}
				if(Integer.parseInt(filterLevel.getText())<0||Integer.parseInt(filterLevel.getText())>100) {
					JOptionPane.showMessageDialog(null, "Filter degree must be an integer between 0-100!!", "Attention",JOptionPane.ERROR_MESSAGE);
					Logger.logError("Error in setting the filter level: user didnt entered a convenient filter level");
					trueValueGiven = false;
				}
				if(trueValueGiven) {
					Logger.logInfo("Filter level is setted!");
					imply.setEnabled(false);
				}
			}
			
		});

		blurring.addActionListener(this);
		sharpening.addActionListener(this);
		brightness.addActionListener(this);
		contrast.addActionListener(this);
		grayscale.addActionListener(this);
		edgeDetection.addActionListener(this);
		share.addActionListener(this);
		photoPanel.setBounds(50, 50,800,400);
		effectsPanel.setBounds(50,500 , 800, 100);
		commentPanel.setBounds(50, 600,750 , 50);
		lowerPanel.setBounds(50, 660, 800, 50);
		this.add(commentPanel);
		this.add(photoPanel);
		this.add(effectsPanel);
		this.add(lowerPanel);
		this.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// If user changes his/her decision about sharing photo, he/she will be redirected to the discover page
		if(e.getSource()== goBack) {
			this.dispose();
			new DiscoverPage(user);
		}
		if(e.getSource()== blurring) {
		if(photoLabel.getIcon()!=null) {
			String absolutePathOfImage= photoLabel.getText();
			// I used string concatenation here to get the files name and extension, my filter methods accept only from an exact place of my computer
			String nameandExtension=absolutePathOfImage.substring(102);
			JLabel filteredImage;
			try {
				filteredImage = user.blurring(nameandExtension, Integer.parseInt(filterLevel.getText()));
				// here it checks if the filtered image is null or not because i implemented filter methods in the user class
				// some user tiers have access to limited filter types, thus if the user doesn't have an overridden filter method
				// filter method will return null and button is useless for this user type
				if(filteredImage!= null) {
					ImageIcon filteredImage1= (ImageIcon) filteredImage.getIcon();
					photoLabel.setIcon(filteredImage1);
					photoLabel.setText("");
					
				}
			} catch (NumberFormatException e1) // filter degree must be given to implement a filter, user will be warned
			{
				JOptionPane.showMessageDialog(null, "Filter degree must be given!!", "Attention",JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				Logger.logError("Error in implementing blurr filter!");
			}
			
			}// if user didn't select an image, it will be warned
		else {
				JOptionPane.showMessageDialog(null, "Please select an image!!", "Attention",JOptionPane.ERROR_MESSAGE);
				Logger.logError("User didnt select any image!");
			}
		}
		// Other filter action listeners are the same except the filters they apply to an image is different
		// thus i did not write documentation for them
		// at the end there is a share button action listener
		if(e.getSource()== sharpening) {
			if(photoLabel.getIcon()!=null) {
				String absolutePathOfImage= photoLabel.getText();
				String nameandExtension=absolutePathOfImage.substring(102);
				JLabel filteredImage;
				try {
					filteredImage = user.sharpening(nameandExtension, Integer.parseInt(filterLevel.getText()));
					if(filteredImage!= null) {
						ImageIcon filteredImage1= (ImageIcon) filteredImage.getIcon();
						photoLabel.setIcon(filteredImage1);
						photoLabel.setText("");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Filter degree must be given!!", "Attention",JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					Logger.logError("Error in imlementing sharpening filter!");
				}
				
				}
			else {
				JOptionPane.showMessageDialog(null, "Please select an image!!", "Attention",JOptionPane.ERROR_MESSAGE);
				Logger.logError("User didnt select any image!");
			}
			}
		if(e.getSource()== brightness) {
			if(photoLabel.getIcon()!=null) {
				String absolutePathOfImage= photoLabel.getText();
				String nameandExtension=absolutePathOfImage.substring(102);
				JLabel filteredImage;
				try {
					filteredImage = user.brightness(nameandExtension, Integer.parseInt(filterLevel.getText()));
					if(filteredImage!= null) {
						ImageIcon filteredImage1= (ImageIcon) filteredImage.getIcon();
						photoLabel.setIcon(filteredImage1);
						photoLabel.setText("");
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Filter degree must be given!!", "Attention",JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					Logger.logError("Error in imlementing brightness filter!");
				}
				
				}
			else {
				JOptionPane.showMessageDialog(null, "Please select an image!!", "Attention",JOptionPane.ERROR_MESSAGE);
				Logger.logError("User didnt select any image!");
			}
			}
		if(e.getSource()== contrast) {
			if(photoLabel.getIcon()!=null) {
				String absolutePathOfImage= photoLabel.getText();
				String nameandExtension=absolutePathOfImage.substring(102);
				JLabel filteredImage;
				try {
					filteredImage = user.contrast(nameandExtension, Integer.parseInt(filterLevel.getText()));
					if(filteredImage!= null) {
						ImageIcon filteredImage1= (ImageIcon) filteredImage.getIcon();
						photoLabel.setIcon(filteredImage1);
						photoLabel.setText("");
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Filter degree must be given!!", "Attention",JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					Logger.logError("Error in imlementing contrast filter!");
				}
				
				}
			else {
				JOptionPane.showMessageDialog(null, "Please select an image!!", "Attention",JOptionPane.ERROR_MESSAGE);
				Logger.logError("User didnt select any image!");
			}
			}
		if(e.getSource()== grayscale) {
			if(photoLabel.getIcon()!=null) {
				String absolutePathOfImage= photoLabel.getText();
				String nameandExtension=absolutePathOfImage.substring(102);
				JLabel filteredImage;
				try {
					filteredImage = user.grayscale(nameandExtension, Integer.parseInt(filterLevel.getText()));
					if(filteredImage!= null) {
						ImageIcon filteredImage1= (ImageIcon) filteredImage.getIcon();
						photoLabel.setIcon(filteredImage1);
						photoLabel.setText("");
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Filter degree must be given!!", "Attention",JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					Logger.logError("Error in imlementing grayscale filter!");
				}
				
				}
			else {
				JOptionPane.showMessageDialog(null, "Please select an image!!", "Attention",JOptionPane.ERROR_MESSAGE);
				Logger.logError("User didnt select any image!");
			}
			}
		if(e.getSource()== edgeDetection) {
			if(photoLabel.getIcon()!=null) {
				String absolutePathOfImage= photoLabel.getText();
				String nameandExtension=absolutePathOfImage.substring(102);
				JLabel filteredImage;
				try {
					filteredImage = user.edgeDetection(nameandExtension, Integer.parseInt(filterLevel.getText()));
					if(filteredImage!= null) {
						ImageIcon filteredImage1= (ImageIcon) filteredImage.getIcon();
						photoLabel.setIcon(filteredImage1);
						photoLabel.setText("");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Filter degree must be given!!", "Attention",JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					Logger.logError("Error in imlementing edge detection filter!");
				}
				
				}
			else {
				JOptionPane.showMessageDialog(null, "Please select an image!!", "Attention",JOptionPane.ERROR_MESSAGE);
				Logger.logError("User didnt select any image!");
			}
			}
		// share button action listener checks if user selected an image
		//if selected, app turns the selected image into a imageandcomment object and shares it
		// there is an optional comment section, app retrieves the comment value to turn the image into a imageandcomment object.
		if(e.getSource()== share) {
			if(photoLabel.getIcon()!=null) {
				ImageIcon photo=(ImageIcon) photoLabel.getIcon();
				JLabel newLabel= new JLabel(photo);
				String usersComment= comment.getText();
				ImageandComment imagetoShare = new ImageandComment(user,newLabel,usersComment);
				user.sharePhoto(imagetoShare);
				Logger.logInfo("Image is shared successfully by user with nickname "+ user.getNickName());
				this.dispose();
				new ProfilePageUser(user);
			}// if user did not select an image, it will be warned and logged as an error
			else {
				JOptionPane.showMessageDialog(null, "Please select an image!!", "Attention",JOptionPane.ERROR_MESSAGE);
				Logger.logError("User didnt select any image!");
			}
		}
	}

}
