package com.example.project_test.Writing;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WritingCategoryData {
    @SerializedName("data")
    public List<Category> items;
}

class Category {
    @SerializedName("tag_name")
    public String tag;
}

