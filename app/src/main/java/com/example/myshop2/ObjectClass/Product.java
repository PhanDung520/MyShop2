package com.example.myshop2.ObjectClass;

public class Product {
    int id,soluong,category_id,rate,price;
    String title, thumbnail, description;

    public Product(int id, int soluong, int category_id, int rate, int price, String title, String thumbnail, String description) {
        this.id = id;
        this.soluong = soluong;
        this.category_id = category_id;
        this.rate = rate;
        this.price = price;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
