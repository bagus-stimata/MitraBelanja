package com.erp.distribution.sfa.model;

/**
 * Created by Azhar Rivaldi on 01/12/2019.
 */

public class ProductModel {
    public String name, store, img;
    public long price, price_old;
    public int discount;
    public float rating;

    public ProductModel(String name, String store, long price, long price_old, String img, float rating, int discount) {
        this.name = name;
        this.store = store;
        this.price = price;
        this.price_old = price_old;
        this.img = img;
        this.rating = rating;
        this.discount = discount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPrice_old() {
        return price_old;
    }

    public void setPrice_old(long price_old) {
        this.price_old = price_old;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
