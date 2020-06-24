package com.example.project_test;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.project_test.Food.FoodActivity;
import com.example.project_test.Info.InfoActivity;
import com.example.project_test.Meet.MeetActivity;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.qa.qaActivity;
import com.example.project_test.Recipe.RecipeBoardActivity;
import com.example.project_test.qa.qaContent.qaActivityContent;

public class BoardActivity extends AppCompatActivity {
    ViewFlipper vflip1, vflip2, vflip3;
    View vflipview1[] = new View[2], vflipview2[] = new View[2];
    ImageButton btn1, btn2, btn3, btn4, btn5, btn6, vimg1, vimg2;
    ImageButton imageButtons[] = {btn1, btn2, btn3, btn4, btn5, btn6};
    TextView v1txt1, v1txt2, v2txt1, v2txt2;
    Button changebtn;
    Toolbar toolbar;

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

        //첫 번째 탭의 게시 인기글 뷰 플리퍼
        int f1_images[] = {R.drawable.vflipimg, R.drawable.recipeimg2}; //뷰 플리퍼에 들어갈 게시판 이미지
        final String f1_text1[] = {"초간단 냉라면 레시피!!", "감자탕레시피"};  //뷰 플리퍼에 들어갈 텍스트(제목)
        final String f1_text2[] = {"요즘 너무 더워서 냉라면을\n 만들어먹어봤어요~", "집에서 감자탕 만들어먹어요"};  //뷰 플리퍼에 들어갈 텍스트(내용)

        for (int i = 0; i < f1_images.length; i++) {
            vflipview1[i] = (View) View.inflate(this, R.layout.view_item, null);
            //이미지 추가
            ImageView imageView = vflipview1[i].findViewById(R.id.view_img);
            imageView.setBackgroundResource(f1_images[i]);
            //텍스트 추가
            TextView textView1 = vflipview1[i].findViewById(R.id.view_title);
            textView1.setText(f1_text1[i]);
            TextView textView2 = vflipview1[i].findViewById(R.id.view_content);
            textView2.setText(f1_text2[i]);
            //뷰플리퍼에 뷰 추가
            vflip1.addView(vflipview1[i]);
        }
        //뷰플리퍼 시간, 애니메이션 설정
        vflip1.setFlipInterval(4000);   // 몇 초 후에 이미지가 넘어갈것인가(1000 당 1초니까 4초후에 넘어가는거야)
        vflip1.setAutoStart(true);      //자동시작유무(true:자동)
        vflip1.setInAnimation(this, android.R.anim.slide_in_left); //animation
        vflip1.setOutAnimation(this, android.R.anim.slide_out_right); //animation


        vflip1.setOnClickListener(new View.OnClickListener() { //뷰 플리퍼 클릭했을 때
            @Override
            public void onClick(View v) {
                int i = vflip1.getDisplayedChild();    //현재 페이지 가져오기

                Intent intent = new Intent(getApplicationContext(), qaActivityContent.class);
                intent.putExtra("제목", f1_text1[i]); //게시물의 제목
                startActivity(intent);

            }
        });

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


                    } else if (v.getId() == R.id.btn6) { //자취Q&A 게시판
                        Intent intent = new Intent(BoardActivity.this, qaActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }


        //첫 번째 탭의 방 구하기 게시물 뷰 플리퍼
        int f2_images[] = {R.drawable.roomimg1, R.drawable.roomimg2}; //뷰 플리퍼에 들어갈 이미지
        final String f2_text1[] = {"명지대 근처 원룸 내놓습니다!", "명전 10분 거리 원룸"};  //뷰 플리퍼에 들어갈 텍스트(제목)
        final String f2_text2[] = {"명지대 도보로 15분 정도 걸려요 벌레 없고 수압 좋습니다 ", "7월 20일에 방뺍니다"};  //뷰 플리퍼에 들어갈 텍스트(내용)

        for (int i = 0; i < f2_images.length; i++) {
            vflipview2[i] = (View) View.inflate(this, R.layout.view_item, null);
            //뷰플리퍼 안에 들어갈 뷰 레이아웃

            //이미지 추가
            ImageView imageView = vflipview2[i].findViewById(R.id.view_img);
            imageView.setBackgroundResource(f2_images[i]);

            //텍스트 추가
            TextView textView1 = vflipview2[i].findViewById(R.id.view_title);
            textView1.setText(f2_text1[i]);
            TextView textView2 = vflipview2[i].findViewById(R.id.view_content);
            textView2.setText(f2_text2[i]);

            //뷰플리퍼에 뷰 추가
            vflip2.addView(vflipview2[i]);
        }
        //뷰플리퍼 시간, 애니메이션 설정
        vflip2.setFlipInterval(4000);   // 몇 초 후에 이미지가 넘어갈것인가(1000 당 1초)
        vflip2.setAutoStart(true);      //자동시작유무(true:자동)
        vflip2.setInAnimation(this, android.R.anim.slide_in_left); //animation
        vflip2.setOutAnimation(this, android.R.anim.slide_out_right); //animation


        //첫 번째 탭의 방 구하기 게시물 뷰 플리퍼 클릭했을 때
        vflip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어떤 이미지 클릭했는지 확인하고 해당 게시물 화면으로 넘어가게
                int i = vflip2.getDisplayedChild();    //현재 페이지 가져오기

                Intent intent = new Intent(getApplicationContext(), qaActivityContent.class);
                intent.putExtra("제목", f2_text1[i]); //게시물의 제목
                startActivity(intent);
            }
        });

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
                //Log.d("iiii", String.valueOf(i));
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
            case android.R.id.message:
                //쪽지함 화면
                return true;
        }
        return true;
    }

}
