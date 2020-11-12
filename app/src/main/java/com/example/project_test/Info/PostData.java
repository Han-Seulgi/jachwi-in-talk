package com.example.project_test.Info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostData {
    @SerializedName("post_code")
    @Expose
    public int post_code;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("post_title")
    @Expose
    public String post_title;
    @SerializedName("post_con")
    @Expose
    public String post_con;
    @SerializedName("post_day")
    @Expose
    public String post_day;
    @SerializedName("board_code")
    @Expose
    public int board_code;

    @Override
    public String toString() {
        return "PostData{" +
                "post_code='" + post_code + '\'' +
                ", id='" + id + '\'' +
                ", post_title='" + post_title + '\'' +
                ", post_con='" + post_con + '\'' +
                ", post_day='" + post_day + '\'' +
                ", board_code='" + board_code + '\'' +
                '}';
    }
}
