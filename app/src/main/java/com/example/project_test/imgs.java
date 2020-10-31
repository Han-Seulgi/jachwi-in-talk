package com.example.project_test;

import com.google.gson.annotations.SerializedName;

public class imgs {
    @SerializedName("img_code")
    public
    int img_code;

    @SerializedName("img_data")
    public
    String img_data;

    @Override
    public String toString() {
        return "imgs{" +
                "img_data='" + img_data + '\'' +
                '}';
    }
}
