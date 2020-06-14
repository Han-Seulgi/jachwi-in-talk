package com.example.project_test;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {

    ImageButton back;
    Button submit;
    EditText pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        back = findViewById(R.id.back);
        submit = findViewById(R.id.submit);
        pw = findViewById(R.id.pwET);

        //비밀번호 *모양
        //pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        //pw.setTransformationMethod(PasswordTransformationMethod.getInstance());

        back.setOnClickListener(new View.OnClickListener() {         //뒤로가기 눌렀을 경우 로그인 화면으로
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {        //완료버튼 눌렀을 경우 ...?
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
