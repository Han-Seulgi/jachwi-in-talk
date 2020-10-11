package com.example.project_test;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class UserFeed{
    @SerializedName("data")
    List<User> items;
}

class UserIdCheck{
    @SerializedName("checkid")
    boolean ckid;

}

public class User {

    @SerializedName("id")
    String id;

    @SerializedName("password")
    public String password;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @NonNull
    @Override
    public String toString() {
        return id + " / " + password;
    }


}
