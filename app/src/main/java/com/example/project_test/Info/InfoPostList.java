package com.example.project_test.Info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoPostList {
    @SerializedName("data")
    public List<PostData> items;
}

