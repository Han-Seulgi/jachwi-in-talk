package com.example.project_test.Writing;

import android.graphics.Bitmap;

public class ThumbnailListData {
    int imgcode;
    String imgdata;
    Bitmap pbm;

    public ThumbnailListData(String imgdata) {
        this.imgdata = imgdata;
    }

    public ThumbnailListData(int imgcode, String imgdata) {
        this.imgcode = imgcode;
        this.imgdata = imgdata;
    }

    public ThumbnailListData(Bitmap pbm) {
        this.pbm = pbm;
    }

    public int getImgcode(){return imgcode;}

    public String getImgdata() {return imgdata;}

    public Bitmap getPbm() { return pbm; }

}
