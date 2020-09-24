package com.example.project_test;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class UserFeed{
    @SerializedName("data")
    List<User> items;
}

class User {

    @SerializedName("id")
    String id;

    @SerializedName("password")
    String password;

    @NonNull
    @Override
    public String toString() {
        return id + " / " + password;
    }


}
