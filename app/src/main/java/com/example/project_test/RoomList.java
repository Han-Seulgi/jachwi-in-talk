package com.example.project_test;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomList {
    @SerializedName("data")
    List<RoomList> items;

    @SerializedName("room_lct")
    public String room_lct;

    public String toString() {
        return room_lct;
    }
}
