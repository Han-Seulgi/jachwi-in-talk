package com.example.project_test.Comment;

public class CommentListData {
    private int code;
    private String id, con, day;
    private int like;

    public CommentListData(int code, String id, String con, String day) {
        this.code = code;
        this.id = id;
        this.con = con;
        this.day = day;
    }

    public CommentListData(int code, String id, String con, String day, int like){
        this.code = code;
        this.id = id;
        this.con = con;
        this.day = day;
        this.like = like;
    }

    public int getCode() {
        return this.code;
    }

    public String getId() {
        return this.id;
    }
    public String getCon() {return this.con;}

    public String getDay() {
        return this.day;
    }

    public int getLike() {return this.like;}

}
