package com.example.project_test.Info.InfoContent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Comment.Cmt;
import com.example.project_test.Comment.CmtData;
import com.example.project_test.Comment.CmtList;
import com.example.project_test.Comment.CommentListData;
import com.example.project_test.Comment.CommentRecyclerAdapter;
import com.example.project_test.Delete.DeletePost;
import com.example.project_test.LoginActivity;
import com.example.project_test.Modify.InfoModifyActivity;
import com.example.project_test.PostList;
import com.example.project_test.R;
import com.example.project_test.likeCheck;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class infoActivityContent extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.Adapter adapter;
    TextView tabTitle, text1, writer, contents, textLikenum;
    int postcode, likenum, position;
    ImageButton like, modify, delete;
    String title, content,cmt_con, id, day, board_title;
    EditText editTextName1;
    Button push;

    private AlertDialog dialog;

    private CommentRecyclerAdapter adapter;
    ArrayList<CommentListData> data;

    private final int MOD = 1000;
    String rtitle, rcon;

    boolean mod = false;

    boolean validatelk;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_qacontent);

            //상단탭
            toolbar = findViewById(R.id.toolbar);
            TextView t = findViewById(R.id.title);
            t.setText("자취인정보");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            tabTitle = findViewById(R.id.title);
            //tabTitle.setText("자취인정보");

            //댓글
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            adapter = new CommentRecyclerAdapter();

            text1 = findViewById(R.id.text1);
            writer = findViewById(R.id.id_day);
            contents = findViewById(R.id.con);
            like = findViewById(R.id.like);
            textLikenum = findViewById(R.id.textLikenum);
            modify = findViewById(R.id.modify);
            delete = findViewById(R.id.delete);
            editTextName1 = findViewById(R.id.editTextName1);
            push = findViewById(R.id.push);

            data = new ArrayList<>();

            //받아오기
            Intent intent = getIntent();
            title = intent.getStringExtra("제목");
            id = intent.getStringExtra("작성자");
            day = intent.getStringExtra("날짜");
            content = intent.getStringExtra("내용");
            board_title = intent.getStringExtra("타이틀");
            //tabTitle.setText(board_title);


            //setText
            text1.setText(title);
            writer.setText(id+"\n"+day);
            contents.setText(content);

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

                            Log.i("infocomment" , String.valueOf(cmtcode.get(0))+cmtday.get(0));

                            AlertDialog.Builder builder = new AlertDialog.Builder(infoActivityContent.this);
                            dialog = builder.setMessage("작성하시겠습니까?").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    adapter.addData(new CommentListData(cmtcode.get(0), LoginActivity.user_ac, cmt_con, cmtday.get(0)));
                                    editTextName1.setText("");
                                    editTextName1.clearFocus();
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

            //제목 이용해서 게시글코드 가져오기
            final Api api = Api.Factory.INSTANCE.create();

            //제목으로 검색
            api.getcontent(title).enqueue(new Callback<PostList>() {
                @Override
                public void onResponse(Call<PostList> call, Response<PostList> response) {
                    PostList postlist = response.body();
                    postcode = postlist.pcode;


                    //유저가 해당 게시글을 추천하였는지 확인, 추천한 게시물은 하트 변경
                    api.validateLike(LoginActivity.user_ac, postcode).enqueue(new Callback<likeCheck>() {
                        @Override
                        public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                            likeCheck likecheck = response.body();
                            validatelk = likecheck.validatelk;

                            //추천 함
                            if (validatelk)
                                like.setImageResource(R.drawable.ic_like2);

                                //추천 안함
                            else
                                like.setImageResource(R.drawable.ic_like);
                        }
                        @Override
                        public void onFailure(Call<likeCheck> call, Throwable t) {

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
                                Log.i("infocmt",d.toString());
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
                /*api.validateLike(LoginActivity.user_ac, postcode).enqueue(new Callback<likeCheck>() {
                    @Override
                    public void onResponse(Call<likeCheck> call, Response<likeCheck> response) {
                        likeCheck likecheck = response.body();
                        boolean validatelk = likecheck.validatelk;*/

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

                                            like.setImageResource(R.drawable.ic_like2);
                                            validatelk = true;
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
                                like.setImageResource(R.drawable.ic_like);

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

                                            like.setImageResource(R.drawable.ic_like);
                                            validatelk = false;
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
//                    @Override
//                    public void onFailure(Call<likeCheck> call, Throwable t) {
//
//                    }
                //});
                // }
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
                        Intent intent = new Intent(infoActivityContent.this, InfoModifyActivity.class);
                        intent.putExtra("제목", title); //게시물의 제목
                        intent.putExtra("내용", content); //게시물의 내용
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

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent rdata) {
        super.onActivityResult(requestCode, resultCode, rdata);
        Log.i("infocontents", "requestcode: " + requestCode + "resultcode" + resultCode);
        //if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case MOD: {
                if (resultCode == RESULT_OK) {
                    Log.i("refresh", "수정후화면");
                    rtitle = rdata.getStringExtra("title");
                    rcon = rdata.getStringExtra("con");

                    text1.setText(rtitle);
                    contents.setText(rcon);
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
        intent.putExtra("board", "생활정보");
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
        int gid = item.getItemId();
        switch (gid) {
            case android.R.id.home:
                if(mod){
                Intent intent = new Intent();
                intent.putExtra("position", position);
                    intent.putExtra("title", rtitle);
                    intent.putExtra("id", id);
                    intent.putExtra("day", day);
                    intent.putExtra("con", rcon);
                    intent.putExtra("board", "생활정보");
                    intent.putExtra("rc", 1);
                setResult(RESULT_OK, intent);
                Log.i("refresh", "상단바뒤로가기");
                finish();}
                else finish();
                return true;
        }
        return true;
    }
}

