package com.example.project_test.Room;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.DaumWebViewActivity;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;
import com.example.project_test.Write;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoomWriting extends AppCompatActivity {
    Toolbar toolbar;
    Button lctbtn, writing;
    String address,post_title;
    EditText medit, cedit,title;
    TextView title2;
    String post_con,price;
    int board_code;
    AlertDialog dialog;
    EditText day_text;
    String year,month,day;
    String date;
    int a;

    private final int GET_LOCATION = 999;

    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(R.id.day_text);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_room);


        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        lctbtn = findViewById(R.id.lctbtn);
        writing = findViewById(R.id.writing);
        medit = findViewById(R.id.medit);
        cedit = findViewById(R.id.cedit);
        title2 = findViewById(R.id.title2);
        title = findViewById(R.id.title);
        day_text = findViewById(R.id.day_text);
        day_text.setFocusable(false);

        post_title = title.getText().toString();
        price = medit.getText().toString();
        post_con = cedit.getText().toString();
        date = day_text.getText().toString();


        lctbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomWriting.this, DaumWebViewActivity.class);
                intent.putExtra("request", GET_LOCATION);
                startActivityForResult(intent, GET_LOCATION);
            }
        });

        //달력 날짜 선택
        day_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RoomWriting.this, datepicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        int request = getIntent().getIntExtra("request", -1);
        switch (request) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomWriting.this);
                dialog = builder.setMessage("작성 오류").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
                dialog.show();
                break;
            case 100:
                writing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        post_title = title.getText().toString();
                        price = medit.getText().toString();
                        post_con = cedit.getText().toString();
                        date = day_text.getText().toString();
                        //date = year + "-" + month + "-" + day;

                        if (title2.getText().toString().equals("보금자리")) {
                            board_code = 88;
                        }

                        if (address == null || address.equals("") || price.equals("") || post_con.equals("") || date.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomWriting.this);
                            dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                                    .create();
                            Log.i("Room",address+price+post_con+date);
                            dialog.show();
                            return;
                        } else {
                            final Api api = Api.Factory.INSTANCE.create();
                            api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                                @Override
                                public void onResponse(Call<Write> call, Response<Write> response) {

                                    api.roomWrite(address, price, date).enqueue(new Callback<Room>() {
                                        @Override
                                        public void onResponse(Call<Room> call, Response<Room> response) {
                                            Log.i("write", date);

                                        }

                                        @Override
                                        public void onFailure(Call<Room> call, Throwable t) {
                                            Log.i("작성실패", t.getMessage());
                                        }
                                    });

                                    AlertDialog.Builder builder = new AlertDialog.Builder(RoomWriting.this);
                                    dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                            .create(); returnResult();
                                    dialog.show();
                                }

                                @Override
                                public void onFailure(Call<Write> call, Throwable t) {
                                    Log.i("작성실패", t.getMessage());
                                }
                            });
                        }


                    }
                });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("roomrequest", "requestcode: "+requestCode+"resultcode"+resultCode);
        switch (requestCode) {
            case GET_LOCATION: if(resultCode == RESULT_OK){
                address = data.getStringExtra("주소");
                lctbtn.setText(address);

            }break;
    }
    }

    private void returnResult() {
        Intent intent = new Intent();
        setResult(RESULT_OK);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder logout = new AlertDialog.Builder(RoomWriting.this);
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

    private void updateLabel(int id) {
        String myFormat = "yyyy-MM-dd";  //출력형식
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText edit = findViewById(id);
        edit.setText(sdf.format(calendar.getTime()));
    }
}


