package com.example.project_test.SharenRent;

public class SRListData {
    private int img;
    private String tabtitle, title, day, id, con;

    public SRListData(String tabtitle, int img, String title /*, String day, String id, String con*/) {
        this.tabtitle = tabtitle;
        this.img = img;
        this.title = title;
        //this.day = day;
        //this.id = id;
        //this.con = con;
    }

    public String getTabtitle() {
        return this.tabtitle;
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
}
