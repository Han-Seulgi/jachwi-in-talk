package com.example.project_test.SharenRent;

public class RentListData {
    private int img;
    private String title, day, id, con, price, term;

    public RentListData(int img, String title , String price, String term /*, String day, String id, String con*/) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.term = term;
        //this.day = day;
        //this.id = id;
        //this.con = con;
    }

    public int getImg() {
        return this.img;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDay() {
        return this.day;
    }

    public String getId() {
        return this.id;
    }

    public String getCon() {
        return this.con;
    }

    public String getPrice() {
        return this.price;
    }

    public String getTerm() {
        return this.term;
    }
}
