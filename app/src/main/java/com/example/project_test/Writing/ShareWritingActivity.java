package com.example.project_test.Writing;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.FoodWrite;
import com.example.project_test.LoginActivity;
import com.example.project_test.MySpinnerAdapter;
import com.example.project_test.R;
import com.example.project_test.Write;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareWritingActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button writing;
    EditText tedit, cedit, wedit, pedit;
    TextView tv1, tv2 , tv3, tv4, title2;
    String post_title, post_con;
    int board_code, share_p;

    private AlertDialog dialog;

    Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_share);

        writing = findViewById(R.id.writing);
        tedit = findViewById(R.id.tedit);
        cedit = findViewById(R.id.cedit);
        wedit = findViewById(R.id.wedit);
        pedit = findViewById(R.id.pedit);
        title2 = findViewById(R.id.title2);


        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);

        spinner = findViewById(R.id.shareSpinner);

        //분류 가져오기
        final ArrayList<String> items = new ArrayList<String>();

        Api api = Api.Factory.INSTANCE.create();
        api.getWritingCategory(44).enqueue(new Callback<WritingCategoryData>() {
            @Override
            public void onResponse(Call<WritingCategoryData> call, Response<WritingCategoryData> response) {
                Log.i("abced","성공");

                WritingCategoryData cgList = response.body();
                List<Category> cg = cgList.items;

                //리스트에 넣기
                for (Category s:cg) {
                    items.add(s.tag);
                }
                items.add("선택");

                MySpinnerAdapter adapter = new MySpinnerAdapter(ShareWritingActivity.this, android.R.layout.simple_spinner_item,items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(adapter.getCount());

            }
            @Override
            public void onFailure(Call<WritingCategoryData> call, Throwable t) {
                Log.i("abced",t.getMessage());
            }
        });


        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //올리기 버튼 클릭시
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post_title = tedit.getText().toString();
                post_con = cedit.getText().toString();

                if(title2.getText().toString().equals("나눔")){
                    board_code = 44;
                }

                Log.i("결과는", LoginActivity.user_ac + post_title + post_con + board_code);


                if (post_title.equals("") || post_con.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShareWritingActivity.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }


                else {


                    Api api = Api.Factory.INSTANCE.create();
                    api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                        public void onResponse(Call<Write> call, Response<Write> response) {

                            Log.i("결과는" , response.toString());

                            AlertDialog.Builder builder = new AlertDialog.Builder(ShareWritingActivity.this);
                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                                    .create();
                            dialog.show();
                        }
                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("작성실패", t.getMessage());
                        }

                    });


                }

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder logout = new AlertDialog.Builder(ShareWritingActivity.this);
                logout.setTitle("작성취소");
                logout.setMessage("작성을 취소하시겠습니까?");
                logout.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                logout.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                logout.show();

                return true;
        }
        return true;
    }
}
