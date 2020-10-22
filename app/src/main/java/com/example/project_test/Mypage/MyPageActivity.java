package com.example.project_test.Mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.Mypage.MyContents.MyContentsActivity;
import com.example.project_test.R;
import com.example.project_test.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {
    Button userSetBtn, logout, addkwd;
    ImageButton gomycon, btn1,btn2, btn3, btn4, btn5;
    ConstraintLayout mplayout;
    TextView textName;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textName = findViewById(R.id.name);
        mplayout=(ConstraintLayout)findViewById(R.id.mplayout);


        //아이디를 이용해 유저 정보 검색
        Api api = Api.Factory.INSTANCE.create();
        api.getUser(LoginActivity.user_ac).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                textName.setText(user.name+" 님");
            }

            public void onFailure(Call<User> call, Throwable t) {

            }
        });

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
                        Intent i = new Intent(MyPageActivity.this , LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
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

            case R.id.btn2:
                mplayout.setBackgroundColor(getResources().getColor(R.color.red));
                break;

        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
