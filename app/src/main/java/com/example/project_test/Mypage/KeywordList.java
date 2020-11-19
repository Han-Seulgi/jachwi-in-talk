package com.example.project_test.Mypage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeywordList {
    @SerializedName("data")
    List<KeywordData> items;

    @SerializedName("update")
    @Expose
    Boolean update;

    @SerializedName("checkpost")
    @Expose
    public Boolean checkpost;
}

class KeywordData {
    @SerializedName("keyword")
    @Expose
    String keyword;
}