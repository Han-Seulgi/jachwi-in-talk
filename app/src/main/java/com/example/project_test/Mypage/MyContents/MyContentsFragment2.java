package com.example.project_test.Mypage.MyContents;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

public class MyContentsFragment2 extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private MyContentsRecyclerAdapter adapter;

    String id;

    ArrayList<MyListData> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mycontents2, container, false);

        id = LoginActivity.user_ac;

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MyContentsRecyclerAdapter();

        data = new ArrayList<>();

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
                ArrayList<Integer> board1 = new ArrayList<>();

                //리스트에 제목, 날짜, 작성자 아이디 넣기
                for (PostData d:postData) {
                    title1.add(d.post_title);
                    day1.add(d.post_day);
                    id1.add(d.id);
                    con1.add(d.post_con);
                    board1.add(d.board_code);
                    Log.i("my","마페 All: " + d.toString());
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                String title[] = title1.toArray(new String[title1.size()]);
                String day[] = day1.toArray(new String[day1.size()]);
                Integer board[] = board1.toArray(new Integer[board1.size()]);
                Integer img[] = new Integer[title1.size()];
                String[] id = id1.toArray(new String[id1.size()]);
                String[] con = con1.toArray(new String[con1.size()]);

                int i = 0;
                while (i < title.length) {
                    img[i] = R.drawable.foodboard;
                    data.add(new MyListData(img[i], title[i], board[i], day[i], id[i], con[i]));
                    i++;
                }
                adapter.setData(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MyPostList> call, Throwable t) {
                Log.i("foood", t.getMessage());
            }
        }); //서버연결

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }
}
