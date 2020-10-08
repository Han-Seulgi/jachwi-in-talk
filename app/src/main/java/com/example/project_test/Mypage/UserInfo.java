package com.example.project_test.Mypage;

import com.google.gson.annotations.SerializedName;

class UserInfo {
    @SerializedName("success")
    boolean success;
}

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