package com.example.project_test.Mypage.MyContents;

public class MyListData {
    private int img, board;
    private String title, day, id, con;

    public MyListData(int img, String title, int board, String day, String id, String con) {
        this.img = img;
        this.title = title;
        this.board = board;
        this.day = day;
        this.id = id;
        this.con = con;
    }

    public int getImg() {
        return this.img;
    }

    public String getTitle() {
        return this.title;
    }

    public int getBoard() {
        return this.board;
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