package com.example.project_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static String user_ac = "abc";

    Button loginBtn, jnBtn;
    EditText idEt, pwEt;
    static public String strID;
    String strPW;
    AlertDialog.Builder loginfail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        jnBtn = findViewById(R.id.jnBtn);
        idEt = findViewById(R.id.idEt);
        pwEt = findViewById(R.id.pwEt);

    }

    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:          //로그인 버튼 누르면 메인화면으로
                Api api = Api.Factory.INSTANCE.create();

                //입력한 아이디와 패스워드 값 가져오기
                strID = idEt.getText().toString();
                strPW = pwEt.getText().toString();

                //입력한 아이디가 db에 존재하는 확인
                api.getID(strID).enqueue(new Callback<UserIdCheck>() {
                    @Override
                    public void onResponse(Call<UserIdCheck> call, Response<UserIdCheck> response) {
                        UserIdCheck id = response.body();
                        boolean ckid = id.ckid;

                        //입력한 아이디 존재
                        if(ckid) {
                            //Log.i("abcdefg", ckid+"존재함");

                            //입력한 패스워드와 서버에서 받아온 패스워드가 일치하는지 확인
                            Api api = Api.Factory.INSTANCE.create();
                            api.getUser(strID).enqueue(new Callback<User>() {
                                public void onResponse(Call<User> call, Response<User> response) {
                                    User user = response.body();
                                    String pw = user.password;

                                    //패스워드 일치
                                    if(pw.equals(strPW)) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                                        idEt.setText("");
                                        pwEt.setText("");
                                    }
                                    //패스워드 불일치
                                    else {
                                        //Log.i("abcdefg", "비밀번호틀림");
                                        loginfail = new AlertDialog.Builder(LoginActivity.this);
                                        loginfail.setTitle("비밀번호 불일치");
                                        loginfail.setMessage("잘못된 비밀번호입니다");
                                        loginfail.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        loginfail.show();
                                    }

                                }

                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.i("abcdefg", t.getMessage());
                                    loginfail = new AlertDialog.Builder(LoginActivity.this);
                                    loginfail.setTitle("서버 연결 실패");
                                    loginfail.setMessage("서버에 연결되지 않았습니다");
                                    loginfail.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    loginfail.show();
                                }
                            });
                        }
                        //아이디 없음
                        else {
                            //Log.i("abcdefg", ckid+"아이디없음");
                            loginfail = new AlertDialog.Builder(LoginActivity.this);
                            loginfail.setTitle("아이디 없음");
                            loginfail.setMessage("존재하지 않는 아이디입니다");
                            loginfail.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            loginfail.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserIdCheck> call, Throwable t) {
                        Log.i("abcdefg", t.getMessage());
                        loginfail = new AlertDialog.Builder(LoginActivity.this);
                        loginfail.setTitle("서버 연결 실패");
                        loginfail.setMessage("서버에 연결되지 않았습니다");
                        loginfail.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        loginfail.show();
                    }
                });
                break;
            case R.id.jnBtn:             //회원가입 버튼 누르면 회원가입 화면으로
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                break;
        }
    }
}
