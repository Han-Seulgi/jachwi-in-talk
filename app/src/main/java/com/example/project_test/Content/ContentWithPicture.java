package com.example.project_test.Content;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.Content.RecyclerAdapterContent;

public class ContentWithPicture extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewImg;
    public RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager layoutManager2;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter2;

    TextView text1, tabTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_pic);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabTitle = findViewById(R.id.title);

        text1 = findViewById(R.id.text1);
        Intent intent = getIntent();
        String a = intent.getStringExtra("제목");
        String t = intent.getStringExtra("탭이름");
        text1.setText(a);
        tabTitle.setText(t);

        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerViewImg = findViewById(R.id.recyclerViewImg);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewImg.setLayoutManager(layoutManager2);

        adapter = new RecyclerAdapterContent();
        recyclerView.setAdapter(adapter);

        adapter2 = new RecyclerAdapterImg();
        recyclerViewImg.setAdapter(adapter2);
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
