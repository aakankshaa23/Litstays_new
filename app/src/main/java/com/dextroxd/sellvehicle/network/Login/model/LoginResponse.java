package com.dextroxd.sellvehicle.network.Login.model;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("authToken")
	private String authToken;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("_id")
	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

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
					",_id = '"+id+'\''+
			"}";
		}
}