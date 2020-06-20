package com.example.project_test.Info.InfoContent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

public class infoActivityContent extends AppCompatActivity {
    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    TextView tabTitle, text1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qacontent);

            tabTitle = findViewById(R.id.title);
            tabTitle.setText("자취인정보");
            text1 = findViewById(R.id.text1);

            Intent intent = getIntent();
            String a = intent.getStringExtra("제목");

            text1.setText(a);
            
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(layoutManager);

            adapter = new InfoRecyclerAdapterContent();
            recyclerView.setAdapter(adapter);



        }
}

