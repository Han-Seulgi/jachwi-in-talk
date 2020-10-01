package com.example.project_test.Info;

public class InfoListData {
    private int img;
    private String title, day, id;

    public InfoListData(int img, String title, String day, String id) {
        this.img = img;
        this.title = title;
        this.day = day;
        this.id = id;
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
}
