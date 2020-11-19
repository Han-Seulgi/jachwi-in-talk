package com.example.project_test.Content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

import java.util.ArrayList;

public class RecyclerAdapterImg extends RecyclerView.Adapter<com.example.project_test.Content.RecyclerAdapterImg.ViewHolder>{
    private ArrayList<ImgListData> datas;

    public void setData(ArrayList<ImgListData> list){
        datas = list;
    };

    public void addData(ImgListData data){
        datas.add(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pictures_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImgListData data = datas.get(position);

        String img_data = data.getImgdata();
        Log.i("setimg",img_data);

        byte[] encodeByte = Base64.decode(img_data, Base64.NO_WRAP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        //bitmap;

        holder.iv.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
