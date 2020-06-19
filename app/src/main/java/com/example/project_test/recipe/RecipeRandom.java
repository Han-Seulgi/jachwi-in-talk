package com.example.project_test.recipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.project_test.R;

public class RecipeRandom extends Activity {
    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀바x
        setContentView(R.layout.activity_random);

        tv=findViewById(R.id.title);
        iv=findViewById(R.id.img);

        Intent intent = getIntent();
        int img = intent.getIntExtra("img",0);
        String title = intent.getStringExtra("title");
        tv.setText(title);
        iv.setImageResource(img);
    }

    public void mOnClose(View v) {

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
