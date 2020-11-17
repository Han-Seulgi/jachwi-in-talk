package com.example.project_test.Room;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomList {
    @SerializedName("data")
    public List<RoomList> items;

    @SerializedName("room_lct")
    public String room_lct;

    @SerializedName("id")
    public String id;

    public String toString() {
        return room_lct + " / " + id;
    }
}



