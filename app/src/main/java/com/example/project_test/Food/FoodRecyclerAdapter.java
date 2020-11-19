package com.example.project_test.Food;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Food.FoodContent.FoodActivityContent;
import com.example.project_test.R;

import java.util.ArrayList;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder> {
    private ArrayList<FoodListData> datas;
    Fragment frg;
    private int MODIFY_POST = 200;
    private int DELETE_POST = 300;

    public void setData(Fragment f, ArrayList<FoodListData> list) {
        datas = list;
        frg = f;
    }

    public void addData(FoodListData data){
        datas.add(0,data);
        notifyItemRangeInserted(0, datas.size());

        Log.i("itemcount", String.valueOf(getItemCount()));
    }

    public void updateData(int position, FoodListData data){
        datas.set(position, data);
        notifyItemChanged(position);
    }

    public void deleteData(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datas.size());
        Log.i("delete", "갱신도 성공"+position);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        FoodViewHolder holder = new FoodViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, final int position) {
        FoodListData data = datas.get(position);

        final String title = data.getTitle();
        final String id = data.getId();
        final String day = data.getDay();
        final String con = data.getCon();

        holder.imageView.setImageResource(data.getImg());
        holder.textView.setText(title);
        holder.textView2.setText(day);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FoodActivityContent.class);
                intent.putExtra("제목", title); //게시물의 제목
                intent.putExtra("작성자", id);
                intent.putExtra("날짜", day);
                intent.putExtra("내용", con);
                intent.putExtra("requestmod", MODIFY_POST);
                intent.putExtra("requestdel", DELETE_POST);
                intent.putExtra("position", position);
                frg.startActivityForResult(intent, 777);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public ImageButton edit, delete;

        public FoodViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.img);
            textView = view.findViewById(R.id.tv1);
            textView2 = view.findViewById(R.id.tv2);
        }
    }

}
