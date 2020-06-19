package com.example.project_test.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.project_test.R;
import com.example.project_test.Writing.FoodWritingActivity;

public class FoodActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        ViewPager pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        FoodAdapter adapter2 = new FoodAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter2);

        Fragment FoodActivityTab1 = new FoodActivityTab1();
        adapter2.addItem(FoodActivityTab1);
        adapter2.notifyDataSetChanged();

        Fragment FoodActivityTab2 = new FoodActivityTab2();
        adapter2.addItem(FoodActivityTab2);
        adapter2.notifyDataSetChanged();

        Button writing = findViewById(R.id.writing);

//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new RecyclerAdapter();

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FoodWritingActivity.class);
                startActivity(intent);

            }
        });
    }

}



