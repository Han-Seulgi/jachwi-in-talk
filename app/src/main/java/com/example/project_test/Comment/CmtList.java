package com.example.project_test.Comment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CmtList {
    @SerializedName("data")
    public List<CmtData> items;

    @SerializedName("qadata")
    public List<qnaCmtData> items2;
}

