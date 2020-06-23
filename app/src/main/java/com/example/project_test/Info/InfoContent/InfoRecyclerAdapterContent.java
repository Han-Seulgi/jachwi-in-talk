package com.example.project_test.Info.InfoContent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

public class InfoRecyclerAdapterContent extends RecyclerView.Adapter<InfoRecyclerAdapterContent.ViewHolder>{
    ImageView img;
    private String[] title = {"조립순서 까먹지 않게 꼭 기억해놓아야해요", "마지막에 마른천으로 한번 닦아주면 좋아요", "인터넷 블로그에 자세히 나와있어요!!", "기사 부르는게 나을수도 있어요 ㅋㅋ", "인터넷 블로그에 자세히 나와있어요!!", "인터넷 블로그에 자세히 나와있어요!!", "인터넷 블로그에 자세히 나와있어요!!", "경비아저씨한테 한번 물어보세요", "경비아저씨한테 한번 물어보세요", "경비아저씨한테 한번 물어보세요"}; // 게시물 제목 가져오기

    private String[] content = {"2020.6.22", "2020.6.22", "2020.6.22", "2020.6.19", "2020.6.19", "2020.6.15", "2020.6.15", "2020.6.10", "2020.6.10", "2020.6.1", }; //게시물 내용

    public class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView2;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.img);
            this.textView = view.findViewById(R.id.tv1);
            this.textView2 = view.findViewById(R.id.tv2);
        }
    }

    @Override
    public InfoRecyclerAdapterContent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        InfoRecyclerAdapterContent.ViewHolder viewHolder = new InfoRecyclerAdapterContent.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final InfoRecyclerAdapterContent.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
