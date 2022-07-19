package com.example.myshop2.ObjectClass;

public class Pre_order {
    int id,user_id,product_id,num,totalPrice;
    String thumbnail, title;

    public Pre_order(int id, int user_id, int product_id, int num) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.num = num;
    }

    public Pre_order(int id, int user_id, int product_id, int num, int totalPrice, String thumbnail, String title) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.num = num;
        this.totalPrice = totalPrice;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
