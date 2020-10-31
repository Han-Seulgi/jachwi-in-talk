package com.example.project_test.Writing;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

import java.util.ArrayList;

public class RecyclerAdapterThumbnail extends RecyclerView.Adapter<RecyclerAdapterThumbnail.ViewHolder>{
    private ArrayList<ThumbnailListData> datas;

    public void setData(ArrayList<ThumbnailListData> list){
        datas = list;
    }
    //int imgs [] = {R.drawable.rrimg1,R.drawable.rrimg2,R.drawable.rrimg3,R.drawable.rrimg4};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pictures_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThumbnailListData data = datas.get(position);

        Bitmap pbm = data.getPbm();

        holder.iv.setImageBitmap(pbm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"이미지띄움",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public ViewHolder (View view){
            super(view);
            this.iv = view.findViewById(R.id.iv);
        }
    }
}
