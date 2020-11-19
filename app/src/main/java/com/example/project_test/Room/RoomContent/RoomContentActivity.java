package com.example.project_test.Room.RoomContent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.R;
import com.example.project_test.Room.RoomData;
import com.example.project_test.Room.RoomList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomContentActivity extends AppCompatActivity {
    Toolbar toolbar;

    String title, id, day, content, room_lct, post_day, p;
    TextView text1, id1, day1, content1, room_lct1, p1;
    int post_code;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_content);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        text1 = findViewById(R.id.text1);
        id1 = findViewById(R.id.id);
        day1 = findViewById(R.id.day);
        content1 = findViewById(R.id.con);
        room_lct1 = findViewById(R.id.room_lct);
        p1 = findViewById(R.id.p);

        //받아오기
        //제목, 내용, 코드
        Intent intent = getIntent();
        title = intent.getStringExtra("제목");
        //id = intent.getStringExtra("아이디");
        //day = intent.getStringExtra("만료날짜");
        content = intent.getStringExtra("내용");
        //room_lct = intent.getStringExtra("장소");
        post_code = intent.getIntExtra("코드", 0);
        Log.i("코드", post_code+"");
        //p = intent.getStringExtra("가격");
        post_day = intent.getStringExtra("날짜");

        text1.setText(title);
        content1.setText(content);

        //받아온 코드로 게시물 검색
        Api api = Api.Factory.INSTANCE.create();
        api.getRoom(post_code).enqueue(new Callback<RoomData>() {
            @Override
            public void onResponse(Call<RoomData> call, Response<RoomData> response) {
                RoomData rdata = response.body();

                id = rdata.id;
                day = rdata.room_day;
                room_lct = rdata.room_lct;
                p = rdata.room_p;


                id1.setText(id+"\n"+post_day);
                day1.setText("만료날짜 : " + day);
                room_lct1.setText(room_lct);
                p1.setText("가격 : " + p);

            }

            @Override
            public void onFailure(Call<RoomData> call, Throwable t) {
                Log.i("코드", t.getMessage());
            }
        });






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
               finish();
                return true;
        }
        return true;
    }
}