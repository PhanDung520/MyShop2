package com.example.myshop2.ObjectClass;

public class MostViewClass {
    String image;
    String title, desc;
    int rating;
    int id;

    public MostViewClass(String image, String title, String desc, int rating, int id) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
