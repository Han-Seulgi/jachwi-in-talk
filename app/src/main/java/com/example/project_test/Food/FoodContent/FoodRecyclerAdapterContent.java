package com.example.project_test.Food.FoodContent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

public class FoodRecyclerAdapterContent extends RecyclerView.Adapter<FoodRecyclerAdapterContent.ViewHolder>{
    ImageView img;
    private String[] title = {"우와~~~", "맛있었겠어요", "저도 한번 가보고싶네요", "혼자 가셨어요???", "언제 갔다오신건가요?!", "나중에 다녀와야겠어요", "저도 저기 가봤는데 좋더라고요~", "분위기 좋아요", "좋으셨겠어요", "언제 다녀오셨어요?!"}; // 게시물 제목 가져오기

    private String[] content = {"2020.6.1", "2020.6.1", "2020.6.1", "2020.5.29", "2020.5.29", "2020.5.29", "2020.5.28", "2020.5.28", "2020.5.28", "2020.5.27", }; //게시물 내용

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
    public FoodRecyclerAdapterContent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        FoodRecyclerAdapterContent.ViewHolder viewHolder = new FoodRecyclerAdapterContent.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FoodRecyclerAdapterContent.ViewHolder holder, final int position) {
        holder.textView.setText(title[position]);
        holder.textView2.setText(content[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
