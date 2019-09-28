package com.dextroxd.sellvehicle.exploreFragment.model_explore;

public class ModelCard {
     String imageUrl;
    String Cost;
    // String title;
    // boolean like;
    String Bedroom;
    String Furnishing;
    Boolean like;

  public ModelCard(String cost,String bedroom,String furnishing,String imageUrl) {
      this.imageUrl = imageUrl;
      Cost = cost;
      // this.title = title;
      // this.like = like;
      Bedroom = bedroom;
      Furnishing = furnishing;
      //this.like=like;
  }
    //}

//    public Boolean getLike() {
//        return like;
//    }
//
//    public void setLike(Boolean like) {
//        this.like = like;
//    }

    //  public String getImageUrl() {
    //    return imageUrl;
    // }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCost() {
        return Cost;

    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getBedroom() {
        return Bedroom;
    }

    public void setBedroom(String bedroom) {
        Bedroom = bedroom;
    }

    public String getFurnishing() {
        return Furnishing;
    }

    public void setFurnishing(String furnishing) {
        Furnishing = furnishing;
    }
}
