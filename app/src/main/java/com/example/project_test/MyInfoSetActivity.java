package com.example.project_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MyInfoSetActivity extends AppCompatActivity {

    Button pwchange, submit, cancle, outBtn;
    View pwchangeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoset);

        pwchange = findViewById(R.id.pwchng);
        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancel);
        outBtn = findViewById(R.id.outBtn);

        pwchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder pwch = new AlertDialog.Builder(MyInfoSetActivity.this);
                pwchangeView = (View) View.inflate(MyInfoSetActivity.this, R.layout.pwchangedialog, null);
                pwch.setView(pwchangeView);

                pwch.setPositiveButton("변경", null);
                pwch.setNegativeButton("취소", null);

                pwch.show();
            }
        });


    }
}
