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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.Delete.DeletePost;
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

public class MyContentsRecyclerAdapter extends RecyclerView.Adapter<MyContentsRecyclerAdapter.MyViewHolder> {
    private ArrayList<MyListData> datas;

    public void setData(ArrayList<MyListData> list) {
        datas = list;
    }

    Intent intent;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycontents, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        MyListData data = datas.get(position);

        final String title = data.getTitle();
        final String id = data.getId();
        final String day = data.getDay();
        final String con = data.getCon();
        final String board = data.getBoard();

        holder.imageView.setImageResource(data.getImg());
        holder.textView.setText(title);
        holder.textView2.setText(data.getBoard());
        holder.textView3.setText(day);

        if( id.equals(LoginActivity.user_ac)) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            Log.i("fd","글 아이디: "+id+"접속아이디: "+LoginActivity.user_ac);
        }
        else {
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }//

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                if(board.equals("요리")) { //자취앤집밥 게시판이라면
                    intent = new Intent(v.getContext(), ContentWithPicture.class);
                }
                else if(board.equals("맛집공유")) { //자취앤혼밥 게시판이라면
                    intent = new Intent(v.getContext(), FoodActivityContent.class);
                }
                else if(board.equals("모임")) { //자취인만남 게시판이라면
                    intent = new Intent(v.getContext(), MeetActivityContent.class);
                }
                else if(board.equals("자취Q&A")) { //자취Q&A 게시판이라면
                    intent = new Intent(v.getContext(), qaActivityContent.class);
                }
                else if (board.equals("생활정보")){ //자취인정보 게시판이라면
                    intent = new Intent(v.getContext(), infoActivityContent.class);
                } else ;
                intent.putExtra("제목", title); //게시물의 제목
                intent.putExtra("작성자", id);
                intent.putExtra("날짜", day);
                intent.putExtra("내용", con);
                v.getContext().startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                AlertDialog dialog;
                dialog = builder.setMessage("게시물을 삭제하시겠습니까?").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("delete", "게시물 삭제하기" + title);

                                Api api = Api.Factory.INSTANCE.create();

                                Log.i("hihihi", "아오"+title);

                                api.deletepost(title).enqueue(new Callback<DeletePost>() {
                                    @Override
                                    public void onResponse(Call<DeletePost> call, Response<DeletePost> response) {
                                        //DeletePost deletePost = response.body();
                                        //boolean del = deletePost.delete;

                                        Log.i("delete", "성공" + response);
                                        datas.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, datas.size());
                                        Log.i("delete", "갱신도 성공");
                                    }
                                    @Override
                                    public void onFailure(Call<DeletePost> call, Throwable t) {
                                        Log.i("delete",t.getMessage());
                                    }
                                });

                            }
                        }
                ).create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;//그림..?
        public TextView textView;//제목
        public TextView textView2;//게시판 이름
        public TextView textView3;//날짜
        public ImageButton edit, delete;

        public MyViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.img);
            this.textView = view.findViewById(R.id.title);
            this.textView2 = view.findViewById(R.id.board);
            this.textView3 = view.findViewById(R.id.day);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
