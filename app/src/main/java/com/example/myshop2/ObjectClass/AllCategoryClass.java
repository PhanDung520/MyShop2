package com.example.myshop2.ObjectClass;

public class AllCategoryClass {
    String image;
    String title, desc;
    int rating,id;

    public AllCategoryClass(String image, String title, String desc, int rating,int id) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
