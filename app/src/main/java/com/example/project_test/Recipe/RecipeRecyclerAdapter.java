package com.example.project_test.Recipe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
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
    private Activity rActivity;
    private int MODIFY_POST = 200;
    private int DELETE_POST = 300;


    public void setData(Activity act, ArrayList<RecipeListData> list){
        datas = list;
        rActivity = act;
    }

    public void addData(RecipeListData data){
        datas.add(0,data);
        notifyItemRangeInserted(0, datas.size());

        Log.i("itemcount", String.valueOf(getItemCount()));
    }

    public void updateData(int position, RecipeListData data){
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
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent, false);

        RecipeViewHolder holder = new RecipeViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, final int position) {
        RecipeListData data = datas.get(position);

        String img_data = data.getImg_data();
        //Log.i("setimg",img_data);
        if(img_data == null || img_data.equals("none") )
            holder.img.setImageResource(R.drawable.recipe);

        else {
            byte[] encodeByte = Base64.decode(img_data, Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            //bitmap;
            holder.img.setImageBitmap(bitmap);
        }

        final String tt = data.getTabTitle();
        final String title = data.getTitle();
        final String id = data.getId();
        final String day = data.getDay();
        final String con = data.getCon();
        //final int code = data.getCode();

        //holder.img.setImageResource(data.getImg());
        holder.title.setText(title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContentWithPicture.class);
                intent.putExtra("탭이름",tt);
                intent.putExtra("제목", title); //게시물의 제목
                intent.putExtra("작성자", id);
                intent.putExtra("날짜", day);
                intent.putExtra("내용", con);
                //intent.putExtra("코드", code);
                intent.putExtra("requestmod", MODIFY_POST);
                intent.putExtra("requestdel", DELETE_POST);
                intent.putExtra("position", position);
                Log.i("rradapter: ", "MOD_POST: " +MODIFY_POST+"   DEL_POST: "+DELETE_POST+"   Act: "+rActivity);
                rActivity.startActivityForResult(intent, 777);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView title;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }
    }
}