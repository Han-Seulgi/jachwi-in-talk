package com.example.project_test.Meet;

public class MeetListData {
    private int img;
    private int count;
    private String title;

    public MeetListData (int img, int count, String title) {
        this.img = img;
        this.count = count;
        this.title = title;
    }

    public int getImg() {
        return this.img;
    }
    public String getCount() {
        String cnt = Integer.toString(this.count);
        return cnt;
    }
    public String getTitle() {
        return  this.title;
    }
}
