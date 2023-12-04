package Users;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import imageRetievingandProcessing.ImageMatrix;
import imageRetievingandProcessing.ImageSecretary;
import loggingInfosandErrors.Logger;

// blurring sharpening brightness contrast
public class HobbyListTier extends FreeTierUser {

	public HobbyListTier(String nickName, String password, String realName, String surname, String email, int age,
			JLabel photo) {
		super(nickName, password, realName, surname, email, age, photo);
		// TODO Auto-generated constructor stub
	}

	public HobbyListTier(String nickName, String password, String realName, String surname, String email, int age) {
		super(nickName, password, realName, surname, email, age);
		// TODO Auto-generated constructor stub
	}
	public  JLabel brightness(String imageNameextension, double filterLevel) throws IOException {
		// Here filter level will not be divided by 100
		try {
			// keeping track of time
		long startTime = System.nanoTime();
		ImageMatrix image = ImageSecretary.readResourceImage(imageNameextension);
	    int xLenght = image.getWidth();
	    int yLenght = image.getHeight();
	    for (int i = 0; i < xLenght; i++) {
	        for (int j = 0; j < yLenght; j++) {
	        	// scans every pixel one by one
	        	// finds the rgb values and adds the filter level to them
	            int redAmount = image.getRed(i, j);
	            int greenAmount = image.getGreen(i, j);
	            int blueAmount = image.getBlue(i, j);
	            redAmount += filterLevel;
	            greenAmount += filterLevel;
	            blueAmount +=filterLevel;
	            // levels the rgb values to be in the range 0-255
	            redAmount = Math.min(Math.max(redAmount, 0), 255);
	            greenAmount = Math.min(Math.max(greenAmount, 0), 255);
	            blueAmount = Math.min(Math.max(blueAmount, 0), 255);
	            image.setRGB(i, j, ImageMatrix.convertRGB(redAmount, greenAmount, blueAmount));
	        }
	    }
	    BufferedImage changedImage = image.getBufferedImage();
	    if(ImageSecretary.writeImageToResources(changedImage, "brightenedImage", ".jpg")) {
			Logger.logInfo("sharpened image is succesfully saved to pc");
		} else {
			Logger.logError("Can't save file");
		   //log in the system
		}
	    // turning into a JLabel to make use of in JFrames
	    ImageIcon iconImage = new ImageIcon(changedImage);
	    JLabel label = new JLabel(iconImage);
	    long endTime = System.nanoTime();
	    long elapsedTime = endTime - startTime;
	    double milliseconds = (double) elapsedTime / 1_000_000.0;
        Logger.logInfo("Brightness filter applied to "+imageNameextension+"file, took: "+ milliseconds+"ms");
	    return label;}
		catch(IOException w){
			Logger.logError("Can't open file while applying brightness filter");
			return null;
		}
	}
	public JLabel contrast(String imageNameextension, double filterLevel) throws IOException{
		// filter level will be divided by 100 and added to the 1
		try {
			// keeping the track of time
		long startTime = System.nanoTime();
	 double changeContrast= (filterLevel/100)+1;
		ImageMatrix image = ImageSecretary.readResourceImage(imageNameextension);
		int xLenght = image.getWidth();
	    int yLenght = image.getHeight();
	    for (int i = 0; i < xLenght; i++) {
	        for (int j = 0; j < yLenght; j++) {
	        	// here methods takes the rgb amounts of each pixel
	            int redAmount = image.getRed(i, j);
	            int greenAmount = image.getGreen(i, j);
	            int blueAmount = image.getBlue(i, j);
	            // here it turns them into contrasted pixels using the filter level
	            int contrastedRed = (int) (((redAmount - 128) * changeContrast) + 128);
                int contrastedGreen = (int) (((greenAmount - 128) * changeContrast) + 128);
                int contrastedBlue = (int) (((blueAmount - 128) * changeContrast) + 128);
                // levels them out to be in the range 0-255
                contrastedRed = Math.max(0, Math.min(255, contrastedRed));
                contrastedGreen = Math.max(0, Math.min(255, contrastedGreen));
                contrastedBlue = Math.max(0, Math.min(255, contrastedBlue));
                image.setRGB(i, j, ImageMatrix.convertRGB(contrastedRed, contrastedGreen, contrastedBlue));	
	}
	    }
	    BufferedImage changedImage = image.getBufferedImage();
	    // saving the file
	    if(ImageSecretary.writeImageToResources(changedImage, "contrastedImage", ".jpg")) {
			Logger.logInfo("sharpened image is succesfully saved to pc");
		} else {
			Logger.logError("Can't save file");
		   //log in the system
		}
	    ImageIcon iconImage = new ImageIcon(changedImage);
	    JLabel label = new JLabel(iconImage);
	    long endTime = System.nanoTime();
	    long elapsedTime = endTime - startTime;
	    double milliseconds = (double) elapsedTime / 1_000_000.0;
        Logger.logInfo("Contrast filter applied to "+imageNameextension+"file, took: "+ milliseconds+"ms");
	    return label;}
		catch(IOException w) {
			Logger.logError("Can't open file while applying contrast filter");
			return null;
		}
	    }
	
}
