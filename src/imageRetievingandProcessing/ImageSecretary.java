/**
 * 
 */
package imageRetievingandProcessing;

import java.awt.image.RenderedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

/**
 * @author karah
 *
 */
public class ImageSecretary {

    public static final String IMAGE_LOCATION = "C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/";
	private ImageSecretary() {

	}

	/**
	 * Reads the image from your resources.
	 * 
	 * @param imNameextension    name and extension of the file
	 * @return new ImageMatrix
	 * @throws IOException
	 */
	public static ImageMatrix readResourceImage(String imNameextension) throws IOException {
		return new ImageMatrix(ImageIO.read(new File(IMAGE_LOCATION + imNameextension)));
	}

	/**
	 * Writes the rendered image to your resources with the given name and extension
	 * 
	 * @param image     rendered image
	 * @param name      of the file
	 * @param extension of the file
	 * @return
	 */
	public static boolean writeFilteredImageToResources(ImageMatrix image, String name, String extension) {
		return writeImageToResources(image.getBufferedImage(), name, extension);
	}

	public static List<String> getRawImageNames() {
		List<String> res = new ArrayList<>();
		File[] files = new File(IMAGE_LOCATION).listFiles();

		for (File file : files) {
			if (file.isFile() && !file.getName().contains("_")) {
				res.add(file.getName());
			}
		}
		return res;
	}

	/**
	 * Writes the rendered image to your resources with the given name as jpg
	 * 
	 * @param image
	 * @param name
	 * @return//USE İT TO SAVE AS FİLE!
	 */
	public static boolean writeImageToResources(RenderedImage image, String name, String extension) {
		boolean result = true;
		try {
			ImageIO.write(image, "jpg", new File(IMAGE_LOCATION + name + extension));
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

}
