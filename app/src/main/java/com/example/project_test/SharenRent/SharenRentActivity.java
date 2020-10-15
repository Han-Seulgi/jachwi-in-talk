package com.example.project_test.SharenRent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.R;

import java.util.ArrayList;

public class SharenRentActivity extends AppCompatActivity {
    Toolbar toolbar;

    ImageButton goshare, gorent;
    TextView titletv1, titletv2;
    private RecyclerView rv1, rv2;
    private SRRecyclerAdapter adapter, adapter2;
    private LinearLayoutManager layoutManager;

    ArrayList<SRListData> data;
    ArrayList<SRListData> data2;

    //나눔데이터
    private  int[] img = {R.drawable.coffee, R.drawable.icetea, R.drawable.chocolate, R.drawable.food};
    private String[] title = {"커피", "아이스티", "초콜릿", "음식"};
    //대여데이터
    private  int[] img2 = {R.drawable.chocolate, R.drawable.rrimg1, R.drawable.rrimg2, R.drawable.rrimg3};
    private String[] title2 = {"초콜릿", "계란", "계란", "빵"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharenrent);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //나눔
        titletv1 = findViewById(R.id.titletv1);
        final String tabtitle1 = titletv1.getText().toString();
        goshare = findViewById(R.id.goshare);
        rv1 = findViewById(R.id.rv);
        rv1.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv1.setLayoutManager(layoutManager);

        adapter = new SRRecyclerAdapter();

        data = new ArrayList<>();
        int i = 0;
        while (i < title.length) {
            data.add(new SRListData(tabtitle1, img[i], title[i]));
            i++;
        }
        adapter.setData(data);
        rv1.setAdapter(adapter);


        //대여
        titletv2 = findViewById(R.id.titletv2);
        final String tabtitle2 = titletv2.getText().toString();
        gorent = findViewById(R.id.gorent);
        rv2 = findViewById(R.id.rv2);
        rv2.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv2.setLayoutManager(layoutManager);

        adapter2 = new SRRecyclerAdapter();

        data2 = new ArrayList<>();
        i = 0;
        while (i < title2.length) {
            data2.add(new SRListData(tabtitle2, img2[i], title2[i]));
            i++;
        }
        adapter2.setData(data2);
        rv2.setAdapter(adapter2);

        //버튼 클릭
        goshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShareActivity.class);
                intent.putExtra("탭이름", tabtitle1);
                v.getContext().startActivity(intent);
            }
        });

        gorent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RentActivity.class);
                intent.putExtra("탭이름", tabtitle2);
                v.getContext().startActivity(intent);
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
