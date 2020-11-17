package com.example.project_test.Room;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Room {
    @SerializedName("insert")
    public boolean insert ;

    @SerializedName("room")
    List<RoomList> items;
}

