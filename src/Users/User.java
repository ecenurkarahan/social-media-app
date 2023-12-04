package Users;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import photoReviewandShare.ImageandComment;

public  class User {
	protected final String nickName;
	private  String password;
	protected String realName;
	protected String surname;
	private final String email;
	protected int age;
	private boolean isadministator;
	private ArrayList<ImageandComment> usersallPhotos= new ArrayList<ImageandComment>();
	protected JLabel profilePhoto;
	public User(String nickName, String password, String realName, String surname, String email, int age, JLabel photo) {
		this.nickName = nickName;
		this.password = password;
		this.realName = realName;
		this.surname = surname;
		this.email = email;
		this.age = age;
		this.profilePhoto= photo;
		this.isadministator= false;
	}
	public User(String nickName, String password, String realName, String surname, String email, int age) {
		this.nickName = nickName;
		this.password = password;
		this.realName = realName;
		this.surname = surname;
		this.email = email;
		this.age = age;
		String path = "C:/Users/karah/eclipse-workspace/EceNurKarahanProgrammingProject/src/imageRetievingandProcessing/image/anonimprofil.jpg";
		ImageIcon profile = new ImageIcon(path);
		JLabel label = new JLabel(profile);
		this.profilePhoto = label;
	}
	public boolean isAdmin() {
		return false;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public JLabel getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(JLabel profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public String getNickName() {
		return nickName;
	}
	public String getEmail() {
		return email;
	}
	public void addPhoto(ImageandComment image) {
		this.usersallPhotos.add(image);
	}
	public void removePhoto(ImageandComment image) {
		this.usersallPhotos.remove(image);
	}
	public void sharePhoto(ImageandComment sharedImage) {
		this.usersallPhotos.add(sharedImage);
	}
	public ArrayList<ImageandComment> getUsersallPhotos() {
		return usersallPhotos;
	}
	public void setUsersallPhotos(ArrayList<ImageandComment> usersallPhotos) {
		this.usersallPhotos = usersallPhotos;
	}
	public JLabel grayscale(String imageNameextension, double filterLevel) throws IOException{
		return null;
	}
	public  JLabel edgeDetection(String imageNameextension, double filterLevel) throws IOException{
		return null;
	}public JLabel contrast(String imageNameextension, double filterLevel) throws IOException{
		return null;
	}public JLabel brightness(String imageNameextension, double filterLevel) throws IOException{
		return null;
	}public  JLabel sharpening(String imageNameextension, double filterLevel) throws IOException{
		return null;
	}public  JLabel blurring(String imageNameextension, double filterLevel) throws IOException{
		return null;
	}

}
