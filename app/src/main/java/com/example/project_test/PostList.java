package com.example.project_test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PostList {

    @SerializedName("post_code")
    public int pcode;

    @SerializedName("id")
    public String id;

    @SerializedName("post_con")
    public String con;

    @SerializedName("post_day")
    public String day;

    @SerializedName("board_code")
    public int bcode;
}
