package com.example.project_test.Room.RoomContent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.R;

public class RoomContentActivity extends AppCompatActivity {
    Toolbar toolbar;

    String title, id, day, content;
    TextView text1;

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

        //받아오기
        Intent intent = getIntent();
        title = intent.getStringExtra("제목");

        text1.setText(title);
    }
}
