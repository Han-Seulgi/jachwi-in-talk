package com.example.project_test.qa;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Info.InfoContent.infoActivityContent;
import com.example.project_test.R;
import com.example.project_test.qa.qaContent.qaActivityContent;

import java.util.ArrayList;

public class qaRecyclerAdapter extends RecyclerView.Adapter<qaRecyclerAdapter.qaViewHolder> {
    private ArrayList<qaListData> datas;
    private Activity rActivity;
    private int MODIFY_POST = 200;
    private int DELETE_POST = 300;

    public void setData(Activity act, ArrayList<qaListData> list){
        datas = list;
        rActivity = act;
    }

    public void addData(qaListData data){
        datas.add(0,data);
        notifyItemRangeInserted(0, datas.size());

        Log.i("itemcount", String.valueOf(getItemCount()));
    }

    public void updateData(int position, qaListData data){
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
    public qaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        qaViewHolder holder = new qaViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final qaRecyclerAdapter.qaViewHolder holder, final int position) {
        qaListData data = datas.get(position);

        final String title = data.getTitle();
        final String id = data.getId();
        final String day = data.getDay();
        final String con = data.getCon();

        holder.imageView.setImageResource(data.getImg());
        holder.textView.setText(data.getTitle());
        holder.textView2.setText(data.getDay());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), qaActivityContent.class);
                intent.putExtra("제목", title); //게시물의 제목
                intent.putExtra("작성자", id);
                intent.putExtra("날짜", day);
                intent.putExtra("내용", con);
                intent.putExtra("타이틀", "자취QA");
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

    public class qaViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView,textView2;

        public qaViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
        }
    }
}
