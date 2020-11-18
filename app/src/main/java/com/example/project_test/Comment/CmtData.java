package com.example.project_test.Comment;

import com.google.gson.annotations.SerializedName;

public class CmtData {
    @SerializedName("cmt_code")
    public int cmt_code;
    @SerializedName("id")
    public String id;
    @SerializedName("post_code")
    public int post_code;
    @SerializedName("cmt_con")
    public String cmt_con;
    @SerializedName("cmt_day")
    public String cmt_day;

    @Override
    public String toString() {
        return "CmtData{" +
                "cmt_code=" + cmt_code +
                ", id='" + id + '\'' +
                ", post_code=" + post_code +
                ", cmt_con='" + cmt_con + '\'' +
                ", cmt_day='" + cmt_day + '\'' +
                '}';
    }
}
