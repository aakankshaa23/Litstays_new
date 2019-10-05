package com.dextroxd.sellvehicle.network.PostOfSearch;

public class Response{
	private int maxArea;
	private int minPrice;
	private String name;
	private boolean bachelorsAllowed;
	private int maxPrice;
	private String location;
	private int buildYear;
	private int bedroom;

	public void setFurnishing(int furnishing) {
		this.furnishing = furnishing;
	}

	public int getFurnishing() {
		return furnishing;
	}

	private int furnishing;
	private int minArea;

	public void setMaxArea(int maxArea){
		this.maxArea = maxArea;
	}

	public int getMaxArea(){
		return maxArea;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMinPrice(int minPrice){
		this.minPrice = minPrice;
	}

	public int getMinPrice(){
		return minPrice;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setBachelorsAllowed(boolean bachelorsAllowed){
		this.bachelorsAllowed = bachelorsAllowed;
	}

	public boolean isBachelorsAllowed(){
		return bachelorsAllowed;
	}

	public void setMaxPrice(int maxPrice){
		this.maxPrice = maxPrice;
	}

	public int getMaxPrice(){
		return maxPrice;
	}

	public void setBuildYear(int buildYear){
		this.buildYear = buildYear;
	}

	public int getBuildYear(){
		return buildYear;
	}

	public void setBedroom(int bedroom){
		this.bedroom = bedroom;
	}

	public int getBedroom(){
		return bedroom;
	}

	public void setMinArea(int minArea){
		this.minArea = minArea;
	}

	public int getMinArea(){
		return minArea;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"maxArea = '" + maxArea + '\'' + 
			",minPrice = '" + minPrice + '\'' + 
			",name = '" + name + '\'' + 
			",bachelorsAllowed = '" + bachelorsAllowed + '\'' + 
			",maxPrice = '" + maxPrice + '\'' + 
			",buildYear = '" + buildYear + '\'' + 
			",bedroom = '" + bedroom + '\'' + 
			",minArea = '" + minArea + '\'' + 
			"}";
		}
}
