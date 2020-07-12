package com.erp.distribution.sfa.model;

/**
 * Created by Azhar Rivaldi on 01/12/2019.
 */

public class CategoryModel {
    public int catid, icres;
    public String title, img;
    public boolean isBig;

    public CategoryModel(int catid, int icres, String title, String img, boolean isBig) {
        this.catid = catid;
        this.icres = icres;
        this.title = title;
        this.img   = img;
        this.isBig = isBig;
    }
}
