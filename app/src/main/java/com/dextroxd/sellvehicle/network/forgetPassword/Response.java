package com.dextroxd.sellvehicle.network.forgetPassword;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("email")
	private String email;

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"email = '" + email + '\'' + 
			"}";
		}
}