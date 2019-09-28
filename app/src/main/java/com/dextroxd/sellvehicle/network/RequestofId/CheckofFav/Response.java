package com.dextroxd.sellvehicle.network.RequestofId.CheckofFav;

public class Response{
	private boolean fav;

	public void setFav(boolean fav){
		this.fav = fav;
	}

	public boolean isFav(){
		return fav;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"fav = '" + fav + '\'' + 
			"}";
		}
}
