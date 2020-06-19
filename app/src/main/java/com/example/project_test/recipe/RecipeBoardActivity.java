package com.example.project_test.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;
import com.example.project_test.Writing.WritingActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

public class RecipeBoardActivity extends AppCompatActivity {
    private RecyclerView rv;
    private RecipeRecyclerAdapter adapter;
    private GridLayoutManager layoutManager;

    final int image[] = {R.drawable.food2, R.drawable.meeting, R.drawable.qna, R.drawable.food2, R.drawable.qna, R.drawable.living,R.drawable.food2, R.drawable.qna, R.drawable.living,R.drawable.food2};
    int cnt[] = {50, 10, 60, 30, 100, 120,116,200,222,10};
    final String title[] = {"맛있는 감자탕", "짬뽕먹고싶다!", "만두만들기", "연근조림만드는법", "볶음밥레시피!", "초간단 김밥","그냥저냥 요리","토마토마토","고치돈","새우깡을 만들어보자"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_recipe);

        RecyclerView rv = findViewById(R.id.rv);
        Button writing = findViewById(R.id.writing);

        ArrayList<RecipeListData> data = new ArrayList<>();

        int i = 0;
        while (i < title.length) {
            data.add(new RecipeListData(image[i], cnt[i], title[i]));
            i++;
        }
        adapter = new RecipeRecyclerAdapter();
        adapter.setData(data);
        rv.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(layoutManager);

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.extend();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "랜덤추천", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Random r = new Random();
                int d = r.nextInt(10);
                Intent intent = new Intent(RecipeBoardActivity.this, RecipeRandom.class);
                intent.putExtra("img",image[d]);
                intent.putExtra("title",title[d]);
                startActivity(intent);
            }
        });


        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeBoardActivity.this, WritingActivity.class);
                startActivity(intent);

            }
        });



    }
}
