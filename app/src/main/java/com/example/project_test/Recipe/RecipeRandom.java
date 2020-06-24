package com.example.project_test.Recipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.project_test.Content.ContentWithPicture;
import com.example.project_test.R;

import java.util.Random;

public class RecipeRandom extends Activity {
    TextView tv;
    ImageView iv;
    ImageButton btnClose;

    TextView showContent;
    TextView click;

    int img[], index;
    String title[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀바x
        setContentView(R.layout.activity_random);

        iv=findViewById(R.id.img);
        tv=findViewById(R.id.title);
        showContent = findViewById(R.id.showContent);
        click = findViewById(R.id.click);

        Intent intent = getIntent();
        img = intent.getIntArrayExtra("img"); // img[] 라는 배열에 게시물 이미지 저장
        title = intent.getStringArrayExtra("title"); // title[]이라는 배열에 게시물 제목 저장

        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void mOnRec(View v) { // 추천받기(다시추천) 버튼
        Random r = new Random();
        index = r.nextInt(10); // 게시물의 갯수 만큼의 범위에서 랜덤값 추출
        iv.setImageResource(img[index]);
        tv.setText(title[index]);
        tv.setVisibility(View.VISIBLE);

        showContent.setVisibility(View.VISIBLE);
        click.setText("다시 추천받기");
        click.setTextSize(20);
    }

    public void mOnShow(View v){ //레시피 보러가기 버튼
        Intent intent = new Intent(v.getContext(), ContentWithPicture.class);
        intent.putExtra("제목", title[index]); //게시물의 제목
        intent.putExtra("탭이름", "자취앤집밥"); //게시판의 제목
        v.getContext().startActivity(intent);
        finish();
    }

    //바깥레이어 클릭시에도 안닫힘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }
}
