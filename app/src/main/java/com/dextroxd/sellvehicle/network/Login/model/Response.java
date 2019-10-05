package com.dextroxd.sellvehicle.network.Login.model;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("password")
	private String password;

	@SerializedName("name")
	private String name;

	@SerializedName("phone")
	private String phone;

	@SerializedName("email")
	private String email;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

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
			"password = '" + password + '\'' +
			",name = '" + name + '\'' +
			",phone = '" + phone + '\'' +
			",email = '" + email + '\'' + 
			"}";
		}



}