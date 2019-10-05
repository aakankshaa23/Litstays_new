package com.dextroxd.sellvehicle.network.GetDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;
public class Response{

	@SerializedName("favorites")
	private List<String> favorites;

	@SerializedName("gender")
	private String gender;

	@SerializedName("profilePic")
	private String profilePic;

	@SerializedName("authToken")
	private String authToken;

	@SerializedName("verified")
	private boolean verified;

	@SerializedName("verificationToken")
	private String verificationToken;

	@SerializedName("resetPasswordToken")
	private Object resetPasswordToken;

	@SerializedName("password")
	private String password;

	@SerializedName("resetPasswordExpires")
	private Object resetPasswordExpires;

	@SerializedName("phone")
	private long phone;

	@SerializedName("__v")
	private int V;

	@SerializedName("name")
	private String name;

	@SerializedName("postings")
	private List<String> postings;

	@SerializedName("_id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("age")
	private int age;

	public void setFavorites(List<String> favorites){
		this.favorites = favorites;
	}

	public List<String> getFavorites(){
		return favorites;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setProfilePic(String profilePic){
		this.profilePic = profilePic;
	}

	public String getProfilePic(){
		return profilePic;
	}

	public void setAuthToken(String authToken){
		this.authToken = authToken;
	}

	public String getAuthToken(){
		return authToken;
	}

	public void setVerified(boolean verified){
		this.verified = verified;
	}

	public boolean isVerified(){
		return verified;
	}

	public void setVerificationToken(String verificationToken){
		this.verificationToken = verificationToken;
	}

	public String getVerificationToken(){
		return verificationToken;
	}

	public void setResetPasswordToken(Object resetPasswordToken){
		this.resetPasswordToken = resetPasswordToken;
	}

	public Object getResetPasswordToken(){
		return resetPasswordToken;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setResetPasswordExpires(Object resetPasswordExpires){
		this.resetPasswordExpires = resetPasswordExpires;
	}

	public Object getResetPasswordExpires(){
		return resetPasswordExpires;
	}

	public void setPhone(long phone){
		this.phone = phone;
	}

	public long getPhone(){
		return phone;
	}

	public void setV(int V){
		this.V = V;
	}

	public int getV(){
		return V;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPostings(List<String> postings){
		this.postings = postings;
	}

	public List<String> getPostings(){
		return postings;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"favorites = '" + favorites + '\'' + 
			",gender = '" + gender + '\'' + 
			",profilePic = '" + profilePic + '\'' + 
			",authToken = '" + authToken + '\'' + 
			",verified = '" + verified + '\'' + 
			",verificationToken = '" + verificationToken + '\'' + 
			",resetPasswordToken = '" + resetPasswordToken + '\'' + 
			",password = '" + password + '\'' + 
			",resetPasswordExpires = '" + resetPasswordExpires + '\'' + 
			",phone = '" + phone + '\'' + 
			",__v = '" + V + '\'' + 
			",name = '" + name + '\'' + 
			",postings = '" + postings + '\'' + 
			",_id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",age = '" + age + '\'' + 
			"}";
		}
}