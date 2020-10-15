package com.example.project_test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularPost {
    @SerializedName("popular")
    List<PopularData> items;
}

class PopularData{
    @SerializedName("post_title")
    @Expose
    String post_title;

    @SerializedName("post_con")
    @Expose
    String post_con;

    public String toString() {
        return "PostData{" +
                "post_title='" + post_title + '\'' +
                ", post_con='" + post_con + '\'' +
                '}';
    }
}