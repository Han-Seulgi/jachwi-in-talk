package com.example.project_test.SharenRent;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryData {
    @SerializedName("data")
    public List<Category> items;
}

class Category {
    @SerializedName("tag_name")
    public String tag;
}
