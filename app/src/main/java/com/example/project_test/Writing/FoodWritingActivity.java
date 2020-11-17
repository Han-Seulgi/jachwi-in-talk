package com.example.project_test.Writing;

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
import com.example.project_test.DaumWebViewActivity;
import com.example.project_test.Food.FoodPostList;
import com.example.project_test.Food.PostData;
import com.example.project_test.FoodWrite;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;
import com.example.project_test.Write;
import com.example.project_test.likeCheck;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodWritingActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button lctbtn, writing;
    EditText tedit, cedit;
    TextView tv1, tv2 , title2;
    String post_title, post_con, food_lct;
    int board_code;

    private AlertDialog dialog;

    private final int GET_LOCATION = 888;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_food);

        lctbtn = findViewById(R.id.lctbtn);
        writing = findViewById(R.id.writing);
        tedit = findViewById(R.id.tedit);
        cedit = findViewById(R.id.cedit);
        title2 = findViewById(R.id.title2);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        post_title = tedit.getText().toString();
        post_con = cedit.getText().toString();

        lctbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodWritingActivity.this, DaumWebViewActivity.class);
                intent.putExtra("request", GET_LOCATION);
                startActivityForResult(intent, GET_LOCATION);
            }
        });

        int request = getIntent().getIntExtra("request", -1);
        switch (request) {
            case 0: AlertDialog.Builder builder = new AlertDialog.Builder(FoodWritingActivity.this);
                dialog = builder.setMessage("작성 오류").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
                dialog.show(); break;
            case 100:
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post_title = tedit.getText().toString();
                post_con = cedit.getText().toString();
                food_lct = lctbtn.getText().toString();

                if(title2.getText().toString().equals("자취앤혼밥")){
                    board_code = 22;
                }
                Log.i("결과는", LoginActivity.user_ac + post_title + post_con + board_code);

                if (post_title.equals("") || post_con.equals("") || food_lct.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FoodWritingActivity.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else {
                    final Api api = Api.Factory.INSTANCE.create();
                    api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {
                            api.FoodWrite(food_lct).enqueue(new Callback<FoodWrite>() {
                                public void onResponse(Call<FoodWrite> call, Response<FoodWrite> response) {
                                    Log.i("결과는" , response.toString());

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
                                public void onFailure(Call<FoodWrite> call, Throwable t) {
                                    Log.i("작성실패", t.getMessage());
                                }
                            });

                            Log.i("결과는" , response.toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(FoodWritingActivity.this);
                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).create();
                            returnResult();
                            dialog.show();
                        }
                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("작성실패", t.getMessage());
                        }
                    });

                }
            }
        });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("foodloc", "requestcode: "+requestCode);
        Log.i("foodloc", "requestcode: "+requestCode+"resultcode"+resultCode);
        switch (requestCode) {
            case GET_LOCATION: if(resultCode == RESULT_OK){
                food_lct = data.getStringExtra("주소");
                lctbtn.setText(food_lct);

            }break;
        }
    }

    private void returnResult() {
        Api api = Api.Factory.INSTANCE.create();
        api.getFoodList(22).enqueue(new Callback<FoodPostList>() {
            @Override
            public void onResponse(Call<FoodPostList> call, Response<FoodPostList> response) {
                FoodPostList postList = response.body();
                List<com.example.project_test.Food.PostData> postData = postList.items;

                ArrayList<String> title1 = new ArrayList<>();
                ArrayList<String> day1 = new ArrayList<>();
                ArrayList<String> id1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();

                //리스트에 제목, 날짜, 작성자 아이디 넣기
                for (PostData d:postData) {
                    title1.add(d.post_title);
                    day1.add(d.post_day);
                    id1.add(d.id);
                    con1.add(d.post_con);
                    Log.i("foood","맛집 All: " + d.toString());
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                String title[] = title1.toArray(new String[title1.size()]);
                String day[] = day1.toArray(new String[day1.size()]);
                String[] id = id1.toArray(new String[id1.size()]);
                String[] con = con1.toArray(new String[con1.size()]);

                Intent intent = new Intent();
                intent.putExtra("title", title[0]);
                intent.putExtra("day", day[0]);
                intent.putExtra("id", id[0]);
                intent.putExtra("con", con[0]);

                setResult(RESULT_OK, intent);
                //finish();

            }

            public void onFailure(Call<FoodPostList> call, Throwable t) {
                Log.i("onfailure", t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodWritingActivity.this);
                dialog = builder.setMessage("작성 실패").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder logout = new AlertDialog.Builder(FoodWritingActivity.this);
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