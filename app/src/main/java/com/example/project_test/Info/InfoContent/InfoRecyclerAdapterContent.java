package com.example.project_test.Info.InfoContent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

public class InfoRecyclerAdapterContent extends RecyclerView.Adapter<InfoRecyclerAdapterContent.ViewHolder>{
    ImageView img;
    private String[] title = {"aaa", "b", "c", "d", "e", "제목6", "제목7", "제목8", "제목9", "제목10"}; // 게시물 제목 가져오기

    private String[] content = {"11111", "2", "3", "4", "5", "내용6", "내용7", "내용8", "내용9", "내용10", }; //게시물 내용

    public class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView2;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.img);
            this.textView = view.findViewById(R.id.tv1);
            this.textView2 = view.findViewById(R.id.tv2);
        }
    }

    @Override
    public InfoRecyclerAdapterContent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        InfoRecyclerAdapterContent.ViewHolder viewHolder = new InfoRecyclerAdapterContent.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InfoRecyclerAdapterContent.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
