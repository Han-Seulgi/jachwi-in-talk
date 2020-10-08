package com.example.project_test.Modify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.R;
import com.example.project_test.Write;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetModifyActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button writing;
    EditText tedit, cedit, nedit, wedit;
    TextView tv0;
    String post_title, post_con, meet_lct, meet_day;
    int post_code, meet_tag, meet_p;

    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_meet);

        writing = findViewById(R.id.writing);
        tedit = findViewById(R.id.tedit);
        cedit = findViewById(R.id.cedit);
        tv0 = findViewById(R.id.tv0);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv0.setText("수정하기");

        //글 제목, 내용
        Intent intent = getIntent();
        post_title = intent.getStringExtra("제목");
        post_con = intent.getStringExtra("내용");
//        meet_tag = intent.getIntExtra("태그", 3306);
//        meet_lct = intent.getStringExtra("위치");
//        meet_p = intent.getIntExtra("인원", 0);
//        meet_day = intent.getStringExtra("날짜");
        post_code = intent.getIntExtra("게시글코드", 0);
        tedit.setText(post_title);
        cedit.setText(post_con);

        //글쓰기 _올리기
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post_title = tedit.getText().toString();
                post_con = cedit.getText().toString();

                if (post_title.equals("") || post_con.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MeetModifyActivity.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else {
                    final Api api = Api.Factory.INSTANCE.create();

                    api.Modify(post_title, post_con, post_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {

                            Write write = response.body();
                            boolean insert = write.insert;
                            Log.i("abcdef", insert + "");
                            AlertDialog.Builder builder = new AlertDialog.Builder(MeetModifyActivity.this);
                                    dialog = builder.setMessage("수정 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                            .create();
                                    dialog.show();


//                            api.ModifyMeet(meet_tag, meet_lct, meet_p, meet_day, post_code).enqueue(new Callback<Write>() {
//                                @Override
//                                public void onResponse(Call<Write> call, Response<Write> response) {
//                                    Write write = response.body();
//                                    boolean insert = write.insert;
//                                    Log.i("abcdef", insert + "");
//
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(MeetModifyActivity.this);
//                                    dialog = builder.setMessage("수정 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            finish();
//                                        }
//                                    })
//                                            .create();
//                                    dialog.show();
//                                }
//
//                                @Override
//                                public void onFailure(Call<Write> call, Throwable t) {
//                                    Log.i("수정실패", t.getMessage());
//                                }
//                            });
                        }

                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("수정실패", t.getMessage());
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
                AlertDialog.Builder logout = new AlertDialog.Builder(MeetModifyActivity.this);
                logout.setTitle("수정취소");
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
