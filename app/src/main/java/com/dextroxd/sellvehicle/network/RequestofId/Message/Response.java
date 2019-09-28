package com.dextroxd.sellvehicle.network.RequestofId.Message;

public class Response{
	private String message;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}
