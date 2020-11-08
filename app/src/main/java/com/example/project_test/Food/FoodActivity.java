package com.example.project_test.Food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.Writing.FoodWritingActivity;

public class FoodActivity extends AppCompatActivity{
    Toolbar toolbar;
    SearchView search;

    private final int WRITE_POST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        search = findViewById(R.id.search);

        ViewPager pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        FoodAdapter adapter2 = new FoodAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter2);

        final Fragment FoodActivityTab1 = new FoodActivityTab1();
        adapter2.addItem(FoodActivityTab1);
        adapter2.notifyDataSetChanged();

        final Fragment FoodActivityTab2 = new FoodActivityTab2();
        adapter2.addItem(FoodActivityTab2);
        adapter2.notifyDataSetChanged();

        Button writing = findViewById(R.id.writing);

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FoodWritingActivity.class);
                intent.putExtra("request", WRITE_POST);
                FoodActivityTab1.startActivityForResult(intent, WRITE_POST);
            }
        });

        //검색
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("search", newText);
                ((com.example.project_test.Food.FoodActivityTab1) FoodActivityTab1).msearch(newText);
                ((com.example.project_test.Food.FoodActivityTab2) FoodActivityTab2).msearch(newText);
                return true;
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



