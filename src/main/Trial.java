package main;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import LoginSignup.*;
import ProfileandDiscoverPages.*;
import Users.*;
import imageRetievingandProcessing.*;
import photoReviewandShare.*;
/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own 
without any help from anyone else. The effort in the project thus belongs 
completely to me. I did not search for a solution, or I did not consult any 
program written by others or did not copy any program from other sources. I 
read and followed the guidelines provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID 
SIGNATURE: Ece Nur Karahan, 80029
*************************************************************************/

public class Trial {
	//C:\Users\karah\eclipse-workspace\EceNurKarahanProgrammingProject\src\imageRetievingandProcessing\image
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		JLabel gunbatimi= getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/günbatımı.jpg");
		JLabel hanımeli= getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/hanımeli.jpg") ;
		JLabel yavrukedi = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/yavrukedi.jpg");
		JLabel okanandMe= getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/okan.jpg");
		JLabel cicek34 = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/cicek34.jpg");
		JLabel sumbul = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/sümbül.jpg");
		JLabel bobRossPhoto = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/bobross2.jpg");
		JLabel bobRossPainting = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/bobross1.png");
		JLabel lale = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/lale.jpg");
		JLabel frezya= getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/frezya.jpg");
		JLabel saricicek = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/sarıçicek.jpg");
		JLabel maviebru = getImageasLabel("C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/maviebru.jpg");
		// creating user instances
		Administrator admin = new Administrator("admin","12345678","Admin","Staff","admin@ku.edu.tr",100);
		ProfessionalTier ece = new ProfessionalTier("ecenurkarahan", "12121212", "Ece Nur", "Karahan", "ekarahan21@ku.edu.tr", 20,okanandMe);
		ProfessionalTier okan = new ProfessionalTier("okantunckin", "12121212", "Okan", "Tunçkın", "otunckin@ku.edu.tr", 21,okanandMe);
		ProfessionalTier serife = new ProfessionalTier("serifegozen", "12121212", "Şerife", "Gözen", "sgozen@ku.edu.tr", 38);
		HobbyListTier zeynep = new HobbyListTier("zeynepgozen", "00000000", "Zeynep", "Gözen", "zgozen@ku.edu.tr", 15,sumbul);
		HobbyListTier bobross = new HobbyListTier("bobross", "littlehappy", "Bob", "Ross", "bross@ku.edu.tr", 70,bobRossPhoto);
		HobbyListTier melek = new HobbyListTier("melekbalkan", "kedykedy", "Melek", "Balkan", "mbalkan22@ku.edu.tr", 19,hanımeli);
		FreeTierUser ipek = new FreeTierUser("ipekakbayturk", "kankistan", "İpek", "Akbaytürk", "iakbayturk21@ku.edu.tr", 20,hanımeli);
		FreeTierUser kedim = new FreeTierUser("adakarahan", "imacat11", "Ada", "Karahan", "akarahan@ku.edu.tr", 2,yavrukedi);
		FreeTierUser ekin = new FreeTierUser("ekinonat", "88888888", "Ekin", "Onat", "eonat@ku.edu.tr", 20);
		// adding them to the environment so that, when they try to log in program will recognize them
		SignupFrame.addUserToLists(ece);
		SignupFrame.addUserToLists(okan);
		SignupFrame.addUserToLists(serife);
		SignupFrame.addUserToLists(zeynep);
		SignupFrame.addUserToLists(bobross);
		SignupFrame.addUserToLists(melek);
		SignupFrame.addUserToLists(ipek);
		SignupFrame.addUserToLists(kedim);
		SignupFrame.addUserToLists(ekin);
		SignupFrame.addUserToLists(admin);
		//sharing some images with their comments( optional descriptions)
		shareImageandComment(ece,gunbatimi,"");
		shareImageandComment(okan,bobRossPhoto,"Bob Ross is my favourite painter");
		shareImageandComment(ece,cicek34,"This image helped me a lot in my project.");
		shareImageandComment(okan,okanandMe,"Me and my girlfriend<3");
		shareImageandComment(zeynep, yavrukedi, "Here is my sweet cat");
		shareImageandComment(serife,lale,"beautiful tulips from Istanbul");
		shareImageandComment(bobross,bobRossPainting," Happy little trees");
		shareImageandComment(melek,saricicek,"");
		shareImageandComment(ipek,maviebru,"My first marbling trial :)");
		shareImageandComment(kedim,sumbul,"miav miav");
		shareImageandComment(ekin,frezya,"I bought these for my mom, aren't they beautiful?");
		shareImageandComment(admin,hanımeli,"Smell of them is so unique");
		new LoginFrame();
		

}
	// this method creates the images as labels so we can turn them into ImageandComment objects and share
	public static JLabel getImageasLabel(String imagePath) {
		ImageIcon icon = new ImageIcon(imagePath);
		JLabel label = new JLabel(icon);
		return label;
	}
	// This method creates an ImageandComment object and shares the object in the users profile and discover page
	public static void shareImageandComment(User user,JLabel label,String comment) {
		ImageandComment image= new ImageandComment(user,label,comment);
		user.sharePhoto(image);
	}
	}
