package com.example.project_test.SharenRent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

import java.util.ArrayList;

public class SRCategoryRecyclerAdapter extends RecyclerView.Adapter<SRCategoryRecyclerAdapter.SRCategoryViewHolder> {

    ArrayList<String> text = new ArrayList<>();
    public void setData(ArrayList<String> list) {text = list;}

    public class SRCategoryViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView;

        public SRCategoryViewHolder(View view) {
            super(view);
            this.textView = view.findViewById(R.id.cgtxt);
        }
    }

    @Override
    public SRCategoryRecyclerAdapter.SRCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        SRCategoryRecyclerAdapter.SRCategoryViewHolder viewHolder = new SRCategoryRecyclerAdapter.SRCategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SRCategoryRecyclerAdapter.SRCategoryViewHolder holder, final int position) {
        final String datas = text.get(position);
        holder.textView.setText(datas);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //카테고리 클릭
            @Override
            public void onClick(View v) {
                Log.i("abced",position+datas);
            }
        });
    }

    @Override
    public int getItemCount() {
        return text.size();
    }
}
