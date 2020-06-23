package com.example.project_test.Info;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Info.InfoContent.infoActivityContent;
import com.example.project_test.R;
public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.ViewHolder> {

    private String[] title = {"에어컨 청소 팁좀 알려주세요~", "안녕하세요", "드디어 입주^^!!", "오늘은 운동삼아 걸어서 퇴근했어요", "우리 초코 산책", "드디어 금요일!!!", "안녕하세요", "드디어 입주^^!!"}; // 게시물 제목 가져오기

    private String[] content = {"2020.6.20", "2020.6.20", "2020.6.19", "2020.6.19", "2020.6.18", "2020.6.17", "2020.6.18", "2020.6.17"}; //게시물 내용

    private Integer[] img = {R.drawable.information, R.drawable.information, R.drawable.information, R.drawable.information, R.drawable.information, R.drawable.information, R.drawable.information, R.drawable.information};

    public class ViewHolder extends RecyclerView.ViewHolder {
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
    public InfoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        InfoRecyclerAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final InfoRecyclerAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
        holder.imageView.setImageResource(img[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), infoActivityContent.class);
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
