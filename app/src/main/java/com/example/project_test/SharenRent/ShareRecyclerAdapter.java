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

public class ShareRecyclerAdapter extends RecyclerView.Adapter<ShareRecyclerAdapter.ShareViewHolder> {
    private ArrayList<ShareListData> datas;

    public void setData(ArrayList<ShareListData> list) {datas = list;}


    public class ShareViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView, textView2;
        public ImageView imageView;

        public ShareViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.img);
            this.textView = view.findViewById(R.id.title);
            this.textView2 = view.findViewById(R.id.price);
        }
    }

    @Override
    public ShareRecyclerAdapter.ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item, parent, false);
        ShareRecyclerAdapter.ShareViewHolder viewHolder = new ShareRecyclerAdapter.ShareViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShareRecyclerAdapter.ShareViewHolder holder, final int position) {
        ShareListData data = datas.get(position);

        final int img = data.getImg();
        final String title = data.getTitle();
        final String price = data.getPrice();

        holder.imageView.setImageResource(img);
        holder.textView.setText(title);
        holder.textView2.setText(price+"원");

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShareContentActivity.class);
                intent.putExtra("탭이름", "나눔");
                intent.putExtra("제목", title);
                intent.putExtra("작성자", "1111");
                intent.putExtra("날짜", "1111");
                intent.putExtra("내용", "1111");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
