package com.example.project_test.Recipe;

public class RecipeListData {
    private int img, code;
    private String title, day, id, con;
    private String tabTitle;
    String img_data;

    public RecipeListData(int img, String title, String day, String id, String tabTitle, String con) {
        this.img = img;
        this.title = title;
        this.day = day;
        this.id = id;
        this.tabTitle = tabTitle;
        this.con = con;
        this.code = code;
    }

    /*public RecipeListData(int img, String title, String day, String id, String tabTitle, String con, int code) {
        this.img = img;
        this.title = title;
        this.day = day;
        this.id = id;
        this.tabTitle = tabTitle;
        this.con = con;
        this.code = code;
    }*/

    public RecipeListData(String img_data, String title, String day, String id, String tabTitle, String con) {
        this.img_data = img_data;
        this.title = title;
        this.day = day;
        this.id = id;
        this.tabTitle = tabTitle;
        this.con = con;
    }

    public String getImg_data() { return img_data; }

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

    public String getTabTitle() {return this.tabTitle;}

    public String getCon() {return this.con;}

    public int getCode() {return this.code;}
}
