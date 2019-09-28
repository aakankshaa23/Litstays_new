package com.dextroxd.sellvehicle.network.Login.model;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("authToken")
	private String authToken;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	public void setAuthToken(String authToken){
		this.authToken = authToken;
	}

	public String getAuthToken(){
		return authToken;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" +
			"authToken = '" + authToken + '\'' + 
			",name = '" + name + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}