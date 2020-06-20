package com.example.project_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {
    Button userSetBtn, logout, addkwd;
    ImageButton gomycon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


    }

    public void mypageClick(View v) {
        switch (v.getId()) {
            case R.id.userSetBtn:          //내정보 수정 화면
                Intent intent = new Intent(MyPageActivity.this, MyInfoSetActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:          //로그아웃
                AlertDialog.Builder logout = new AlertDialog.Builder(MyPageActivity.this);
                logout.setTitle("로그아웃");
                logout.setMessage("로그아웃 하시겠습니까?");
                logout.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                logout.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                logout.show();

                break;
            case R.id.addkwd:           //키워드 추가
                //intent = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent);
                break;
            case R.id.go:             //내가 쓴글 목록 화면
                intent = new Intent(getApplicationContext(), MyContentsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
