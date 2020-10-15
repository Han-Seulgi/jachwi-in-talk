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
import com.example.project_test.SharenRent.SharenRentContent.ShareContentActivity;

import java.util.ArrayList;

public class RentRecyclerAdapter extends RecyclerView.Adapter<RentRecyclerAdapter.RentViewHolder> {
    private ArrayList<RentListData> datas;

    public void setData(ArrayList<RentListData> list) {datas = list;}


    public class RentViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView, textView2, textView3;
        public ImageView imageView;

        public RentViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.img);
            this.textView = view.findViewById(R.id.title);
            this.textView2 = view.findViewById(R.id.price);
            this.textView3 = view.findViewById(R.id.term);
        }
    }

    @Override
    public RentRecyclerAdapter.RentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_item, parent, false);
        RentRecyclerAdapter.RentViewHolder viewHolder = new RentRecyclerAdapter.RentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RentRecyclerAdapter.RentViewHolder holder, final int position) {
        RentListData data = datas.get(position);

        final int img = data.getImg();
        final String title = data.getTitle();
        final String price = data.getPrice();
        final String term = data.getTerm();

        holder.imageView.setImageResource(img);
        holder.textView.setText(title);
        holder.textView2.setText(price+"원");
        holder.textView3.setText(term);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShareContentActivity.class);
                intent.putExtra("탭이름", "대여");
                intent.putExtra("제목", title);
                intent.putExtra("작성자", "1111");
//                intent.putExtra("날짜", "1111");
//                intent.putExtra("내용", "1111");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
