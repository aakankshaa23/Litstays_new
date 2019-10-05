package com.dextroxd.sellvehicle.network.ModelSubmitforChangeofDetails;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("phone")
	private long phone;

	@SerializedName("name")
	private String name;

	public void setPhone(long phone){
		this.phone = phone;
	}

	public long getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}