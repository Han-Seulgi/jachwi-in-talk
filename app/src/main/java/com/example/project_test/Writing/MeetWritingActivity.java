package com.example.project_test.Writing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.Meet.MeetPostList;
import com.example.project_test.Meet.PostData;
import com.example.project_test.MeetWrite;
import com.example.project_test.MySpinnerAdapter;
import com.example.project_test.R;
import com.example.project_test.Write;
import com.example.project_test.likeCheck;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetWritingActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button writing;
    EditText tedit, cedit, wedit,nedit;
    TextView tv1, tv2 , tv3, tv4, title2;
    String post_title, post_con, meet_lct;
    int board_code, meet_p;
    String meet_tag;

    private AlertDialog dialog;

    Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_meet);

        writing = findViewById(R.id.writing);
        tedit = findViewById(R.id.tedit);
        cedit = findViewById(R.id.cedit);
        wedit = findViewById(R.id.wedit);
        nedit = findViewById(R.id.nedit);
        title2 = findViewById(R.id.title2);


        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);

        spinner = findViewById(R.id.meetSpinner);

        //분류 가져오기
        final ArrayList<String> items = new ArrayList<String>();

        Api api = Api.Factory.INSTANCE.create();
        api.getWritingCategory(33).enqueue(new Callback<WritingCategoryData>() {
            @Override
            public void onResponse(Call<WritingCategoryData> call, Response<WritingCategoryData> response) {
                Log.i("abced", "성공");

                WritingCategoryData cgList = response.body();
                List<Category> cg = cgList.items;

                //리스트에 넣기
                for (Category s : cg) {
                    items.add(s.tag);
                }
                items.add("선택");

                MySpinnerAdapter adapter = new MySpinnerAdapter(MeetWritingActivity.this, android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());
            }

            @Override
            public void onFailure(Call<WritingCategoryData> call, Throwable t) {
                Log.i("abced", t.getMessage());
            }
        });

//        items.add("운동");
//        items.add("음식");
//        items.add("영화");
//
//        MySpinnerAdapter adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item,items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(adapter.getCount());

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        int request = getIntent().getIntExtra("request", -1);
        switch (request) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(MeetWritingActivity.this);
                dialog = builder.setMessage("작성 오류").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
                dialog.show();
                break;
            case 100:
                writing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_title = tedit.getText().toString();
                        post_con = cedit.getText().toString();
                        meet_lct = wedit.getText().toString();
                        String p = nedit.getText().toString();
                        meet_tag = spinner.getSelectedItem().toString();

                        if (title2.getText().toString().equals("자취인만남")) {
                            board_code = 33;
                        }

                        meet_tag = (String) spinner.getSelectedItem();
//                if(spinner.getSelectedItem().toString().equals("운동")){ meet_tag = 3301; }
//                if(spinner.getSelectedItem().toString().equals("음식")){ meet_tag = 3302; }
//                if(spinner.getSelectedItem().toString().equals("영화")){ meet_tag = 3303; }
                        Log.i("결과는", LoginActivity.user_ac + post_title + post_con + board_code);
                        if (post_title.equals("") || post_con.equals("") || meet_lct.equals("") || p.equals("") || meet_tag.equals("선택")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MeetWritingActivity.this);
                            dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null).create();
                            dialog.show();
                            return;
                        } else {
                            meet_p = Integer.parseInt(p);
                            final Api api = Api.Factory.INSTANCE.create();
                            api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                                public void onResponse(Call<Write> call, Response<Write> response) {
                                    Log.i("결과는", response.toString());
                                    //post_code가 제대로 저장되지 않아 바꿨음
                                    api.MeetWrite(meet_tag, meet_lct, meet_p).enqueue(new Callback<MeetWrite>() {
                                        public void onResponse(Call<MeetWrite> call, Response<MeetWrite> response) {
                                            Log.i("결과는", response.toString());
                                            AlertDialog.Builder builder = new AlertDialog.Builder(MeetWritingActivity.this);
                                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            }).create();
                                            dialog.show();
                                            returnResult();

                                            api.newlike().enqueue(new Callback<likeCheck>() {
                                                @Override
                                                public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                                                    likeCheck lc = response.body();
                                                    boolean newlk = lc.newlike;
                                                    Log.i("aaaa", newlk + "");
                                                }
                                                @Override
                                                public void onFailure(Call<likeCheck> call, Throwable t) {

                                                }
                                            });
                                        }
                                        public void onFailure(Call<MeetWrite> call, Throwable t) {
                                            Log.i("작성실패", t.getMessage());
                                        }
                                    });
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MeetWritingActivity.this);
////                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialog, int which) {
////                                    finish();
////                                }
////                            })
////                                    .create();
////                            dialog.show();
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

    private void returnResult() {
        final Api api = Api.Factory.INSTANCE.create();
        api.getMeetList(33).enqueue(new Callback<MeetPostList>() {
            @Override
            public void onResponse(Call<MeetPostList> call, Response<MeetPostList> response) {
                MeetPostList postList = response.body();
                List<PostData> postData = postList.items;

                final ArrayList<String> title1 = new ArrayList<>();
                final ArrayList<String> day1 = new ArrayList<>();
                final ArrayList<String> id1 = new ArrayList<>();
                final ArrayList<String> con1 = new ArrayList<>();
                final ArrayList<Integer> img1 = new ArrayList<>();

                //리스트에 제목, 날짜, 작성자 아이디, 내용 넣기
                for (com.example.project_test.Meet.PostData d:postData) {
                    title1.add(d.post_title);
                    day1.add(d.post_day);
                    id1.add(d.id);
                    con1.add(d.post_con);
                    Log.i("abc","모임 All: " + d.toString());
                }

                //리스트에 사진 넣기 -> 포스트를 가져올 때와 같이 작성날짜로 내림차순(검색조건 변경시 같이 변경해야 함)
                api.getMeetCategory().enqueue(new Callback<MeetPostList>() {
                    @Override
                    public void onResponse(Call<MeetPostList> call, Response<MeetPostList> response) {
                        MeetPostList postList = response.body();
                        List<com.example.project_test.Meet.PostData> postData = postList.items;

                        for(PostData t:postData) {
                            Log.i("abc"," " + t.tag_name);
                            if(t.tag_name.equals("운동")) img1.add(R.drawable.meeting2);
                            else if(t.tag_name.equals("음식")) img1.add(R.drawable.meeting4);
                            else if(t.tag_name.equals("영화")) img1.add(R.drawable.meeting6);
                            else if(t.tag_name.equals("독서")) img1.add(R.drawable.meeting1);
                            else if(t.tag_name.equals("공연/전시")) img1.add(R.drawable.meeting3);
                            else if(t.tag_name.equals("기타")) img1.add(R.drawable.meeting5);
                        }

                        //리스트를 배열로 바꾸기, 이미지 배열 생성
                        String[] title = title1.toArray(new String[title1.size()]);
                        String[] day = day1.toArray(new String[day1.size()]);
                        String[] id = id1.toArray(new String[id1.size()]);
                        String[] con = con1.toArray(new String[con1.size()]);

                        Intent intent = new Intent();
                        intent.putExtra("title", title[0]);
                        intent.putExtra("day", day[0]);
                        intent.putExtra("id", id[0]);
                        intent.putExtra("con", con[0]);

                        setResult(RESULT_OK, intent);
                    }

                    @Override
                    public void onFailure(Call<MeetPostList> call, Throwable t) {
                        Log.i("abc"," " + t.getMessage());
                    }
                });
            }

            public void onFailure(Call<MeetPostList> call, Throwable t) {
                Log.i("onfailure", t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(MeetWritingActivity.this);
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
                AlertDialog.Builder logout = new AlertDialog.Builder(MeetWritingActivity.this);
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
