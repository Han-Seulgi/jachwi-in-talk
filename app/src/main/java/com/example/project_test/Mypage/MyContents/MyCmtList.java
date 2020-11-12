package com.example.project_test.Mypage.MyContents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyCmtList {
    @SerializedName("data")
    List<CmtData> items;
}

class CmtData {
    @SerializedName("cmt_code")
    @Expose
    int cmt_code;
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("cmt_day")
    @Expose
    String cmt_day;
    @SerializedName("cmt_con")
    @Expose
    String cmt_con;
    @SerializedName("post_title")
    @Expose
    String post_title;
    @SerializedName("post_con")
    @Expose
    String post_con;
    @SerializedName("post_day")
    @Expose
    String post_day;
    @SerializedName("board_code")
    @Expose
    int bcode;

    @SerializedName("post_code")
    @Expose
    int post_code;

}
