package com.example.project_test.Mypage.MyContents;

public class MyListData {
    private int img, code;
    private String title, board, day, id, con;

    public MyListData(int img, String title, String board, String day, String id, String con) {
        this.img = img;
        this.title = title;
        this.board = board;
        this.day = day;
        this.id = id;
        this.con = con;
    }

    public MyListData(int img, String title, String board, String day, String id, String con, int code) {
        this.img = img;
        this.title = title;
        this.board = board;
        this.day = day;
        this.id = id;
        this.con = con;
        this.code = code;
    }

    public int getImg() {
        return this.img;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBoard() {
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

    public int getCode() { return this.code; }
}