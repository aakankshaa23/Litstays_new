package com.dextroxd.sellvehicle.network.RequestofId;

public class Response{
	private String id;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"id = '" + id + '\'' + 
			"}";
		}
}
