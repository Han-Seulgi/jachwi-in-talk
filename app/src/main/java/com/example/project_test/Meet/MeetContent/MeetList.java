package com.example.project_test.Meet.MeetContent;

import com.google.gson.annotations.SerializedName;

public class MeetList {
    @SerializedName("meet_tag")
    public int tag;

    @SerializedName("meet_lct")
    public String lct;

    @SerializedName("meet_p")
    public int pnum;

    @SerializedName("meet_day")
    public String day;
}
