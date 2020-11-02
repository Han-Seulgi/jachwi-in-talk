package com.example.project_test.Writing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.Info.InfoActivity;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;
import com.example.project_test.Write;
import com.example.project_test.likeCheck;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoWritingActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button writing;
    EditText tedit, cedit;
    TextView tv0, tv1 , title2;
    ImageView wimg;
    String post_title, post_con;
    int board_code;

    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_info);


        writing = findViewById(R.id.writing);
        tedit = findViewById(R.id.tedit);
        cedit = findViewById(R.id.cedit);
        title2 = findViewById(R.id.title2);


        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);
        wimg = findViewById(R.id.wimg);


        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //글쓰기 _올리기
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post_title = tedit.getText().toString();
                post_con = cedit.getText().toString();
                if(title2.getText().toString().equals("자취인정보")){
                    board_code = 77;
                }

                Log.i("결과는",LoginActivity.user_ac + post_title + post_con + board_code);


               if (post_title.equals("") || post_con.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoWritingActivity.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                else {
                  final Api api = Api.Factory.INSTANCE.create();
                     api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {

                            Log.i("결과는" , response.toString());

                        AlertDialog.Builder builder = new AlertDialog.Builder(InfoWritingActivity.this);
                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                                    .create();
                            dialog.show();

                            api.newlike().enqueue(new Callback<likeCheck>() {
                                @Override
                                public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                                    likeCheck lc = response.body();
                                    boolean newlk = lc.newlike;

                                    Log.i("aaaa" , newlk+"");
                                }

                                @Override
                                public void onFailure(Call<likeCheck> call, Throwable t) {

                                }
                            });
                        }
                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("작성실패", t.getMessage());
                        }

                    });

                }

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder logout = new AlertDialog.Builder(InfoWritingActivity.this);
                logout.setTitle("작성취소");
                logout.setMessage("작성을 취소하시겠습니까?");
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

                return true;
        }
        return true;

    }

}