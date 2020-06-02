package com.example.project_test;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BoardActivity extends AppCompatActivity {
    ViewFlipper vflip1,vflip2;
    ImageButton btn1, btn2, btn3, btn4, btn5, btn6, img1, img2;
    ImageButton imageButtons[] = {btn1, btn2, btn3, btn4, btn5, btn6};
    Button changebtn;

    Toolbar toolbar;

    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);

        //상단탭
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.fish);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //메인화면
        vflip1 = findViewById(R.id.vflip1);
        vflip2 = findViewById(R.id.vflip2);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        changebtn = findViewById(R.id.changebtn);

        imageButtons[0] = findViewById(R.id.btn1);
        imageButtons[1] = findViewById(R.id.btn2);
        imageButtons[2] = findViewById(R.id.btn3);
        imageButtons[3] = findViewById(R.id.btn4);
        imageButtons[4] = findViewById(R.id.btn5);
        imageButtons[5] = findViewById(R.id.btn6);


        //첫 번째 탭의 게시 인기글 뷰 플리퍼
        int f1_images[] = {R.drawable.food, R.drawable.meet}; //뷰 플리퍼에 들어갈 이미지

        for(int image :f1_images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            vflip1.addView(imageView);      // 이미지 추가
            vflip1.setFlipInterval(4000);   // 몇 초 후에 이미지가 넘어갈것인가(1000 당 1초니까 4초후에 넘어가는거야)
            vflip1.setAutoStart(true);      //자동시작유무(true:자동)
            vflip1.setInAnimation(this,android.R.anim.slide_in_left); //animation
            vflip1.setOutAnimation(this,android.R.anim.slide_out_right); //animation
        }

        vflip1.setOnClickListener(new View.OnClickListener() { //뷰 플리퍼 클릭했을 때
            @Override
            public void onClick(View v) {
                //어떤 이미지 클릭했는지 확인하고 해당 게시물 화면으로 넘어가게
            }
        });

        //첫 번째 탭 6개의 게시판
        for(int i=0; i<imageButtons.length; i++) {
            imageButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.btn1) {
                        //자취앤집밥 게시판으로 넘어가기
                    }
                    else if (v.getId() == R.id.btn2) {
                        //자취인디자인 게시판으로 넘어가기
                    }
                    else if (v.getId() == R.id.btn3) {
                        //자취인만남 게시판으로 넘어가기
                    }
                    else if (v.getId() == R.id.btn4) {
                        //자취Q&A 게시판으로 넘어가기
                    }
                    else if (v.getId() == R.id.btn5) {
                        //자취인정보 게시판으로 넘어가기
                    }
                    else if (v.getId() == R.id.btn6) {
                        //자취인혼밥 게시판으로 넘어가기
                    }
                    else {
                        //예외처리
                    }
                }
            });
        }


        //첫 번째 탭의 방 구하기 게시물 뷰 플리퍼
        int f2_images[] = {R.drawable.food, R.drawable.meet}; //뷰 플리퍼에 들어갈 이미지

        for(int image :f2_images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            vflip2.addView(imageView);      // 이미지 추가
            vflip2.setFlipInterval(4000);   // 몇 초 후에 이미지가 넘어갈것인가(1000 당 1초)
            vflip2.setAutoStart(true);      //자동시작유무(true:자동)
            vflip2.setInAnimation(this,android.R.anim.slide_in_left); //animation
            vflip2.setOutAnimation(this,android.R.anim.slide_out_right); //animation
        }

        //첫 번째 탭의 방 구하기 게시물 뷰 플리퍼 클릭했을 때
        vflip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어떤 이미지 클릭했는지 확인하고 해당 게시물 화면으로 넘어가게
            }
        });

        //첫 번째 탭 나눔 HOT에서 다음 버튼을 눌렀을 때
        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //i는 0으로 초기화되어있음
                if ( i == 0 ){
                    img1.setImageResource(R.drawable.meet);
                    img2.setImageResource(R.drawable.board);
                    i--;
                }
                else if ( i == -1 ){
                    img1.setImageResource(R.drawable.naverlogo2);
                    img2.setImageResource(R.drawable.backbtn);
                    i--;
                }
                else {
                    img1.setImageResource(R.drawable.check);
                    img2.setImageResource(R.drawable.backbtn);
                    i=0;
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
                //마이페이지 화면
                return true;
            case android.R.id.message:
                //쪽지함 화면
                return true;
        }
        return true;
    }

}
