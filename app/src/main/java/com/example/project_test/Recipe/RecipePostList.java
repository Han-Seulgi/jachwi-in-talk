package com.example.project_test.Recipe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePostList {
    @SerializedName("data")
    public List<PostData> items;

    @SerializedName("post_code")
    public int pcode;
}



