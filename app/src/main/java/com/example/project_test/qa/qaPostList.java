package com.example.project_test.qa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class qaPostList {
    @SerializedName("data")
    List<PostData> items;
}

class PostData {
    @SerializedName("post_code")
    @Expose
    int post_code;
    @SerializedName("id")
    @Expose
    String id;
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
    int board_code;

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