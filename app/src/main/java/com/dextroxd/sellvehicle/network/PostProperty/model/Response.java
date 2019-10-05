package com.dextroxd.sellvehicle.network.PostProperty.model;

import java.util.ArrayList;
import java.util.List;

public class Response{
	private boolean parking;
	private int area;
	private ArrayList<String> images = new ArrayList<>();
	private String listedBy;
	private boolean bachelorsAllowed;
	private String description;
	private String facing;
	private String _id;
	private int buildYear;
	private String location;
	private String type;
	private int bedroom;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private int floors;
	private int furnished;
	private int price;
	private int V;
	private String name;
//	private String id;
	private int bathroom;

	public void setParking(boolean parking){
		this.parking = parking;
	}

	public boolean isParking(){
		return parking;
	}

	public void setArea(int area){
		this.area = area;
	}

	public int getArea(){
		return area;
	}

	public void setImages(ArrayList<String> images){
		this.images = images;
	}

	public ArrayList<String> getImages(){
		return images;
	}

	public void setListedBy(String listedBy){
		this.listedBy = listedBy;
	}

	public String getListedBy(){
		return listedBy;
	}

	public void setBachelorsAllowed(boolean bachelorsAllowed){
		this.bachelorsAllowed = bachelorsAllowed;
	}

	public boolean isBachelorsAllowed(){
		return bachelorsAllowed;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setFacing(String facing){
		this.facing = facing;
	}

	public String getFacing(){
		return facing;
	}

	public void setBuildYear(int buildYear){
		this.buildYear = buildYear;
	}

	public int getBuildYear(){
		return buildYear;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setBedroom(int bedroom){
		this.bedroom = bedroom;
	}

	public int getBedroom(){
		return bedroom;
	}

	public String get_id() {
		return _id;
	}

	public int getFurnished() {
		return furnished;
	}

	public void setFloors(int floors){
		this.floors = floors;
	}

	public int getFloors(){
		return floors;
	}

	public void setFurnished(int furnished){
		this.furnished = furnished;
	}

	public int isFurnished(){
		return furnished;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setV(int V){
		this.V = V;
	}

	public int getV(){
		return V;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
//
//	public void setId(String id){
//		this.id = id;
//	}
//
//	public String getId(){
//		return id;
//	}

	public void setBathroom(int bathroom){
		this.bathroom = bathroom;
	}

	public int getBathroom(){
		return bathroom;
	}

	@Override
 	public String toString(){
		return 
			"Response_Submit{" +
			"parking = '" + parking + '\'' + 
			",area = '" + area + '\'' + 
			",images = '" + images + '\'' + 
			",listedBy = '" + listedBy + '\'' + 
			",bachelorsAllowed = '" + bachelorsAllowed + '\'' + 
			",description = '" + description + '\'' + 
			",facing = '" + facing + '\'' + 
			",buildYear = '" + buildYear + '\'' + 
			",type = '" + type + '\'' + 
			",bedroom = '" + bedroom + '\'' + 
			",floors = '" + floors + '\'' + 
			",furnished = '" + furnished + '\'' + 
			",price = '" + price + '\'' + 
			",__v = '" + V + '\'' + 
			",name = '" + name + '\'' + 
			",_id = '" + _id + '\'' +
			",bathroom = '" + bathroom + '\'' + 
			"}";
		}
}