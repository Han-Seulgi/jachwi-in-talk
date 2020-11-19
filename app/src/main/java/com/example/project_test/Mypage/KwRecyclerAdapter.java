package com.example.project_test.Mypage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KwRecyclerAdapter extends RecyclerView.Adapter<KwRecyclerAdapter.kwViewHolder> {

    private ArrayList<String> datas;

    public void setData(ArrayList<String> list) { datas = list;}

    @NonNull
    @Override
    public kwViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kwd, parent, false);
        kwViewHolder holder = new kwViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(final KwRecyclerAdapter.kwViewHolder holder, final int position) {
        final String kw = datas.get(position);
        holder.kwdbtn.setText(kw);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                datas.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, datas.size());

                //dv에서 삭제
                Api api = Api.Factory.INSTANCE.create();
                api.deletekeyword(kw, LoginActivity.user_ac).enqueue(new Callback<KeywordList>() {
                    @Override
                    public void onResponse(Call<KeywordList> call, Response<KeywordList> response) {
                        KeywordList kl = response.body();
                        Boolean update = kl.update;

                        if(update) {
                            Log.i("삭제", update+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<KeywordList> call, Throwable t) {
                        Log.i("실패", t.getMessage());
                    }
                });

                return false;
            }
        });
    }

    public int getItemCount() {
        return (datas != null ? datas.size() : 0);
    }

    public class kwViewHolder extends RecyclerView.ViewHolder {
        public TextView kwdbtn;

        public kwViewHolder(@NonNull View itemView) {
            super(itemView);

            kwdbtn = itemView.findViewById(R.id.kwdbtn);
        }
    }
}
