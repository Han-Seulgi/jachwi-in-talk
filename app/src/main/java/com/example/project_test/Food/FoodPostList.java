package com.example.project_test.Food;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodPostList {
    @SerializedName("data")
    public List<PostData> items;
}

