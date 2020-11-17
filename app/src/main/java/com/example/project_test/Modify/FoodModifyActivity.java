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

public class FoodModifyActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button lctbtn, writing;
    EditText tedit, cedit;
    TextView tv0;
    String post_title, post_con, food_lct;
    int post_code;

    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_food);

        lctbtn = findViewById(R.id.lctbtn);
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
        food_lct = intent.getStringExtra("위치");
        post_code = intent.getIntExtra("게시글코드", 0);
        tedit.setText(post_title);
        lctbtn.setText(food_lct);
        cedit.setText(post_con);

        //글쓰기 _올리기
        int request = getIntent().getIntExtra("request", -1);
        Log.i("modifyrequest", String.valueOf(request));
        switch (request) {
//            case 0: AlertDialog.Builder builder = new AlertDialog.Builder(RecipeModifyActivity.this);
//                dialog = builder.setMessage("수정 오류").setNegativeButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                }).create();
//                dialog.show(); break;
            case 1000:

        //글쓰기 _올리기
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post_title = tedit.getText().toString();
                post_con = cedit.getText().toString();
                food_lct = lctbtn.getText().toString();

                if (post_title.equals("") || post_con.equals("") || food_lct.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FoodModifyActivity.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else {
                    final Api api = Api.Factory.INSTANCE.create();

                    api.Modify(post_title, post_con, post_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {

                            Write write = response.body();

                            api.ModifyFood(food_lct, post_code).enqueue(new Callback<Write>() {
                                @Override
                                public void onResponse(Call<Write> call, Response<Write> response) {
                                    Write write = response.body();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(FoodModifyActivity.this);
                                    dialog = builder.setMessage("수정 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            returnResult();
                                            finish();
                                        }
                                    })
                                            .create();
                                    dialog.show();
                                }

                                @Override
                                public void onFailure(Call<Write> call, Throwable t) {
                                    Log.i("수정실패", t.getMessage());
                                }
                            });
                        }

                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("수정실패", t.getMessage());
                        }

                    });
                }

            }
        });break;}
    }

    private void returnResult() {
        post_title = tedit.getText().toString();
        post_con = cedit.getText().toString();
        food_lct = lctbtn.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("title", post_title);
        intent.putExtra("con", post_con);
        intent.putExtra("lct", food_lct);

        setResult(RESULT_OK, intent);
        Log.i("foodmodifyact", "수정");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder logout = new AlertDialog.Builder(FoodModifyActivity.this);
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
