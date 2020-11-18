package com.example.project_test.Food.FoodContent;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.project_test.Modify.FoodModifyActivity;
import com.example.project_test.PostList;
import com.example.project_test.R;
import com.example.project_test.likeCheck;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivityContent extends AppCompatActivity implements OnMapReadyCallback {
    MapFragment mapFragment;
    GoogleMap gMap;

    double latitude, longitude;

    Toolbar toolbar;

    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.Adapter adapter;
    TextView text1, writer, contents, textLikenum, lct;
    int postcode, likenum, position;
    ImageButton like, modify, delete;
    String title, content, location,cmt_con, id, day;
    EditText editTextName1;
    Button push;

    private AlertDialog dialog;

    private CommentRecyclerAdapter adapter;
    ArrayList<CommentListData> data;

    private final int MOD = 1000;

    String rtitle, rcon, rlct;

    boolean mod = false;

    boolean validatelk;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_foodcontent);

            //상단탭
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

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

            lct = findViewById(R.id.lct);

            data = new ArrayList<>();

            //받아오기
            Intent intent = getIntent();
            title = intent.getStringExtra("제목");
            id = intent.getStringExtra("작성자");
            day = intent.getStringExtra("날짜");
            content = intent.getStringExtra("내용");

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

                            Log.i("comment" , String.valueOf(cmtcode.get(0))+cmtday.get(0));

                            AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivityContent.this);
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

                    //게시글 코드로 위치 가져오기
                    api.getlocation(postcode).enqueue(new Callback<FoodList>() {
                        @Override
                        public void onResponse(Call<FoodList> call, Response<FoodList> response) {
                            FoodList foodList = response.body();
                            location = foodList.lct;
                            lct.setText(location);

                            Log.i("abcdef", location+"");
                        }

                        @Override
                        public void onFailure(Call<FoodList> call, Throwable t) {
                        }
                    });

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
                }

                @Override
                public void onFailure(Call<PostList> call, Throwable t) {
                }
            });


            //추천 클릭
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*//유저가 해당 게시글을 추천하였는지 확인
                    api.validateLike(LoginActivity.user_ac, postcode).enqueue(new Callback<likeCheck>() {
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

                                                    like.setImageResource(R.drawable.ic_like);
                                                    validatelk = false;

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
//                        @Override
//                        public void onFailure(Call<likeCheck> call, Throwable t) {
//
//                        }
//                    });
//                }
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
                        Intent intent = new Intent(FoodActivityContent.this, FoodModifyActivity.class);
                        intent.putExtra("제목", title); //게시물의 제목
                        intent.putExtra("내용", content); //게시물의 내용
                        intent.putExtra("위치", location); //위치
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

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent rdata) {
        super.onActivityResult(requestCode, resultCode, rdata);
        Log.i("foodcontents", "requestcode: " + requestCode + "resultcode" + resultCode);
        //if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case MOD: {
                if (resultCode == RESULT_OK) {
                    Log.i("refresh", "수정후화면");
                    rtitle = rdata.getStringExtra("title");
                    rcon = rdata.getStringExtra("con");
                    rlct = rdata.getStringExtra("lct");

                    text1.setText(rtitle);
                    contents.setText(rcon);
                    lct.setText(rlct);

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
                    intent.putExtra("rc", 1);
                    setResult(RESULT_OK, intent);
                    Log.i("refresh", "상단바 뒤로가기");
                    finish();}
                else finish();
                return true;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        gMap.getUiSettings().setZoomControlsEnabled(true);

        final Api api = Api.Factory.INSTANCE.create();
        //제목으로 검색
        api.getcontent(title).enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList postlist = response.body();
                postcode = postlist.pcode;

                //게시글 코드로 위치 가져오기
                api.getlocation(postcode).enqueue(new Callback<FoodList>() {
                    @Override
                    public void onResponse(Call<FoodList> call, Response<FoodList> response) {
                        FoodList foodList = response.body();
                        location = foodList.lct;
                        List<Address> list = null;
                        try {
                            Geocoder geocoder = new Geocoder(FoodActivityContent.this);
                            list = geocoder.getFromLocationName(location, 10);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                        }

                        if (list != null) {
                            if (list.size() == 0) {
                                Toast.makeText(FoodActivityContent.this, "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG);
                            } else {
                                latitude = list.get(0).getLatitude(); //위도
                                longitude = list.get(0).getLongitude(); // 경도
                                LatLng loc = new LatLng(latitude, longitude); //현재 위치로 바꿔보기
                                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
                            }
                        }

                        LatLng food = new LatLng(latitude, longitude);
                        LatLng latLng[] = new LatLng[]{food};
                        String titles[] = new String[]{title};

                        //위치 마커
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng[0]);
                        markerOptions.title(titles[0]);
                        markerOptions.snippet("맛집");
                        markerOptions.alpha(0.5f);

                        gMap.addMarker(markerOptions);

                        Log.i("foodlocation", location + "");
                    }

                    @Override
                    public void onFailure(Call<FoodList> call, Throwable t) {
                        Log.i("food", t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
            }
        });
    }
}

