package com.example.project_test.Modify;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModifyCategoryData {
    @SerializedName("data")
    public List<Category> items;
}

class Category {
    @SerializedName("tag_name")
    public String tag;

}
