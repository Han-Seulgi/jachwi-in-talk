package com.example.project_test.Recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostData {
    @SerializedName("post_code")
    @Expose
    public int pcode;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("post_title")
    @Expose
    public String title;
    @SerializedName("post_con")
    @Expose
    public String con;
    @SerializedName("post_day")
    @Expose
    public String day;
    @SerializedName("board_code")
    @Expose
    public int bcode;


    @Override
    public String toString() {
        return "PostData{" +
                "pcode=" + pcode +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", con='" + con + '\'' +
                ", day='" + day + '\'' +
                ", bcode=" + bcode +
                '}';
    }
}
