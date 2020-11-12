package com.example.project_test.Food;

import android.app.Activity;
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
import com.example.project_test.DeletePost;
import com.example.project_test.Food.FoodContent.FoodActivityContent;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder> {
    private ArrayList<FoodListData> datas;
    Activity rActivity;
    private int MODIFY_POST = 200;
    private int DELETE_POST = 300;

    public void setData(Activity act, ArrayList<FoodListData> list) {
        datas = list;
        rActivity = act;
    }

    public void addData(FoodListData data){
        datas.add(0,data);
        notifyItemRangeInserted(0, datas.size());

        Log.i("itemcount", String.valueOf(getItemCount()));
    }

    public void updateData(int position, FoodListData data){
        datas.set(position, data);
        notifyItemChanged(position);
    }

    public void deleteData(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datas.size());
        Log.i("delete", "갱신도 성공"+position);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        FoodViewHolder holder = new FoodViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, final int position) {
        FoodListData data = datas.get(position);

        final String title = data.getTitle();
        final String id = data.getId();
        final String day = data.getDay();
        final String con = data.getCon();

        holder.imageView.setImageResource(data.getImg());
        holder.textView.setText(title);
        holder.textView2.setText(day);

        if( id.equals(LoginActivity.user_ac)) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            Log.i("fd","글 아이디: "+id+"접속아이디: "+LoginActivity.user_ac);
        }
        else {
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() { //글 목록 클릭했을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FoodActivityContent.class);
                intent.putExtra("제목", title); //게시물의 제목
                intent.putExtra("작성자", id);
                intent.putExtra("날짜", day);
                intent.putExtra("내용", con);
                intent.putExtra("requestmod", MODIFY_POST);
                intent.putExtra("requestdel", DELETE_POST);
                intent.putExtra("position", position);
                Log.i("rradapter: ", "MOD_POST: " +MODIFY_POST+"   DEL_POST: "+DELETE_POST+"   Act: "+rActivity);
                rActivity.startActivityForResult(intent, 777);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"수정",Toast.LENGTH_SHORT).show();
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

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public ImageButton edit, delete;

        public FoodViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.img);
            textView = view.findViewById(R.id.tv1);
            textView2 = view.findViewById(R.id.tv2);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }

}
