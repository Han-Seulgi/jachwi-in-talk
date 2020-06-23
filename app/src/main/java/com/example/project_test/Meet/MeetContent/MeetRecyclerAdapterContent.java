package com.example.project_test.Meet.MeetContent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Meet.MeetContent.MeetRecyclerAdapterContent;
import com.example.project_test.R;

public class MeetRecyclerAdapterContent extends RecyclerView.Adapter<MeetRecyclerAdapterContent.ViewHolder>{
    ImageView img;
    private String[] title = {"저도 갈래요!", "모임은 한번밖에 없는건가요?", "저도 가고싶은데,, 일이있어서 못가요ㅠㅠㅜ", "저도요!!", "오 저도 가고싶어요", "저도 가고싶네요", "위치는 변경 없는건가요??", "위치는 변경 없는건가요??", "오 저도 가고싶어요", "저도요!!!"}; // 게시물 제목 가져오기

    private String[] content = {"2020.6.23", "2020.6.23", "2020.6.20", "2020.6.10", "2020.6.10", "2020.6.8", "2020.6.5", "2020.6.5", "2020.6.1", "2020.6.1", }; //게시물 내용

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
    public MeetRecyclerAdapterContent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MeetRecyclerAdapterContent.ViewHolder viewHolder = new MeetRecyclerAdapterContent.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MeetRecyclerAdapterContent.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
