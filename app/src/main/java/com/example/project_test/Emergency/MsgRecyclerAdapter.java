package com.example.project_test.Emergency;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.Mypage.KeywordList;
import com.example.project_test.Mypage.KwRecyclerAdapter;
import com.example.project_test.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MsgRecyclerAdapter extends RecyclerView.Adapter<MsgRecyclerAdapter.MsgViewHolder> {

    private ArrayList<String> datas;

    public void setData(ArrayList<String> list) { datas = list;}

    @NonNull
    @Override
    public MsgRecyclerAdapter.MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msgnum, parent, false);
        MsgRecyclerAdapter.MsgViewHolder holder = new MsgRecyclerAdapter.MsgViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(final MsgRecyclerAdapter.MsgViewHolder holder, final int position) {
        final String mn = datas.get(position);
        holder.kwdbtn.setText(mn);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                datas.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, datas.size());

                //db에서 삭제
                Api api = Api.Factory.INSTANCE.create();
                api.delmsgnum(mn, EmergencyActivity.ID).enqueue(new Callback<MsgNumList>() {
                    @Override
                    public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                        MsgNumList mnl = response.body();
                        Boolean update = mnl.update;

                        if(update) {
                            Log.i("삭제", update+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgNumList> call, Throwable t) {

                    }
                });

                return false;
            }
        });
    }

    public int getItemCount() {
        return (datas != null ? datas.size() : 0);
    }

    public class MsgViewHolder extends RecyclerView.ViewHolder {
        public TextView kwdbtn;

        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);

            kwdbtn = itemView.findViewById(R.id.kwdbtn);
        }
    }
}
