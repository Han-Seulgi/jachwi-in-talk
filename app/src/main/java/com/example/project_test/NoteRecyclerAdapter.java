package com.example.project_test;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Content.ContentWithPicture;


import java.util.ArrayList;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder> {
    private ArrayList<NoteListData> datas;

    public void setData(ArrayList<NoteListData> list) {datas = list;}

    @NonNull
    @Override
    public NoteRecyclerAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent, false);

        NoteRecyclerAdapter.NoteViewHolder holder = new NoteRecyclerAdapter.NoteViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerAdapter.NoteViewHolder holder, final int position) {
        NoteListData data = datas.get(position);

        final String sid = data.getSid();
        final String con = data.getCon();
        final String day = data.getDay();

        holder.sendid.setText(sid);
        holder.con.setText(con);
        holder.day.setText(day);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView sendid, con, day;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            sendid = itemView.findViewById(R.id.sendid);
            con = itemView.findViewById(R.id.con);
            day = itemView.findViewById(R.id.day);
        }
    }
}
