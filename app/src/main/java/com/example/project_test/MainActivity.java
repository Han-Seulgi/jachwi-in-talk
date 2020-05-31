package com.example.project_test;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends TabActivity {
    ViewFlipper vflip;
    ImageButton btn1, btn2, btn3, btn4, btn5, btn6;
    ImageButton imageButtons[] = {btn1, btn2, btn3, btn4, btn5, btn6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vflip = findViewById(R.id.vflip);
        imageButtons[0] = findViewById(R.id.btn1);
        imageButtons[1] = findViewById(R.id.btn2);
        imageButtons[2] = findViewById(R.id.btn3);
        imageButtons[3] = findViewById(R.id.btn4);
        imageButtons[4] = findViewById(R.id.btn5);
        imageButtons[5] = findViewById(R.id.btn6);

        //탭 만들기
        TabHost tabHost = getTabHost();

        ImageView tab1 = new ImageView(this);
        tab1.setImageResource(R.drawable.btn_star_big_on);

        ImageView tab2 = new ImageView(this);
        tab2.setImageResource(R.drawable.fish);

        TabSpec tabSpecTab1 = tabHost.newTabSpec("TAB1").setIndicator(tab1);
        tabSpecTab1.setContent(R.id.tab1);
        tabHost.addTab(tabSpecTab1);

        TabSpec tabSpecTab2 = tabHost.newTabSpec("TAB2").setIndicator(tab2);
        tabSpecTab2.setContent(R.id.tab2);
        tabHost.addTab(tabSpecTab2);

        TabSpec tabSpecTab3 = tabHost.newTabSpec("TAB3").setIndicator("행사행사행사^^");
        tabSpecTab3.setContent(R.id.tab3);
        tabHost.addTab(tabSpecTab3);

        TabSpec tabSpecTab4 = tabHost.newTabSpec("TAB4").setIndicator("안전알리미************");
        tabSpecTab4.setContent(R.id.tab4);
        tabHost.addTab(tabSpecTab4);

        tabHost.setCurrentTab(0);

        //첫 번째 탭의 뷰 플리퍼
        int images[] = {R.drawable.food, R.drawable.meet}; //뷰 플리퍼에 들어갈 이미지

        for(int image :images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            vflip.addView(imageView);      // 이미지 추가
            vflip.setFlipInterval(4000);   // 몇 초 후에 이미지가 넘어갈것인가(1000 당 1초)
            vflip.setAutoStart(true);      //자동시작유무(true:자동)
            vflip.setInAnimation(this,android.R.anim.slide_in_left); //animation
            vflip.setOutAnimation(this,android.R.anim.slide_out_right); //animation
        }

        vflip.setOnClickListener(new View.OnClickListener() { //뷰 플리퍼 클릭했을 때
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
                    if(i == 0) {
                        //자취앤집밥 게시판으로 넘어가기
                    }
                    else if (i == 1) {
                        //자취인디자인 게시판으로 넘어가기
                    }
                    else if (i == 2) {
                        //자취인만남 게시판으로 넘어가기
                    }
                    else if (i == 3) {
                        //자취Q&A 게시판으로 넘어가기
                    }
                    else if (i == 4) {
                        //자취인정보 게시판으로 넘어가기
                    }
                    else if (i == 5) {
                        //자취인혼밥 게시판으로 넘어가기
                    }
                    else {
                        //예외처리
                    }
                }
            });
        }
    }



}

