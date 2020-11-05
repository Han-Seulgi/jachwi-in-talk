package com.example.project_test;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Write {
    @SerializedName("data")
    public List<PostList> items;

    @SerializedName("post_code")
    public int pcode;

    @Override
    public String toString() {
        return "Write{" +
                "items=" + items +
                ", pcode=" + pcode +
                '}';
    }
}
