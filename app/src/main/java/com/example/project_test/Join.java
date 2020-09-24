package com.example.project_test;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

class ValidateID {
    @SerializedName("newID")
    boolean newID;
}

class ValidateName {
    @SerializedName("newName")
    boolean newName;
}

class ValidateEmail {
    @SerializedName("newEmail")
    boolean newEmail;
}

class Join {
    @SerializedName("success")
    boolean success;
}