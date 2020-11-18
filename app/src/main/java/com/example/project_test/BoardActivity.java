package com.example.project_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.Food.FoodActivity;
import com.example.project_test.Food.FoodContent.FoodActivityContent;
import com.example.project_test.Info.InfoActivity;
import com.example.project_test.Info.InfoContent.infoActivityContent;
import com.example.project_test.Meet.MeetActivity;
import com.example.project_test.Meet.MeetContent.MeetActivityContent;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.Recipe.RecipeBoardActivity;
import com.example.project_test.Room.RoomActivity;
import com.example.project_test.Room.RoomContent.RoomContentActivity;
import com.example.project_test.Room.RoomData;
import com.example.project_test.Room.RoomList;
import com.example.project_test.SharenRent.SharenRentActivity;
import com.example.project_test.qa.qaActivity;
import com.example.project_test.qa.qaContent.qaActivityContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivity extends AppCompatActivity {
    ViewFlipper vflip1, vflip2, vflip3;
    View[] vflipview1;
    View[] vflipview2 = new View[2];
    ImageButton btn1, btn2, btn3, btn4, btn5, btn6, vimg1, vimg2;
    ImageButton imageButtons[] = {btn1, btn2, btn3, btn4, btn5, btn6};
    TextView v1txt1, v1txt2, v2txt1, v2txt2;
    Button changebtn;
    Toolbar toolbar;
    Intent intent;
    public String[] roomlist;
    Random rnd;

    int i = 0, t = 0, j=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //메인화면
        vflip1 = findViewById(R.id.vflip1);
        vflip2 = findViewById(R.id.vflip2);
        vflip3 = findViewById(R.id.vflip3);
        vimg1 = findViewById(R.id.vimg1);
        vimg2 = findViewById(R.id.vimg2);
        v1txt1 = findViewById(R.id.v1txt1);
        v1txt2 = findViewById(R.id.v1txt2);
        v2txt1 = findViewById(R.id.v2txt1);
        v2txt2 = findViewById(R.id.v2txt2);

        changebtn = findViewById(R.id.changebtn);

        imageButtons[0] = findViewById(R.id.btn1);
        imageButtons[1] = findViewById(R.id.btn2);
        imageButtons[2] = findViewById(R.id.btn3);
        imageButtons[3] = findViewById(R.id.btn4);
        imageButtons[4] = findViewById(R.id.btn5);
        imageButtons[5] = findViewById(R.id.btn6);

        rnd = new Random();

        //첫 번째 탭의 게시 인기글 뷰 플리퍼
        final Api api = Api.Factory.INSTANCE.create();

        api.CheckCook().enqueue(new Callback<PopularPost>() {
            @Override
            public void onResponse(Call<PopularPost> call, Response<PopularPost> response) {

                PopularPost popularPost = response.body();
                List<PopularData> popularData = popularPost.items;

                final ArrayList<String> title1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();

                //리스트에 제목, 내용 넣기
                for (PopularData d:popularData) {
                    title1.add(d.post_title);
                    con1.add(d.post_con);
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                final String[] title = title1.toArray(new String[title1.size()]);
                final String[] con = con1.toArray(new String[con1.size()]);
                final Integer[] img = new Integer[title1.size()];

                for (int i = 0; i < img.length; i++) {

                    vflipview1 = new View[img.length];
                    vflipview1[i] = (View) View.inflate(BoardActivity.this, R.layout.view_item, null);
                    //뷰플리퍼 안에 들어갈 뷰 레이아웃

                    //텍스트 추가
                    TextView textView1 = vflipview1[i].findViewById(R.id.view_title);
                    textView1.setText(title[i]);
                    TextView textView2 = vflipview1[i].findViewById(R.id.view_content);
                    textView2.setText(con[i]);
                    final ImageView imageView = vflipview1[i].findViewById(R.id.view_img);
                    final int finalI = i;
                    api.getcontent(title[i]).enqueue(new Callback<PostList>() {
                        @Override
                        public void onResponse(Call<PostList> call, Response<PostList> response) {
                            PostList postlist = response.body();
                            int  bcode = postlist.bcode;

                            if (bcode == 11) { //자취앤집밥 게시판이라면
                                img[finalI] = R.drawable.vflip2;
                            } else if (bcode == 22) { //자취앤혼밥 게시판이라면
                                img[finalI] = R.drawable.vflip3;
                            } else if (bcode == 33) { //자취인만남 게시판이라면
                                img[finalI] = R.drawable.vflip1;
                            } else if (bcode == 66) { //자취Q&A 게시판이라면
                                img[finalI] = R.drawable.vflip5;
                            } else { //자취인정보 게시판이라면
                                img[finalI] = R.drawable.vflip4;
                            }
                            imageView.setBackgroundResource(img[finalI]);
                        }
                        @Override
                        public void onFailure(Call<PostList> call, Throwable t) {
                        }

                    });

                    //뷰플리퍼에 뷰 추가
                    vflip1.addView(vflipview1[i]);

                    vflip1.setOnClickListener(new View.OnClickListener() { //뷰 플리퍼 클릭했을 때
                        @Override
                        public void onClick(View v) {
                            final int i = vflip1.getDisplayedChild();    //현재 페이지 가져오기

                            api.getcontent(title[i]).enqueue(new Callback<PostList>() {
                                public void onResponse(Call<PostList> call, Response<PostList> response) {
                                    PostList postlist = response.body();
                                    String id = postlist.id;
                                    String day = postlist.day;
                                    int  bcode = postlist.bcode;

                                    if(bcode == 11) { //자취앤집밥 게시판이라면
                                        intent = new Intent(getApplicationContext(), ContentWithPicture.class);
                                    }
                                    else if(bcode == 22) { //자취앤혼밥 게시판이라면
                                        intent = new Intent(getApplicationContext(), FoodActivityContent.class);
                                    }
                                    else if(bcode == 33) { //자취인만남 게시판이라면
                                        intent = new Intent(getApplicationContext(), MeetActivityContent.class);
                                    }
                                    else if(bcode == 66) { //자취Q&A 게시판이라면
                                        intent = new Intent(getApplicationContext(), qaActivityContent.class);
                                    }
                                    else { //자취인정보 게시판이라면
                                        intent = new Intent(getApplicationContext(), infoActivityContent.class);
                                    }
                                    intent.putExtra("제목", title[i]);
                                    intent.putExtra("작성자", id);
                                    intent.putExtra("날짜", day);
                                    intent.putExtra("내용", con[i]);
                                    startActivity(intent);

                                }
                                public void onFailure(Call<PostList> call, Throwable t) {
                                    Log.i("실패", t.getMessage());
                                }

                            });
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<PopularPost> call, Throwable t) {
                Log.i("실패", t.getMessage());
            }

        });

        //뷰플리퍼 시간, 애니메이션 설정
        vflip1.setFlipInterval(4000);   // 몇 초 후에 이미지가 넘어갈것인가(1000 당 1초)
        vflip1.setAutoStart(true);      //자동시작유무(true:자동)
        vflip1.setInAnimation(BoardActivity.this, android.R.anim.slide_in_left); //animation
        vflip1.setOutAnimation(BoardActivity.this, android.R.anim.slide_out_right); //animation

        //첫 번째 탭 6개의 게시판
        for (int i = 0; i < imageButtons.length; i++) {
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.btn1) { //자취앤집밥 게시판
                        Intent intent = new Intent(BoardActivity.this, RecipeBoardActivity.class);
                        startActivity(intent);
                    } else if (v.getId() == R.id.btn2) { //자취앤혼밥 게시판
                        Intent intent = new Intent(BoardActivity.this, FoodActivity.class);
                        startActivity(intent);

                    } else if (v.getId() == R.id.btn3) { //자취인만남 게시판
                        Intent intent = new Intent(BoardActivity.this, MeetActivity.class);
                        startActivity(intent);

                    } else if (v.getId() == R.id.btn4) { //자취인정보 게시판
                        Intent intent = new Intent(BoardActivity.this, InfoActivity.class);
                        startActivity(intent);

                    } else if (v.getId() == R.id.btn5) {  //나눔대여 게시판
                        Intent intent = new Intent(BoardActivity.this, SharenRentActivity.class);
                        startActivity(intent);

                    } else if (v.getId() == R.id.btn6) { //자취Q&A 게시판
                        Intent intent = new Intent(BoardActivity.this, qaActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        //첫 번째 탭의 방 구하기 게시물 뷰 플리퍼
        final int f2_images[] = {R.drawable.roomimg1, R.drawable.roomimg2, R.drawable.roomimg3, R.drawable.roomimg4}; //뷰 플리퍼에 들어갈 이미지

        api.getAllRoom().enqueue(new Callback<RoomList>() {
            @Override
            public void onResponse(Call<RoomList> call, Response<RoomList> response) {

                RoomList rlist = response.body();
                List<RoomData> rooms = rlist.items;

                roomlist = new String[rooms.size()];

                final ArrayList<String> post_code = new ArrayList<>();
                final ArrayList<String> post_title = new ArrayList<>();
                final ArrayList<String> post_con = new ArrayList<>();
                final ArrayList<String> id = new ArrayList<>();
                final ArrayList<String> room_lct = new ArrayList<>();
                final ArrayList<String> room_day = new ArrayList<>();

                int j=0;
                for(RoomData d:rooms) {
                    post_code.add(d.post_code);
                    post_title.add(d.post_title);
                    post_con.add(d.post_con);
                    id.add(d.id);
                    room_lct.add(d.room_lct);
                    room_day.add(d.room_day);
                    Log.e("roomlist", d.toString());
                    j++;
                }
                final Integer[] img = {R.drawable.roomimg1, R.drawable.roomimg2};
                final String[] post_title1 = post_con.toArray(new String[post_title.size()]);
                final String[] post_con1 = post_con.toArray(new String[post_con.size()]);
                final String[] room_lct1 = room_lct.toArray(new String[room_lct.size()]);
                final String[] post_code1 = post_code.toArray(new String[post_code.size()]);
                final Integer[] room_day1 = new Integer[room_day.size()];

                for(int i=0; i<post_con.size(); i++) {
                    vflipview2 = new View[post_con.size()];
                    vflipview2[i] = (View) View.inflate(BoardActivity.this, R.layout.view_item, null);

                    TextView textView1 = vflipview2[i].findViewById(R.id.view_title);
                    textView1.setText(post_title1[i]);
                    TextView textView2 = vflipview2[i].findViewById(R.id.view_content);
                    textView2.setText(room_lct1[i]);
                    int ran = rnd.nextInt(img.length);
                    final ImageView imageView = vflipview2[i].findViewById(R.id.view_img);
                    imageView.setBackgroundResource(img[ran]);
                    vflip2.addView(vflipview2[i]);

                }

                vflip2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //제목, 아이디, 만료날짜, 내용
                        final int k = vflip2.getDisplayedChild();
                        Intent intent = new Intent(BoardActivity.this, RoomContentActivity.class);
                        intent.putExtra("제목", post_title.get(k));
                        intent.putExtra("아이디", id.get(k));
                        intent.putExtra("만료날짜", room_day.get(k));
                        intent.putExtra("내용", post_con.get(k));
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<RoomList> call, Throwable t) {
                Log.i("실패", t.getMessage());
            }
        });


        //뷰플리퍼 시간, 애니메이션 설정
        vflip2.setFlipInterval(4000);   // 몇 초 후에 이미지가 넘어갈것인가(1000 당 1초)
        vflip2.setAutoStart(true);      //자동시작유무(true:자동)
        vflip2.setInAnimation(this, android.R.anim.slide_in_left); //animation
        vflip2.setOutAnimation(this, android.R.anim.slide_out_right); //animation*/


        //첫 번째 탭의 나눔 HOT 뷰 플리퍼
        final int f3_images[] = {R.drawable.icetea1, R.drawable.orange, R.drawable.shareimg3, R.drawable.chocolate, R.drawable.coke, R.drawable.coffee}; //뷰 플리퍼에 들어갈 이미지
        final String f3_text1[] = {"아이스티", "오렌지", "물", "초콜릿", "콜라", "커피"};  //뷰 플리퍼에 들어갈 텍스트(제목)
        final String f3_text2[] = {"1000원", "2000원", "100원", "20000원", "3000원", "20원"};  //뷰 플리퍼에 들어갈 텍스트(가격)

        //첫 번째 탭 나눔 HOT에서 다음 버튼을 눌렀을 때
        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //i, t, j는 0으로 초기화되어있음
                //배열에 있는 값 차례로 가져와서 넣음
                vimg1.setImageResource(f3_images[i++]);
                v1txt1.setText(f3_text1[t++]);
                v1txt2.setText(f3_text2[j++]);

                vimg2.setImageResource(f3_images[i++]);
                v2txt1.setText(f3_text1[t++]);
                v2txt2.setText(f3_text2[j++]);
                if (i >= f3_images.length) {   //배열의 끝까지 왔으면 0으로 초기화해서 무한반복
                    i = 0;
                    t = 0;
                    j=0;
                }
            }
        });

    }

    //상단탭 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    //메뉴액션 --home:마이페이지 --message:쪽지함
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent mypage_itnt = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(mypage_itnt);
                return true;
            case R.id.mail:
                //쪽지함 화면
                Intent note_itnt = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(note_itnt);
                return true;
        }
        return true;
    }

}
