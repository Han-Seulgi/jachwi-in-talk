package com.example.project_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShakeNumberActivity extends AppCompatActivity {

    Toolbar toolbar;

    Button monthup, monthdown;   //월 변경 버튼
    TextView month;    //월 출력 텍스트뷰

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shakenum);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        monthup = findViewById(R.id.monthup);
        monthdown = findViewById(R.id.monthdown);
        month = findViewById(R.id.month);

    }

    public void monthChange(View view) {
        String mtxt, rtxt;
        int i;

        mtxt = month.getText().toString();

        //문자열 잘라서 숫자로 변경
        rtxt = mtxt.substring(0, mtxt.length()-1);
        i = Integer.parseInt(rtxt);

        //월 증가
        if(view.getId() == R.id.monthup)
            if(++i <= 12)   //13까지 저장됨
                month.setText(i + "월");

        //월 감소
        if(view.getId() == R.id.monthdown)
            if(--i > 0)      //0까지 저장됨
                month.setText(i + "월");

    }
}
