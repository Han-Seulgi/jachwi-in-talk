package com.example.project_test.Meet.MeetContent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Cmt;
import com.example.project_test.CmtData;
import com.example.project_test.CmtList;
import com.example.project_test.CommentListData;
import com.example.project_test.CommentRecyclerAdapter;
import com.example.project_test.DeletePost;
import com.example.project_test.LoginActivity;
import com.example.project_test.Modify.MeetModifyActivity;
import com.example.project_test.PostList;
import com.example.project_test.R;
import com.example.project_test.likeCheck;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetActivityContent extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private CommentRecyclerAdapter adapter;
    int postcode, likenum, pnum , img1, position;
    TextView text1, writer, contents, textLikenum, locationtv, numtv, datetv;
    ImageView iv;
    ImageButton like, modify, delete;
    String title, content, location, date , cmt_con, tag, day, id;
    EditText editTextName1;
    Button push;

    ArrayList<CommentListData> data;

    private AlertDialog dialog;

    private final int MOD = 1000;
    String rtitle, rcon, rtag, rlct;
    int rpnum;
    //String rdate;
    boolean mod = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetcontent);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //댓글
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new CommentRecyclerAdapter();
        data = new ArrayList<>();

        text1 = findViewById(R.id.text1);
        writer = findViewById(R.id.id_day);
        contents = findViewById(R.id.con);
        locationtv = findViewById(R.id.location);
        numtv = findViewById(R.id.num);
        datetv = findViewById(R.id.date);
        like = findViewById(R.id.like);
        textLikenum = findViewById(R.id.textLikenum);
        modify = findViewById(R.id.modify);
        delete = findViewById(R.id.delete);
        editTextName1 = findViewById(R.id.editTextName1);
        push = findViewById(R.id.push);
        iv = findViewById(R.id.iv);

        //받아오기
        Intent intent = getIntent();
        title = intent.getStringExtra("제목");
        id = intent.getStringExtra("작성자");
        day = intent.getStringExtra("날짜");
        //int img = intent.getIntExtra("사진", 0);
        content = intent.getStringExtra("내용");

        //set
        text1.setText(title);
        writer.setText(id+"\n"+day);
        contents.setText(content);

        //현재 접속한 아이디와 글 작성자가 같으면 수정, 삭제 가능
        if( id.equals(LoginActivity.user_ac)) {
            modify.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            Log.i("recyclerview","글 아이디: "+id+"접속아이디: "+LoginActivity.user_ac);
        }
        else {
            modify.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        //댓글 작성
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmt_con = editTextName1.getText().toString();

                Api api = Api.Factory.INSTANCE.create();
                api.Cmt(LoginActivity.user_ac,postcode,cmt_con).enqueue(new Callback<Cmt>() {
                    public void onResponse(Call<Cmt> call, Response<Cmt> response) {
                        Cmt cmt = response.body();
                        List<CmtData> cmtData = cmt.cmtdatas;

                        final ArrayList<Integer> cmtcode = new ArrayList<>();
                        final ArrayList<String> cmtday = new ArrayList<>();
                        for (CmtData d:cmtData) {
                            cmtcode.add(d.cmt_code);
                            cmtday.add(d.cmt_day);
                        }

                        Log.i("comment" , String.valueOf(cmtcode.get(0))+cmtday.get(0));

                        AlertDialog.Builder builder = new AlertDialog.Builder(MeetActivityContent.this);
                        dialog = builder.setMessage("작성하시겠습니까?").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.addData(new CommentListData(cmtcode.get(0), LoginActivity.user_ac, cmt_con, cmtday.get(0)));

                                Toast.makeText(getApplicationContext(),"작성완료",Toast.LENGTH_SHORT).show();
                            }
                        })
                                .create();
                        dialog.show();
                    }
                    public void onFailure(Call<Cmt> call, Throwable t) {
                        Log.i("작성실패", t.getMessage());
                    }

                });

            }
        });

        //제목 이용해서 게시글코드 가져오기
        final Api api = Api.Factory.INSTANCE.create();

        //제목으로 검색
        api.getcontent(title).enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList postlist = response.body();
                postcode = postlist.pcode;

                //게시글 코드로 카테고리, 위치, 인원수, 날짜 가져오기
                api.getmeetday(postcode).enqueue(new Callback<MeetList>() {
                    @Override
                    public void onResponse(Call<MeetList> call, Response<MeetList> response) {
                        MeetList meetList = response.body();

                        tag = meetList.tag;
                        location = meetList.lct;
                        date = meetList.day;
                        pnum = meetList.pnum;
                        locationtv.setText("위치:   "+location);
                        numtv.setText("인원:   "+pnum);
                        datetv.setText("날짜:   "+date);

                        //태그에 따라 사진 설정
                        if(tag.equals("운동")) img1=R.drawable.meeting2;
                        else if(tag.equals("음식")) img1=R.drawable.meeting4;
                        else if(tag.equals("영화")) img1=R.drawable.meeting6;
                        else if(tag.equals("독서")) img1=R.drawable.meeting1;
                        else if(tag.equals("공연/전시")) img1=R.drawable.meeting3;
                        else if(tag.equals("기타")) img1=R.drawable.meeting5;
                        iv.setImageResource(img1);
                    }

                    @Override
                    public void onFailure(Call<MeetList> call, Throwable t) {
                    }
                });

                //댓글 가져오기
                api.getComments(postcode).enqueue(new Callback<CmtList>() {
                    @Override
                    public void onResponse(Call<CmtList> call, Response<CmtList> response) {
                        CmtList cmtList = response.body();
                        List<CmtData> cmtData = cmtList.items;

                        ArrayList<Integer> cmt_code1 = new ArrayList<>();
                        ArrayList<String> cmt_id1 = new ArrayList<>();
                        ArrayList<String> cmt_con1 = new ArrayList<>();
                        ArrayList<String> cmt_day1 = new ArrayList<>();

                        for(CmtData d:cmtData) {
                            cmt_code1.add(d.cmt_code);
                            cmt_id1.add(d.id);
                            cmt_con1.add(d.cmt_con);
                            cmt_day1.add(d.cmt_day);
                            Log.i("cmt",d.toString());
                        }

                        Integer[] cmt_code = cmt_code1.toArray(new Integer[cmt_code1.size()]);
                        String[] cmt_id = cmt_id1.toArray(new String[cmt_id1.size()]);
                        String[] cmt_con = cmt_con1.toArray(new String[cmt_con1.size()]);
                        String[] cmt_day = cmt_day1.toArray(new String[cmt_day1.size()]);

                        int i = 0;
                        while (i<cmt_code.length){
                            data.add(new CommentListData(cmt_code[i],cmt_id[i],cmt_con[i],cmt_day[i]));
                            i++;
                        }
                        adapter.setData(data);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<CmtList> call, Throwable t) {

                    }
                });


                //추천수 설정
                api.getlikenum(postcode).enqueue(new Callback<likeCheck>() {
                    @Override
                    public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                        likeCheck likecheck = response.body();
                        likenum = likecheck.likenum;
                        //추천수 화면 출력
                        textLikenum.setText("" + likenum);
                    }

                    @Override
                    public void onFailure(Call<likeCheck> call, Throwable t) {
                    }
                });
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
            }
        });


        //추천 클릭
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //유저가 해당 게시글을 추천하였는지 확인
                api.validateLike(LoginActivity.user_ac, postcode).enqueue(new Callback<likeCheck>() {
                    @Override
                    public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                        likeCheck likecheck = response.body();
                        boolean validatelk = likecheck.validatelk;

                        //해당게시글을 추천한 적이 없다면 likes 테이블에 추가
                        if (!validatelk) {
                            Log.i("abcdefg", validatelk + "추천안함");
                            api.addlike(LoginActivity.user_ac, postcode).enqueue(new Callback<likeCheck>() {
                                @Override
                                public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                                    likeCheck likecheck = response.body();
                                    boolean cklk = likecheck.ckaddlk;
                                    Log.i("abcdefg", cklk + "추천입력성공");

                                    //likes 테이블에 추천코드, id, 게시글코드가 입력되었다면
                                    if (cklk) {
                                        //like_num 테이블의 추천수 +1
                                        api.addlikenum(postcode).enqueue(new Callback<likeCheck>() {
                                            @Override
                                            public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                                                likeCheck likecheck = response.body();
                                                boolean cklknum = likecheck.ckaddlikenum;

                                                Log.i("abcdefg", cklknum + "추천수증가성공");

                                                //추천수 화면 출력
                                                likenum++;
                                                textLikenum.setText("" + likenum);

                                                Toast.makeText(getApplicationContext(),"추천됨",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<likeCheck> call, Throwable t) {
                                            }
                                        });
                                    } else {
                                        Log.i("abcdefg", "추천테이블에 입력 실패");
                                    }
                                }
                                @Override
                                public void onFailure(Call<likeCheck> call, Throwable t) {
                                }
                            });
                        }

                        //추천한 게시물이면 추천 정보 삭제
                        else {
                            Log.i("abcdefg", "이미 추천한 게시글 추천 삭제");
                            api.deletelike(LoginActivity.user_ac, postcode).enqueue(new Callback<likeCheck>() {
                                @Override
                                public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                                    likeCheck likecheck = response.body();
                                    boolean cklk = likecheck.ckdellk;
                                    Log.i("abcdefg", cklk + "추천삭제성공");

                                    //likes 테이블에서 해당 추천 삭제되면
                                    if (cklk) {
                                        //like_num 테이블의 추천수 - 1
                                        api.sublikenum(postcode).enqueue(new Callback<likeCheck>() {
                                            @Override
                                            public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                                                likeCheck likecheck = response.body();
                                                boolean cklknum = likecheck.cksublikenum;

                                                Log.i("abcdefg", cklknum + "추천수감소성공");

                                                //추천수 화면 출력
                                                likenum--;
                                                textLikenum.setText("" + likenum);

                                                Toast.makeText(getApplicationContext(),"추천삭제됨",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<likeCheck> call, Throwable t) {
                                            }
                                        });
                                    } else {
                                        Log.i("abcdefg", "추천테이블에 입력 실패");
                                    }
                                }
                                @Override
                                public void onFailure(Call<likeCheck> call, Throwable t) {
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<likeCheck> call, Throwable t) {

                    }
                });
            }
        });

        //수정 클릭
        int request = getIntent().getIntExtra("requestmod", -1);
        int request2 = getIntent().getIntExtra("requestdel", -1);
        position = getIntent().getIntExtra("position",-1);
        Log.i("modifyrequest", String.valueOf(request));
        if (request == 200) {
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MeetActivityContent.this, MeetModifyActivity.class);
                    intent.putExtra("제목", title); //게시물의 제목
                    intent.putExtra("내용", content); //게시물의 내용
                    intent.putExtra("태그", tag);  //카테고리
                    intent.putExtra("위치", location);  //위치
                    intent.putExtra("인원", pnum);  //인원
                    intent.putExtra("날짜", date);  //날짜
                    intent.putExtra("게시글코드", postcode); //게시물의 코드
                    intent.putExtra("request", MOD);
                    startActivityForResult(intent, MOD);
                }
            });
        }
        if (request2 == 300) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    AlertDialog dialog;
                    dialog = builder.setMessage("게시물을 삭제하시겠습니까?").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Api api = Api.Factory.INSTANCE.create();

                                    api.deletepost(title).enqueue(new Callback<DeletePost>() {
                                        @Override
                                        public void onResponse(Call<DeletePost> call, Response<DeletePost> response) {

                                            Log.i("delete", "성공" + response);
                                            Toast.makeText(getApplicationContext(), "삭제됨", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.putExtra("position", position);
                                            intent.putExtra("rc", 2);
                                            setResult(RESULT_OK, intent);
                                            Log.i("refresh", "뒤로가기");
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<DeletePost> call, Throwable t) {
                                            Log.i("delete", t.getMessage());
                                        }
                                    });
                                }
                            }
                    ).create();
                    dialog.show();
                }
            });
        }

        //댓글
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent rdata) {
        super.onActivityResult(requestCode, resultCode, rdata);
        Log.i("meetcontents", "requestcode: " + requestCode + "resultcode" + resultCode);
        //if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case MOD: {
                if (resultCode == RESULT_OK) {
                    Log.i("refresh", "수정후화면");
                    rtitle = rdata.getStringExtra("title");
                    rcon = rdata.getStringExtra("con");
                    rtag = rdata.getStringExtra("tag");
                    rlct = rdata.getStringExtra("lct");
                    //rdate = rdata.getStringExtra("date");
                    rpnum = rdata.getIntExtra("pnum", 0);

                    //태그에 따라 사진 설정
                    if(rtag.equals("운동")) img1=R.drawable.meeting2;
                    else if(rtag.equals("음식")) img1=R.drawable.meeting4;
                    else if(rtag.equals("영화")) img1=R.drawable.meeting6;
                    else if(rtag.equals("독서")) img1=R.drawable.meeting1;
                    else if(rtag.equals("공연/전시")) img1=R.drawable.meeting3;
                    else if(rtag.equals("기타")) img1=R.drawable.meeting5;
                    iv.setImageResource(img1);

                    text1.setText(rtitle);
                    contents.setText(rcon);
                    locationtv.setText("위치:   "+rlct);
                    numtv.setText("인원:   "+rpnum);
                    //datetv.setText("날짜:   "+rdate);
                    mod = true;
                }
                break;
            }
        }
        //}
    }

    @Override
    public void onBackPressed() {
        if(mod){
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.putExtra("title", rtitle);
            intent.putExtra("id", id);
            intent.putExtra("day", day);
            intent.putExtra("con", rcon);
            intent.putExtra("img", img1);
            intent.putExtra("rc", 1);
            setResult(RESULT_OK, intent);
            Log.i("refresh", "뒤로가기");
            finish();}
        else finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if(mod){
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("title", rtitle);
                    intent.putExtra("id", id);
                    intent.putExtra("day", day);
                    intent.putExtra("con", rcon);
                    intent.putExtra("img", img1);
                    intent.putExtra("rc", 1);
                    setResult(RESULT_OK, intent);
                    Log.i("refresh", "뒤로가기");
                    finish();}
                else finish();
                return true;
        }
        return true;
    }
}
