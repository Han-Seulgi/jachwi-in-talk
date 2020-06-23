package com.example.project_test.Food;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Food.FoodContent.FoodActivityContent;
import com.example.project_test.R;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder> {
    //ImageView img;

    private String[] title = {"점심으로 베가보쌈 어때요?", "퐁닭떡볶이 신메뉴 추천!!!", "오늘 곱창집 세일하던데 맛 좋아요~", "학교 앞에 피자집 새로 생겨서 한번 가봤어요", "청년다방 떡볶이", "여기 혼밥 하기 딱이에요!", "오늘 여기서 혼자 밥 먹고 왔는데 분위기 좋아요"}; // 게시물 제목

    private String[] content = {"2020.6.20", "2020.6.20", "2020.6.19", "2020.6.18", "2020.6.18", "2020.6.18", "2020.6.17"}; //게시물 내용

    private Integer[] img = {R.drawable.foodboard, R.drawable.foodboard, R.drawable.foodboard, R.drawable.foodboard, R.drawable.foodboard, R.drawable.foodboard, R.drawable.foodboard};
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
    public FoodRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        FoodRecyclerAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final FoodRecyclerAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
        holder.imageView.setImageResource(img[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FoodActivityContent.class);
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
