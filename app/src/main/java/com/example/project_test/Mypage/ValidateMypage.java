package com.example.project_test.Mypage;

import com.google.gson.annotations.SerializedName;

public class ValidateMypage {
    @SerializedName("newName")
    boolean newName;

    @SerializedName("newEmail")
    boolean newEmail;

    @SerializedName("update")
    boolean updateInfo;

    @SerializedName("delete")
    boolean delete;

}
