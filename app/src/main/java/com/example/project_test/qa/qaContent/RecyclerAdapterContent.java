package com.example.project_test.qa.qaContent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

public class RecyclerAdapterContent extends RecyclerView.Adapter<RecyclerAdapterContent.ViewHolder>{
    ImageView img;
    private String[] title = {"아마도 그럴거에요", "아닐걸요??", "인터넷 블로그에 자세히 나와있어요!!", "아마도 그럴거에요", "아닐걸요??", "아닐걸요??", "인터넷 블로그에 자세히 나와있어요!!", "경비아저씨한테 한번 물어보세요", "경비아저씨한테 한번 물어보세요", "경비아저씨한테 한번 물어보세요"}; // 게시물 제목 가져오기

    private String[] content = {"2020.6.22", "2020.6.22", "2020.6.22", "2020.6.19", "2020.6.19", "2020.6.15", "2020.6.15", "2020.6.10", "2020.6.10", "2020.6.1", }; //게시물 내용

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
    public RecyclerAdapterContent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        RecyclerAdapterContent.ViewHolder viewHolder = new RecyclerAdapterContent.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterContent.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
