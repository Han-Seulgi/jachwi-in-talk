package com.example.project_test.Recipe;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.R;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeViewHolder> {
    private ArrayList<RecipeListData> datas;

    public void setData(ArrayList<RecipeListData> list){
        datas = list;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent, false);

        RecipeViewHolder holder = new RecipeViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeListData data = datas.get(position);

        holder.img.setImageResource(data.getImg());
        holder.count.setText(data.getCount());
        holder.title.setText(data.getTitle());
        final String t = data.getTitle();
        final String tt = data.getTabTitle();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContentWithPicture.class);
                intent.putExtra("제목", t); //게시물의 제목
                intent.putExtra("탭이름",tt);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView count,title;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            count = itemView.findViewById(R.id.count);
            title = itemView.findViewById(R.id.title);
        }
    }
}
