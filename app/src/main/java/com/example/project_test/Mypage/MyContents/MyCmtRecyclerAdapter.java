package com.example.project_test.Mypage.MyContents;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.Delete.DeleteCmt;
import com.example.project_test.Food.FoodContent.FoodActivityContent;
import com.example.project_test.Info.InfoContent.infoActivityContent;
import com.example.project_test.LoginActivity;
import com.example.project_test.Meet.MeetContent.MeetActivityContent;
import com.example.project_test.R;
import com.example.project_test.qa.qaContent.qaActivityContent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCmtRecyclerAdapter extends RecyclerView.Adapter<MyCmtRecyclerAdapter.CmtViewHolder> {
    private ArrayList<MyCmtListData> datas;
    public void setData(ArrayList<MyCmtListData> list) { datas = list; }
    Intent intent;

    @NonNull
    @Override
    public MyCmtRecyclerAdapter.CmtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycontents2,parent,false);

        CmtViewHolder holder = new CmtViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CmtViewHolder holder, final int position) {
        MyCmtListData data = datas.get(position);

        final int code = data.getCmtcode();
        final String cmtcon = data.getCmtcon();
        final String cmtday = data.getCmtday();
        final String id = data.getId();
        final String ptitle = data.getPosttitle();
        final String pcon = data.getPostcon();
        final String pday = data.getPostday();
        final int board = data.getBoard();

        holder.iv.setImageResource(data.getImg());
        holder.title.setText(ptitle);
        holder.cmt.setText(cmtcon);
        holder.day.setText(cmtday);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                if(board == 11) { //자취앤집밥 게시판이라면
                    intent = new Intent(v.getContext(), ContentWithPicture.class);
                }
                else if(board == 22) { //자취앤혼밥 게시판이라면
                    intent = new Intent(v.getContext(), FoodActivityContent.class);
                }
                else if(board == 33) { //자취인만남 게시판이라면
                    intent = new Intent(v.getContext(), MeetActivityContent.class);
                }
                else if(board == 66) { //자취Q&A 게시판이라면
                    intent = new Intent(v.getContext(), qaActivityContent.class);
                }
                else if (board == 77){ //자취인정보 게시판이라면
                    intent = new Intent(v.getContext(), infoActivityContent.class);
                } else ;
                intent.putExtra("제목", ptitle); //게시물의 제목
                intent.putExtra("작성자", id);
                intent.putExtra("날짜", pday);
                intent.putExtra("내용", pcon);
                v.getContext().startActivity(intent);
            }
        });

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
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CmtViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView cmt, title, day;
        public ImageButton delete;

        public CmtViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.img);
            cmt = itemView.findViewById(R.id.cmt);
            title = itemView.findViewById(R.id.title);
            day = itemView.findViewById(R.id.day);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}


