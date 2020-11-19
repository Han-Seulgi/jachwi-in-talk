package com.example.project_test.Mypage.MyContents;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class MyContentsFragment1 extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private MyContentsRecyclerAdapter adapter;

    String id;

    ArrayList<MyListData> data;

    public Fragment frg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mycontents1, container, false);

        id = LoginActivity.user_ac;

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MyContentsRecyclerAdapter();

        data = new ArrayList<>();

        frg = MyContentsFragment1.this;

        //서버 연결
        Api api = Api.Factory.INSTANCE.create();
        api.checkWriter(id).enqueue(new Callback<MyPostList>() {
            @Override
            public void onResponse(Call<MyPostList> call, Response<MyPostList> response) {
                MyPostList postList = response.body();
                List<PostData> postData = postList.items;

                ArrayList<String> title1 = new ArrayList<>();
                ArrayList<String> day1 = new ArrayList<>();
                ArrayList<String> id1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();
                ArrayList<String> board1 = new ArrayList<>();
                ArrayList<Integer> code1 = new ArrayList<>();

                //리스트에 제목, 날짜, 작성자 아이디 넣기
                for (PostData d:postData) {
                    title1.add(d.post_title);
                    day1.add(d.post_day);
                    id1.add(d.id);
                    con1.add(d.post_con);
                    board1.add(d.board_name);
                    code1.add(d.post_code);
                    Log.i("my","마페 All: " + d.toString());
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                String title[] = title1.toArray(new String[title1.size()]);
                String day[] = day1.toArray(new String[day1.size()]);
                String board[] = board1.toArray(new String[board1.size()]);
                Integer img[] = new Integer[title1.size()];
                String[] id = id1.toArray(new String[id1.size()]);
                String[] con = con1.toArray(new String[con1.size()]);
                Integer[] code = code1.toArray(new Integer[code1.size()]);

                int i = 0;
                while (i < title.length) {
                    img[i] = R.drawable.foodboard;
                    data.add(new MyListData(img[i], title[i], board[i], day[i], id[i], con[i], code[i]));
                    i++;
                }
                adapter.setData(frg, data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MyPostList> call, Throwable t) {
                Log.i("my", t.getMessage());
            }
        }); //서버연결

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent rdata) {
        super.onActivityResult(requestCode, resultCode, rdata);
//        if (resultCode == RESULT_OK) {
        Log.i("refresh", "requestcode: "+requestCode);
        Log.i("rbact", "requestcode: "+requestCode+"resultcode"+resultCode);
        switch (requestCode) {
            case 777:
                if(resultCode == RESULT_OK){
                    int position = rdata.getIntExtra("position", 0);
                    int rc = rdata.getIntExtra("rc", 0);
                    Log.i("mod", ""+rc);
                    if (rc == 1) {
                        int img = R.drawable.foodboard;
                        String title = rdata.getStringExtra("title");
                        String id = rdata.getStringExtra("id");
                        String day = rdata.getStringExtra("day");
                        String con = rdata.getStringExtra("con");
                        String board = rdata.getStringExtra("board");
                        Log.i("mod", "수정"+rdata.getStringExtra("board"));
                        adapter.updateData(position, new MyListData(img, title, board, day, id, con));
                        Log.i("mod", "update");
                    } else if (rc == 2) {
                        adapter.deleteData(position);
                    } else Log.i("mod/del fail", "실패");
                }
        }
    }
}
