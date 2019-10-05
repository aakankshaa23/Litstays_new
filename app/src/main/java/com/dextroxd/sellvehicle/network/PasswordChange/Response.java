package com.dextroxd.sellvehicle.network.PasswordChange;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("password")
	private String password;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"password = '" + password + '\'' + 
			"}";
		}
}