package com.dextroxd.sellvehicle.network.PostProperty.model;

public class Response_Submit {
	private String msg;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	@Override
 	public String toString(){
		return 
			"Response_Submit{" +
			"msg = '" + msg + '\'' + 
			"}";
		}
}
