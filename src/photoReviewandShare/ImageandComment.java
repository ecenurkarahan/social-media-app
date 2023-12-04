/**
 * 
 */
package photoReviewandShare;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import Users.User;

public class ImageandComment {
	private JLabel imageLabel= new JLabel();
	private String comment;
	private User ImageOwner;
	/**
	 * 
	 * @param ImageOwner is a user who shared the image
	 * @param imageLabel is the photo label which contains the shared photo
	 * @param comment is the optional description which user may or may not write while sharing the picture
	 */
	public ImageandComment(User ImageOwner,JLabel imageLabel,String comment) {
		this.comment=comment;
		this.imageLabel= imageLabel;
		this.ImageOwner= ImageOwner;
	}
	public User getImageOwner() {
		return ImageOwner;
	}
	public void setImageOwner(User imageOwner) {
		ImageOwner = imageOwner;
	}
	public JLabel geticon() {
		return imageLabel;
	}
	public void setIcon(JLabel label) {
		this.imageLabel= label;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
