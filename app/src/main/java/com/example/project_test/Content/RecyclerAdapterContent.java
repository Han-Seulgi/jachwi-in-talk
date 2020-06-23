package com.example.project_test.Content;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

public class RecyclerAdapterContent extends RecyclerView.Adapter<com.example.project_test.Content.RecyclerAdapterContent.ViewHolder>{
    ImageView img;
    private String[] title = {"우와 완전 요리 고수네요", "오늘 저녁은 이걸로 해야겠어요 ㅎㅎ", "이 요리는 하는 데 시간이 얼마나 걸릴까요?", "생각보다 재료가 간단하네요!!", "생각보다 재료가 간단하네요!!", "이 요리는 하는 데 시간이 얼마나 걸릴까요?", "요리 고수네요", "혼자 해먹기 딱 좋은 요리인거같아요", "오늘 저녁은 이거!", "집가서 만들어먹어야겠어요^^"}; // 게시물 제목 가져오기
    private String[] content = {"2020.6.23", "2020.6.23", "2020.6.20", "2020.6.20", "2020.6.19", "2020.6.19", "2020.6.10", "2020.6.10", "2020.6.8", "2020.6.5", }; //게시물 내용

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
    public com.example.project_test.Content.RecyclerAdapterContent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        com.example.project_test.Content.RecyclerAdapterContent.ViewHolder viewHolder = new com.example.project_test.Content.RecyclerAdapterContent.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final com.example.project_test.Content.RecyclerAdapterContent.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}