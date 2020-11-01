package com.example.project_test.Meet.MeetContent;

import com.google.gson.annotations.SerializedName;

public class MeetList {
    @SerializedName("tag_name")
    public String tag;

    @SerializedName("meet_lct")
    public String lct;

    @SerializedName("meet_p")
    public int pnum;

    @SerializedName("meet_day")
    public String day;
}
