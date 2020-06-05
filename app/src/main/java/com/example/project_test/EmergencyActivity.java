package com.example.project_test;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class EmergencyActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        GridView gv = findViewById(R.id.gv1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.fish);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                //마이페이지 화면
                return true;
            case android.R.id.message:
                //쪽지함 화면
                return true;
        }
        return true;
    }
}

class MyGridAdapter extends BaseAdapter {
    Context context;

    public MyGridAdapter(Context _context) {
        context = _context;
    }

    @Override
    public int getCount() {
        return iconID.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    Integer[] iconID = {R.drawable.meet, R.drawable.meet,R.drawable.meet,R.drawable.meet,R.drawable.meet,R.drawable.meet};

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageButton btnG = new ImageButton(context);
        btnG.setLayoutParams(new GridView.LayoutParams(300,300));
        btnG.setScaleType(ImageButton.ScaleType.FIT_CENTER);
        btnG.setPadding(5,5,5,5);
        btnG.setImageResource(iconID[position]);

        return btnG;
    }
}
