package com.example.project_test.SharenRent;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.R;

import java.util.ArrayList;

public class SRRecyclerAdapter extends RecyclerView.Adapter<SRRecyclerAdapter.SRViewHolder> {
    private ArrayList<SRListData> datas;

    public void setData(ArrayList<SRListData> list) {datas = list;}


    public class SRViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public SRViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.img);
            this.textView = view.findViewById(R.id.title);
        }
    }

    @Override
    public SRRecyclerAdapter.SRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        SRRecyclerAdapter.SRViewHolder viewHolder = new SRRecyclerAdapter.SRViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SRRecyclerAdapter.SRViewHolder holder, final int position) {
        SRListData data = datas.get(position);

        final String tt = data.getTabtitle();
        final int img = data.getImg();
        final String title = data.getTitle();

        holder.imageView.setImageResource(img);
        holder.textView.setText(title);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ShareContentActivity.class);
////                intent.putExtra("탭이름", tt);
////                intent.putExtra("제목", title);
////                intent.putExtra("작성자", "1111");
////                intent.putExtra("날짜", "1111");
////                intent.putExtra("내용", "1111");
////                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
