package com.example.project_test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Note {
    @SerializedName("data")
    public List<NoteData> items;
}

class NoteData {
    @SerializedName("note_code")
    @Expose
    public int note_code;
    @SerializedName("sid")
    @Expose
    public String sid;
    @SerializedName("rid")
    @Expose
    public String rid;
    @SerializedName("note_con")
    @Expose
    public String note_con;
    @SerializedName("note_day")
    @Expose
    public String note_day;
    @SerializedName("note_ck")
    @Expose
    public int note_ck;
}

class NewNote {
    @SerializedName("newNote")
    boolean newNote;
}

class CheckNote {
    @SerializedName("checknote")
    boolean checknote;
}