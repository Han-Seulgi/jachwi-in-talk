package com.example.project_test.Emergency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MsgNumList {
    @SerializedName("data")
    List<MsgNumData> items;

    @SerializedName("update")
    @Expose
    Boolean update;
}

class MsgNumData {
    @SerializedName("msg_num")
    @Expose
    String msgnum;

    @SerializedName("call_num")
    @Expose
    String callnum;

    @SerializedName("sys_sensor")
    @Expose
    int syssensor;

    @SerializedName("sys_volume")
    @Expose
    int sysvolume;
}

