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

// Just blurring and sharpening
public class FreeTierUser extends User {

	public FreeTierUser(String nickName, String password, String realName, String surname, String email, int age) {
		super(nickName, password, realName, surname, email, age);
		// TODO Auto-generated constructor stub
	}

	public FreeTierUser(String nickName, String password, String realName, String surname, String email, int age,
			JLabel photo) {
		super(nickName, password, realName, surname, email, age, photo);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param imageNameextension name and the extension of the selected file to be processed
	 * @param filterLevel level of the filter (0-100)
	 */
	public  JLabel blurring(String imageNameextension, double filterLevel) throws IOException{
		try {
			// keeps track of time
		long startTime = System.nanoTime();
		// casting the filter level
		filterLevel=( filterLevel/100)+1;
		ImageMatrix image = ImageSecretary.readResourceImage(imageNameextension);
		int xLenght = image.getWidth();
	    int yLenght = image.getHeight();
	    int blurDegree = (int) filterLevel;
	    for (int x = 0; x < xLenght; x++) {
	        for (int y = 0; y < yLenght; y++) {
	        	// here i keep track of the total rgb values
	            int redTotal = 0;
	            int greenTotal = 0;
	            int blueTotal = 0;
	            int pixelcount = 0;
	            // determined by the blurring degree, method looks neighbor pixels to blend with them
	            for (int i = -blurDegree; i <= blurDegree; i++) {
	                for (int j = -blurDegree; j <= blurDegree; j++) {
	                    int neighborX = x + i;
	                    int neighborY = y + j;
	                    // if neighbor pixels doesnt exceed our image their rgb values are added to total rgb values 
	                    // pixel count is incremented
	                    if (neighborX >= 0 && neighborX < xLenght && neighborY >= 0 && neighborY < yLenght) {
	                        redTotal += image.getRed(neighborX, neighborY);
	                        greenTotal += image.getGreen(neighborX, neighborY);
	                        blueTotal+= image.getBlue(neighborX, neighborY);
	                        pixelcount++;
	                    }
	                }
	            }
	            // this takes the average of the neighbor pixels' colors
	            int averageRed = redTotal / pixelcount;
	            int averageGreen = greenTotal / pixelcount;
	            int averageBlue = blueTotal / pixelcount;
	            int averageRGB = ImageMatrix.convertRGB(averageRed, averageGreen, averageBlue);
	            image.setRGB(x, y, averageRGB);
	        }
	    }
	    BufferedImage changedImage = image.getBufferedImage();
	    if(ImageSecretary.writeImageToResources(changedImage, "blurredImage", ".jpg")) {
			Logger.logInfo("Edged image is succesfully saved to pc");
		} else {
			Logger.logError("Can't save file");
		   //log in the system
		}
	    ImageIcon iconImage = new ImageIcon(changedImage);
	    JLabel label = new JLabel(iconImage);
	    long endTime = System.nanoTime();
	    long elapsedTime = endTime - startTime;
	    double milliseconds = (double) elapsedTime / 1_000_000.0;
        Logger.logInfo("Blurring filter applied to "+imageNameextension+"file, took: "+ milliseconds+"ms");
	    return label;}
		catch(IOException w){
			Logger.logError("There is an error in opening file while appyling blurring filter.");
			return null;
		}
	}
	
	public JLabel sharpening(String imageNameextension, double filterLevel) throws IOException{
		try {
			//keeping track of time
		long startTime = System.nanoTime();
		filterLevel= filterLevel/50;
		// here i took the original image and blurred image as imagematrix 
		ImageMatrix originalImage = ImageSecretary.readResourceImage(imageNameextension);
	    JLabel blurredImageLabel = this.blurring( imageNameextension, 70);
	    ImageIcon icon= (ImageIcon) blurredImageLabel.getIcon();
		Image image= icon.getImage();
		BufferedImage ourImage= (BufferedImage) image;
		ImageMatrix blurredImage= new ImageMatrix(ourImage);
		// i subtract two imagematrices 
        ImageMatrix subtractedImage = subtractMatrices(originalImage, blurredImage);
        // i add the subtracted matrix and original image
        ImageMatrix sharpenedImageMatrix = addMatrices(originalImage, subtractedImage, filterLevel);
        // the result is the sharpened image
        BufferedImage sharpenedImage = sharpenedImageMatrix.getBufferedImage();
        //saving to a file
        if(ImageSecretary.writeImageToResources(sharpenedImage, "sharpenedImage", ".jpg")) {
			Logger.logInfo("sharpened image is succesfully saved to pc");
		} else {
			Logger.logError("Can't save file");
		   //log in the system
		}
        ImageIcon iconImage = new ImageIcon(sharpenedImage);
	    JLabel label = new JLabel(iconImage);
	    //loggin the time spent
	    long endTime = System.nanoTime();
	    long elapsedTime = endTime - startTime;
	    double milliseconds = (double) elapsedTime / 1_000_000.0;
        Logger.logInfo("Sharpening filter applied to "+imageNameextension+"file, took: "+ milliseconds+"ms");
	    return label;}
		catch(IOException w){
			Logger.logError("There is an error in opening file while appyling sharpening filter.");
			return null;
		}
	    
	}
	private  ImageMatrix subtractMatrices(ImageMatrix image1, ImageMatrix image2) {
	    int xLenght = image1.getWidth();
	    int yLenght = image1.getHeight();
	    ImageMatrix subtractedMatrix = new ImageMatrix(xLenght, yLenght);
	    for (int i = 0; i < xLenght; i++) {
	        for (int j = 0; j < yLenght; j++) {
	        	// here this method iterates all the pixels of the original image and blurred image and subtracts its rgb values one by one
	            int redDifference = (int)((image1.getRed(i,j))- (image2.getRed(i, j)));
	            int greenDifference = (int)((image1.getGreen(i,j))- (image2.getGreen(i, j)));
	            int blueDifference = (int)((image1.getBlue(i,j))- (image2.getBlue(i, j)));
	            // here i have to level them since some of them are out of the range 0-255
	            redDifference = Math.min(255, Math.max(0, redDifference));
	            greenDifference = Math.min(255, Math.max(0, greenDifference));
	            blueDifference = Math.min(255, Math.max(0, blueDifference));
	            int subtractedPixel = ImageMatrix.convertRGB(redDifference, greenDifference, blueDifference);
	            subtractedMatrix.setRGB(i, j, subtractedPixel);
	        }
	    }

	    return subtractedMatrix;
	}

	private ImageMatrix addMatrices(ImageMatrix image1, ImageMatrix image2, double addingLevel) {
	    int xLenght = image1.getWidth();
	    int yLenght = image1.getHeight();
	    ImageMatrix addedMatrix = new ImageMatrix(xLenght, yLenght);
	    // here this method adds two matrices with an adding level
	    for (int i = 0; i < xLenght; i++) {
	        for (int j = 0; j < yLenght; j++) {
	            int addedred = (int) (image1.getRed(i, j) + (addingLevel *(image2.getRed(i, j))));
	            int addedgreen = (int) ((image1.getGreen(i, j)) + (addingLevel * (image2.getGreen(i, j))));
	            int addedblue = (int) ((image1.getBlue(i, j)) + (addingLevel * (image2.getBlue(i, j))));
	            // it levels out the pixel rgb values
	            addedred = Math.min(255, Math.max(0, addedred));
	            addedgreen = Math.min(255, Math.max(0, addedgreen));
	            addedblue = Math.min(255, Math.max(0, addedblue));

	            int addedPixel = ImageMatrix.convertRGB(addedred, addedgreen, addedblue);
	            addedMatrix.setRGB(i, j, addedPixel);
	        }
	    }

	    return addedMatrix;
	}

}
