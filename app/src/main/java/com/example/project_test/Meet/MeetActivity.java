package com.example.project_test.Meet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.Writing.MeetWritingActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView rv;
    private MeetRecyclerAdapter adapter;
    private GridLayoutManager layoutManager;

    ArrayList<MeetListData> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_meet);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        adapter = new MeetRecyclerAdapter();
        Button writing = findViewById(R.id.writing);

        data = new ArrayList<>();
        //서버 연결
        Api api = Api.Factory.INSTANCE.create();
        api.getMeetList(33).enqueue(new Callback<MeetPostList>() {
            @Override
            public void onResponse(Call<MeetPostList> call, Response<MeetPostList> response) {
                MeetPostList postList = response.body();
                List<PostData> postData = postList.items;

                ArrayList<String> title1 = new ArrayList<>();
                ArrayList<String> day1 = new ArrayList<>();
                ArrayList<String> id1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();

                //리스트에 제목, 날짜, 작성자 아이디 넣기
                for (PostData d:postData) {
                    title1.add(d.post_title);
                    day1.add(d.post_day);
                    id1.add(d.id);
                    con1.add(d.post_con);
                    Log.i("abc","모임 All: " + d.toString());
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                String[] title = title1.toArray(new String[title1.size()]);
                String[] day = day1.toArray(new String[day1.size()]);
                String[] id = id1.toArray(new String[id1.size()]);
                String[] con = con1.toArray(new String[con1.size()]);
                Integer[] img = new Integer[title1.size()];

                //넘어온 데이터의 사이즈에 맞춰 이미지 생성(?), 리사이클러뷰 데이터파일에 데이터 넘기기
                int i = 0;
                while (i < title.length) {
                    img[i] = R.drawable.meet2;
                    data.add(new MeetListData(img[i], title[i], day[i], id[i], con[i]));
                    i++;
                }
                adapter.setData(data);
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MeetPostList> call, Throwable t) {
                Log.i("abcdef", t.getMessage());
            }
        }); //서버연결

        layoutManager = new GridLayoutManager(this, 1);
        rv.setLayoutManager(layoutManager);

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetActivity.this, MeetWritingActivity.class);
                startActivity(intent);

            }
        });

    }

    //상단탭 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    //메뉴액션 --home:마이페이지 --message:쪽지함
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent mypage_itnt = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(mypage_itnt);
                return true;
            case android.R.id.message:
                //쪽지함 화면
                return true;
        }
        return true;
    }
}
