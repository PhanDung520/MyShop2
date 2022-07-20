package com.example.myshop2.ObjectClass;

import java.util.ArrayList;
import java.util.Date;

public class DonHang {
    String image, title;
    int order_id, totalprice;
    String date;
    ArrayList<Product> productArrayList;

    public DonHang() {
    }

    public DonHang(String image, String title, int order_id, int totalprice, Date date) {
        this.image = image;
        this.title = title;
        this.order_id = order_id;
        this.totalprice = totalprice;
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
}
