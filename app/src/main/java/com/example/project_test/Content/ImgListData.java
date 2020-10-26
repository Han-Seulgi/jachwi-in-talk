package com.example.project_test.Content;

public class ImgListData {
    int imgcode;
    String imgdata;
    public ImgListData(String imgdata) {
        this.imgdata = imgdata;
    }

    public ImgListData(int imgcode, String imgdata) {
        this.imgcode = imgcode;
        this.imgdata = imgdata;
    }

    public int getImgcode(){return imgcode;}

    public String getImgdata() {return imgdata;}
}
