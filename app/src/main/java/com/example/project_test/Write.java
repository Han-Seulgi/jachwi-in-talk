package com.example.project_test;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Write {


    @SerializedName("post_code")
    int post_code;

    @SerializedName("id")
    String id;

    @SerializedName("post_title")
    String post_title;

    @SerializedName("post_con")
    String post_con;

    @SerializedName("board_code")
    int board_code;



    @NonNull
    @Override

    public String toString() {
        return  id + "/" + post_title + " / " + post_con ;
    }


    public int getPost_code() {
        return post_code;
    }
    public int getBoard_code(){
        return  board_code;
    }







}



