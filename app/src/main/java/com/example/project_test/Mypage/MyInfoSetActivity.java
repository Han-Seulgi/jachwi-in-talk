package com.example.project_test.Mypage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.MySpinnerAdapter;
import com.example.project_test.R;
import com.example.project_test.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoSetActivity extends AppCompatActivity {

    Button pwchange, submit, cancle, outBtn;
    EditText idET, nameET, emailET;
    View pwchangeView;
    Spinner spinner;
    Boolean checkName = false, checkEmail = false;
    String name, email;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoset);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pwchange = findViewById(R.id.pwchng);
        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancel);
        outBtn = findViewById(R.id.outBtn);
        idET = findViewById(R.id.idET);
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        spinner = findViewById(R.id.emailSpinner);

        //이메일
        final ArrayList<String> items = new ArrayList<String>();
        items.add("naver.com");
        items.add("gmail.com");
        items.add("daum.net");
        items.add("hanmail.com");
        items.add("nate.com");
        items.add("선택하세요");

        MySpinnerAdapter adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //아이디는 변경 못함
        idET.setText(LoginActivity.user_ac);

        //아이디를 이용해 유저 정보 검색
        Api api = Api.Factory.INSTANCE.create();
        api.getUser(LoginActivity.user_ac).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                nameET.setText(user.name);
                String emailstr[] = user.email.split("@");
                emailET.setText(emailstr[0]);
                int cnt=0;
                for (String i:items) {
                    if(i.equals(emailstr[1])) {
                        spinner.setSelection(cnt);
                    }
                    cnt++;
                }
            }

            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        //취소버튼
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logout = new AlertDialog.Builder(MyInfoSetActivity.this);
                logout.setTitle("수정취소");
                logout.setMessage("정보수정을 취소하시겠습니까?");
                logout.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                logout.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                logout.show();
            }
        });

        pwchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder pwch = new AlertDialog.Builder(MyInfoSetActivity.this);
                pwchangeView = (View) View.inflate(MyInfoSetActivity.this, R.layout.pwchangedialog, null);
                pwch.setView(pwchangeView);

                pwch.setPositiveButton("변경", null);
                pwch.setNegativeButton("취소", null);

                pwch.show();
            }
        });


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
