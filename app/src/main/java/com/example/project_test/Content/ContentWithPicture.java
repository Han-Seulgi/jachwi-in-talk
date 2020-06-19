package com.example.project_test.Content;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.Content.RecyclerAdapterContent;

public class ContentWithPicture extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewImg;
    public RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager layoutManager2;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter2;

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_pic);

        text1 = findViewById(R.id.text1);
        Intent intent = getIntent();
        String a = intent.getStringExtra("제목");
        text1.setText(a);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
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
}
