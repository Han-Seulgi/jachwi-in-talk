package com.example.project_test.Mypage.MyContents;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.qa.qaContent.qaActivityContent;

public class MyContentsRecyclerViewAdapter extends RecyclerView.Adapter<MyContentsRecyclerViewAdapter.ViewHolder> {
    ImageView img;

    private String[] title = {"자취앤집밥", "자취앤집밥", "자취인만남", "자취인정보", "나눔.대여", "자취Q&A"}; //게시판 이름
    private String[] content = {"요리레시피", "요리레시피2", "독서모임", "생활정보", "물품나눔", "큐앤에이"}; //게시물 내용

    private String[] day = {"2020.6.20", "2020.6.20", "2020.6.19", "2020.6.18", "2020.6.18", "2020.6.18"}; //날짜

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView2;
        public TextView textView3;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.img);
            this.textView = view.findViewById(R.id.title);
            this.textView2 = view.findViewById(R.id.content);
            this.textView3 = view.findViewById(R.id.day);
        }
    }

    @Override
    public MyContentsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycontents, parent, false);
        MyContentsRecyclerViewAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyContentsRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
        holder.textView3.setText(day[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), qaActivityContent.class );
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
