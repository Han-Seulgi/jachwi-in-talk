package com.example.project_test.Modify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.DeleteImg;
import com.example.project_test.R;
import com.example.project_test.Writing.ThumbnailListData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ThumbnailListData data = datas.get(position);

        String img_data = data.getImgdata();
        final int img_code = data.getImgcode();
        Log.i("setimg",img_data);

        byte[] encodeByte = Base64.decode(img_data, Base64.NO_WRAP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        //bitmap;

        holder.iv.setImageBitmap(bitmap);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Api api = Api.Factory.INSTANCE.create();
                api.deleteImg(img_code).enqueue(new Callback<DeleteImg>() {
                    @Override
                    public void onResponse(Call<DeleteImg> call, Response<DeleteImg> response) {
                        Log.i("imgdelete","성공"+img_code);
                        datas.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, datas.size());
                        Log.i("imgdelete", "갱신도 성공");
                        notifyItemRemoved(position);
                    }

                    @Override
                    public void onFailure(Call<DeleteImg> call, Throwable t) {
                        Log.i("imgdelete",t.getMessage()+"실패");
                    }
                });
                return false;
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
