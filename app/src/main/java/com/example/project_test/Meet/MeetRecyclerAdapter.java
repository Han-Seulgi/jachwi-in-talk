package com.example.project_test.Meet;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.Meet.MeetContent.MeetActivityContent;
import com.example.project_test.R;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class MeetRecyclerAdapter extends RecyclerView.Adapter<MeetRecyclerAdapter.MeetViewHolder> {
    private ArrayList<MeetListData> datas;

    public void setData(ArrayList<MeetListData> list){
        datas = list;
    }

    @NonNull
    @Override
    public MeetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent, false);

        MeetViewHolder holder = new MeetViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetViewHolder holder, int position) {
        MeetListData data = datas.get(position);

        holder.img.setImageResource(data.getImg());
        holder.count.setText(data.getCount());
        holder.title.setText(data.getTitle());
        final String t = data.getTitle();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MeetActivityContent.class);
                intent.putExtra("제목", t); //게시물의 제목
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MeetViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView count,title;

        public MeetViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            count = itemView.findViewById(R.id.count);
            title = itemView.findViewById(R.id.title);
        }
    }
}
