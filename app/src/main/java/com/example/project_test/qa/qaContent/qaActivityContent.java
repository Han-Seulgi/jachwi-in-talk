package com.example.project_test.qa.qaContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

public class qaActivityContent extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    TextView text1,tabTitle;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qacontent);

            //상단탭
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            tabTitle=findViewById(R.id.title);
            text1 = findViewById(R.id.text1);

            Intent intent = getIntent();
            String a = intent.getStringExtra("제목");
            String tt = intent.getStringExtra("탭이름");

            text1.setText(a);
            tabTitle.setText(tt);
            
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(layoutManager);

            adapter = new RecyclerAdapterContent();
            recyclerView.setAdapter(adapter);
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

