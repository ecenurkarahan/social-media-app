package Users;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import imageRetievingandProcessing.*;
import loggingInfosandErrors.Logger;

//blurring, sharpening, brightness, contrast, grayscale, edge detection
public class ProfessionalTier extends HobbyListTier {
	// Professional tier inherits its constructors from User class
	public ProfessionalTier(String nickName, String password, String realName, String surname, String email, int age,
			JLabel photo) {
		super(nickName, password, realName, surname, email, age, photo);
	}

	public ProfessionalTier(String nickName, String password, String realName, String surname, String email, int age) {
		super(nickName, password, realName, surname, email, age);
	}
	/**
	 * @param imageNameextension name and the extension of the selected file to be processed
	 * @param filterLevel level of the filter (0-100)
	 */
	public  JLabel grayscale(String imageNameextension, double filterLevel) throws IOException {
		try {
			// I tracked the time to log how many milliseconds has passed while implementing the filter
			long startTime = System.nanoTime();   
			filterLevel = filterLevel/100;
			// turning image to a matrix using image secretary class
		ImageMatrix image = ImageSecretary.readResourceImage(imageNameextension);
		// getting the dimensions of the image
	    int xLenght = image.getWidth();
	    int yLenght = image.getHeight();

	    for (int i = 0; i < xLenght; i++) {
	      for (int j = 0; j < yLenght; j++) {
	    	  // iterating pixel by pixel and retrieving the rgb values of the pixel
	    	  // I used imagematrix class's methods, they make bitwise operations to retrieve this values
	        int redAmount = image.getRed(i, j);
	        int greenAmount = image.getGreen(i, j);
	        int blueAmount = image.getBlue(i, j);
	        // here i created a gray color using the rgb amounts in the pixel
	        int createdGray = (int) (0.299 * redAmount + 0.587 * greenAmount + 0.114 * blueAmount);
	        // leveled the gray amount by filter level
	        int leveledGray=(int) (filterLevel*createdGray);
	        // using the imagematrix convertrgb method, i created a rgb with leveled gray color
	        int grayRGB = ImageMatrix.convertRGB(leveledGray, leveledGray, leveledGray);
	        // i set the iterated pixel's rgb value to grayRGB
	        image.setRGB(i, j, grayRGB);
	      }
	    }
	    BufferedImage changedImage = image.getBufferedImage();
	    if(ImageSecretary.writeImageToResources(changedImage, "grayedImage", ".jpg")) {
			Logger.logInfo("Grayed image is succesfully saved to pc");
		} else {
			Logger.logError("Can't save file");
		   //log in the system
		}
	    // I return a JLabel in order to make use of the image in my JFrames
	    ImageIcon iconImage = new ImageIcon(changedImage);
	    JLabel label = new JLabel(iconImage);
	    long endTime = System.nanoTime();
	    long elapsedTime = endTime - startTime;
	    double milliseconds = (double) elapsedTime / 1_000_000.0;
	    Logger.logInfo("Grayscale filter applied to "+imageNameextension+"file, took: "+ milliseconds+"ms");
	    return label;
		}
		catch(IOException e) {
			Logger.logError("Can't open file while applying grayscale filter");
			return null;
		}
	}
	/**
	 * @param imageNameextension name and the extension of the selected file to be processed
	 * @param filterLevel level of the filter (0-100)
	 */
	public JLabel edgeDetection(String imageNameextension, double filterLevel) throws IOException {
		try {
			// I tracked the time to log how many milliseconds has passed while implementing the filter
			long startTime = System.nanoTime();
			JLabel grayscaledImage= this.grayscale(imageNameextension,filterLevel);
			ImageIcon icon= (ImageIcon) grayscaledImage.getIcon();
			Image image= icon.getImage();
			BufferedImage ourImage= (BufferedImage) image;
			// I first grayed the image to apply filter properly
			ImageMatrix grayedImage= new ImageMatrix(ourImage);
			int yLenght = ourImage.getHeight();
			int xLenght = ourImage.getWidth();
			// Here i used sobel operations, in sobel operations we need to determine the difference between vertical pixel weights and horizontal weights
			// I set a border value, if the difference is bigger than border value pixel weight will be 255 which is white
			// If difference is smaller than border, then pixel will be black
			int border=20,
			pixelWeight= 0;
			int[][] verticalGradient = new int[xLenght][yLenght];
			int[][] horizontalGradient = new int[xLenght][yLenght];
			int[][] edgeWeight = new int[xLenght][yLenght];
			for (int y=1; y<yLenght-1; y++) {
				for (int x=1; x<xLenght-1; x++) {
					// i iterated through a grayscaled image
					// since i couldn't blurr the image this edge detection filter is an basic level filter
					// the resulting image is not so qualified
					// here i get the horizontal differences using sobel operations
					horizontalGradient[x][y] = (int)(grayedImage.getRGB(x-1, y+1)& 0xFF + 2*(grayedImage.getRGB(x, y+1)& 0xFF) + grayedImage.getRGB(x+1, y+1)& 0xFF
						- grayedImage.getRGB(x-1, y-1)& 0xFF - 2*(grayedImage.getRGB(x, y-1)& 0xFF) - grayedImage.getRGB(x+1, y-1)& 0xFF);
					// here i get the vertical differences using sobel operations
					verticalGradient[x][y] = (int)(grayedImage.getRGB(x+1, y-1)& 0xFF + 2*(grayedImage.getRGB(x+1, y)& 0xFF) + grayedImage.getRGB(x+1, y+1)& 0xFF
							- grayedImage.getRGB(x-1, y-1)& 0xFF - 2*(grayedImage.getRGB(x-1, y)& 0xFF) - grayedImage.getRGB(x-1, y+1)& 0xFF);
					// this is the final gradient which will determine the pixel is going to be white or black
					edgeWeight[x][y] = (int)(Math.sqrt(verticalGradient[x][y] * verticalGradient[x][y] + horizontalGradient[x][y] * horizontalGradient[x][y]));
					if (edgeWeight[x][y] > border)
						pixelWeight = (255<<24) | (255<<16) | (255<<8) | 255;
					else 
						pixelWeight= (255<<24) | (0<<16) | (0<<8) | 0; 
					ourImage.setRGB(x,y,pixelWeight);
					}}
			
			ImageIcon icon3= new ImageIcon(ourImage);
			// here i saved the image to image directory
			if(ImageSecretary.writeImageToResources(ourImage, "edgedImage", ".jpg")) {
				Logger.logInfo("Edged image is succesfully saved to pc");
			} else {
				Logger.logError("Can't save file");
			   //log in the system
			}
	        JLabel label = new JLabel(icon3);
	        long endTime = System.nanoTime();
		    long elapsedTime = endTime - startTime;
		    double milliseconds = (double) elapsedTime / 1_000_000.0;
	        Logger.logInfo("Edge detection filter applied to "+imageNameextension+"file, took: "+ milliseconds+"ms");
	        return label;
   
		}
		catch(IOException e){
			Logger.logError("Can't open file while applying edgedetection filter");
			return null;
		}
		
	}
	}
