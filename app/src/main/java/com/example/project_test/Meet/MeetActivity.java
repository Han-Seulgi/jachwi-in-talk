package com.example.project_test.Meet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.Writing.MeetWritingActivity;
import com.example.project_test.Writing.WritingActivity;

import java.util.ArrayList;

public class MeetActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView rv;
    private MeetRecyclerAdapter adapter;
    private GridLayoutManager layoutManager;

    final int image[] = {R.drawable.meetimg1,R.drawable.meetimg1,R.drawable.meetimg1};
    int cnt[] = {1,3,5};
    final String title[] = {"응암동 공부 스터디모임","강아지산책모임","영어 회화 모임 "};

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

        RecyclerView rv = findViewById(R.id.rv);
        Button writing = findViewById(R.id.writing);

        ArrayList<MeetListData> data = new ArrayList<>();

        int i = 0;
        while (i < title.length) {
            data.add(new MeetListData(image[i], cnt[i], title[i]));
            i++;
        }
        adapter = new MeetRecyclerAdapter();
        adapter.setData(data);
        rv.setAdapter(adapter);
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
