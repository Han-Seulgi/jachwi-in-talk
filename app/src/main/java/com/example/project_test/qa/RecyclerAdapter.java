package com.example.project_test.qa;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.qa.qaContent.qaActivityContent;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ImageView img;

    private String[] title = {"제목1", "제목2", "제목3", "제목4", "제목5", "제목6", "제목7", "제목8", "제목9", "제목10"}; // 게시물 제목 가져오기

    private String[] content = {"내용1", "내용2", "내용3", "내용4", "내용5", "내용6", "내용7", "내용8", "내용9", "내용10", }; //게시물 내용

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
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        RecyclerAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), qaActivityContent.class);
                intent.putExtra("제목", title[position]); //게시물의 제목
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
