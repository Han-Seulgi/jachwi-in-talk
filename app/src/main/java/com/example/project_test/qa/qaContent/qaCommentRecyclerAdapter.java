package com.example.project_test.qa.qaContent;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Comment.CommentListData;
import com.example.project_test.Delete.DeleteCmt;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class qaCommentRecyclerAdapter extends RecyclerView.Adapter<qaCommentRecyclerAdapter.CommentViewHolder> {
    private ArrayList<CommentListData> datas;

    public void setData(ArrayList<CommentListData> list) {datas = list;}

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qacomment_item,parent,false);

        CommentViewHolder holder = new CommentViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, final int position) {
        CommentListData data = datas.get(position);

        final String id = data.getId();
        final int code = data.getCode();
        final int like_num = data.getLike();

        holder.textView.setText(data.getCon());
        holder.textView2.setText(data.getId());
        holder.textView3.setText(data.getDay());
        holder.like.setVisibility(View.VISIBLE);
        holder.likeNum.setVisibility(View.VISIBLE);
        holder.likeNum.setText(like_num);

        //삭제버튼 보이기
        if( id.equals(LoginActivity.user_ac)) {
            holder.delete.setVisibility(View.VISIBLE);
            Log.i("recyclerview","글 아이디: "+id+"접속아이디: "+LoginActivity.user_ac);
        }
        else {
            holder.delete.setVisibility(View.GONE);
        }
        //삭제
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setMessage("댓글을 삭제하시겠습니까?").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("delete", "댓글 삭제하기" + code);

                                Api api = Api.Factory.INSTANCE.create();

                                api.deletecmt(code).enqueue(new Callback<DeleteCmt>() {
                                    @Override
                                    public void onResponse(Call<DeleteCmt> call, Response<DeleteCmt> response) {
                                        Log.i("delete", "성공" + response);
                                        datas.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, datas.size());
                                        Log.i("delete", "갱신도 성공");
                                    }
                                    @Override
                                    public void onFailure(Call<DeleteCmt> call, Throwable t) {
                                        Log.i("delete",t.getMessage());
                                    }
                                });
                            }
                        }
                ).create();
                builder.show();
            }
        });
        //추천
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView textView,textView2,textView3,likeNum;

        public ImageButton delete;
        public ImageButton like;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
            textView3 = itemView.findViewById(R.id.tv3);

            delete = itemView.findViewById(R.id.delete);
            like = itemView.findViewById(R.id.like);
            likeNum = itemView.findViewById(R.id.textLikenum);
        }
    }
}
