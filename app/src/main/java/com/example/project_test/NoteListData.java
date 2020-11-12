package com.example.project_test;

public class NoteListData {
    private String sid, con, day;

    public NoteListData(String sid, String con, String day) {
        this.sid = sid;
        this.con = con;
        this.day = day;
    }

    public String getSid() {
        return this.sid;
    }

    public String getCon() {return this.con;}

    public String getDay() {
        return this.day;
    }
}
