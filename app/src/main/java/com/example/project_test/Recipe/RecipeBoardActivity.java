package com.example.project_test.Recipe;

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
import com.example.project_test.Writing.WritingActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class RecipeBoardActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView rv;
    private RecipeRecyclerAdapter adapter;
    private GridLayoutManager layoutManager;

    final int image[] = {R.drawable.recipeimg2, R.drawable.recipeimg3, R.drawable.recipeimg4, R.drawable.recipeimg5, R.drawable.recipeimg6, R.drawable.recipeimg7,R.drawable.recipeimg1, R.drawable.recipeimg8, R.drawable.recipe,R.drawable.food2};
    int cnt[] = {50, 10, 60, 30, 100, 120,116,200,222,10};
    final String title[] = {"맛있는 감자탕", "짬뽕먹고싶다!", "만두만들기", "연근조림만드는법", "볶음밥레시피!", "초간단 김밥","그냥저냥 요리","토마토마토","고치돈","새우깡을 만들어보자"};

    TextView tabTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_recipe);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabTitle = findViewById(R.id.title);
        String tt = tabTitle.getText().toString();

        RecyclerView rv = findViewById(R.id.rv);
        Button writing = findViewById(R.id.writing);

        ArrayList<RecipeListData> data = new ArrayList<>();

        int i = 0;
        while (i < title.length) {
            data.add(new RecipeListData(image[i], cnt[i], title[i],tt));
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
                Intent intent = new Intent(RecipeBoardActivity.this, RecipeRandom.class);
                intent.putExtra("img",image);
                intent.putExtra("title",title);
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
