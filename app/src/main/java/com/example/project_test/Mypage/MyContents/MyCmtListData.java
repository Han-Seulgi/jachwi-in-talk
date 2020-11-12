package com.example.project_test.Mypage.MyContents;

public class MyCmtListData {
    private int cmt_code;
    private String cmt_con, cmt_day;
    private String id;
    private String post_title, post_con, post_day;
    private int bcode;
    private int img;

    public MyCmtListData(int img, int cmt_code, String cmt_con, String cmt_day, String id, String post_title, String post_con, String post_day, int bcode) {
        this.img = img;
        this.cmt_code = cmt_code;
        this.cmt_con = cmt_con;
        this.cmt_day = cmt_day;
        this.id = id;
        this.post_title = post_title;
        this.post_con = post_con;
        this.post_day = post_day;
        this.bcode = bcode;
    }

    public int getImg() { return img; }
    public int getCmtcode() { return cmt_code; }
    public String getCmtcon() { return cmt_con; }
    public String getCmtday() { return cmt_day; }
    public String getId() { return id; }
    public String getPosttitle() { return post_title; }
    public String getPostcon() { return post_con; }
    public String getPostday() { return post_day; }
    public int getBoard() { return bcode; }

}
