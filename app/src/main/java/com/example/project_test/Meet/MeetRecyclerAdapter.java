package com.example.project_test.Meet;

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

import com.example.project_test.Meet.MeetContent.MeetActivityContent;
import com.example.project_test.R;

import java.util.ArrayList;

public class MeetRecyclerAdapter extends RecyclerView.Adapter<MeetRecyclerAdapter.MeetViewHolder> {
    private ArrayList<MeetListData> datas;
    private Activity rActivity;
    private int MODIFY_POST = 200;
    private int DELETE_POST = 300;

    public void setData(Activity act, ArrayList<MeetListData> list){
        datas = list;
        rActivity = act;
    }

    public void addData(MeetListData data){
        datas.add(0,data);
        notifyItemRangeInserted(0, datas.size());

        Log.i("itemcount", String.valueOf(getItemCount()));
    }

    public void updateData(int position, MeetListData data){
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
    public MeetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meet_item,parent, false);

        MeetViewHolder holder = new MeetViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MeetViewHolder holder, final int position) {
        MeetListData data = datas.get(position);

        final String title = data.getTitle();
        final String id = data.getId();
        final String day = data.getDay();
        final String con = data.getCon();
        final int dimg = data.getImg();

        holder.img.setImageResource(dimg);
        holder.title.setText(title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MeetActivityContent.class);
                intent.putExtra("제목", title); //게시물의 제목
                intent.putExtra("작성자", id);
                intent.putExtra("날짜", day);
                intent.putExtra("내용", con);
                //intent.putExtra("사진", dimg);
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

    public class MeetViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView title;

        public MeetViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }
    }
}
