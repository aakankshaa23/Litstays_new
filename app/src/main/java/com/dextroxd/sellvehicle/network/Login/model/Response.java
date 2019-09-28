package com.dextroxd.sellvehicle.network.Login.model;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("password")
	private String password;

	@SerializedName("name")
	private String name;

	@SerializedName("password2")
	private String password2;

	@SerializedName("email")
	private String email;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPassword2(String password2){
		this.password2 = password2;
	}

	public String getPassword2(){
		return password2;
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
			"password = '" + password + '\'' +
			",name = '" + name + '\'' +
			",password2 = '" + password2 + '\'' +
			",email = '" + email + '\'' + 
			"}";
		}



}