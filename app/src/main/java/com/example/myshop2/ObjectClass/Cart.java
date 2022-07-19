package com.example.myshop2.ObjectClass;

public class Cart {
    String thumbnail, title;
    int soluong, soLuongTorngKho, product_id, pre_id,price;

    public Cart(String thumbnail, String title, int price, int pre_id, int product_id, int soluong, int soLuongTrongKho) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.price = price;
        this.soluong = soluong;
        this.soLuongTorngKho = soLuongTrongKho;
        this.product_id = product_id;
        this.pre_id = pre_id;
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

    public int getPre_id() {
        return pre_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPre_id(int pre_id) {
        this.pre_id = pre_id;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getSoLuongTorngKho() {
        return soLuongTorngKho;
    }

    public void setSoLuongTorngKho(int soLuongTorngKho) {
        this.soLuongTorngKho = soLuongTorngKho;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
