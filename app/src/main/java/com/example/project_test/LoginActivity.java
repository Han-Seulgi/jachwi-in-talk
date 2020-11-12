package com.example.project_test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.project_test.Mypage.KeywordList;

import java.util.ArrayList;

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
    ArrayList<String> kw = new ArrayList<>();


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
            case R.id.loginBtn:

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

                //키워드 알림
                api.getkeyword(LoginActivity.user_ac).enqueue(new Callback<KeywordList>() {
                    @Override
                    public void onResponse(Call<KeywordList> call, Response<KeywordList> response) {
                        KeywordList kl = response.body();

                        if (!kl.equals("")) {
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builder = null;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                String channelID = "channel_01";
                                String channelName = "MyChannel01";

                                NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);

                                notificationManager.createNotificationChannel(channel);
                                builder = new NotificationCompat.Builder(LoginActivity.this, channelID);
                            } else {
                                builder = new NotificationCompat.Builder(LoginActivity.this, null);
                            }
                            builder.setSmallIcon(android.R.drawable.ic_menu_view);

                            builder.setContentTitle("New 게시물");
                            builder.setContentText("키워드 알림");
                            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
                            builder.setLargeIcon(bm);

                            Notification notification = builder.build();

                            notificationManager.notify(1, notification);


                        }
                    }

                    @Override
                    public void onFailure(Call<KeywordList> call, Throwable t) {

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
