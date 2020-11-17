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
    Button btn, writing;
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

        btn = findViewById(R.id.btn);
        writing = findViewById(R.id.writing);
        medit = findViewById(R.id.medit);
        cedit = findViewById(R.id.cedit);
        title2 = findViewById(R.id.title2);
        title = findViewById(R.id.title);
        day_text = findViewById(R.id.day_text);
        day_text.setFocusable(false);

        Intent intent = getIntent();
        address = intent.getStringExtra("주소");
        a = intent.getIntExtra("체크", 0);
        btn.setText(address);
        Log.i("List", "->1");
        Log.i("List", "->" + a);

     /*  Intent intent2 = getIntent();
        a = intent2.getIntExtra("체크", 0);
        Log.i("List", "->3");
        Log.i("List", "->->" + a);


        if(a == 1) {
            Log.i("List", "->4");
            List<Address> list = null;

            String str = intent.getStringExtra("주소");
            Log.i("List", "->.주소는" + str);

            try {

                Geocoder geocoder = new Geocoder(this);
                list = geocoder.getFromLocationName(str, 10); // 지역이름, 읽을 개수

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }

            if (list != null) {
                if (list.size() == 0) {
                    Toast.makeText(RoomWriting.this, "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG);
                } else {

                    Toast.makeText(RoomWriting.this, list.get(0).toString(), Toast.LENGTH_LONG);

                    //          list.get(0).getCountryName();  // 국가명
                    //          list.get(0).getLatitude();        // 위도
                    double abc = list.get(0).getLongitude();    // 경도
                    Log.i("결과는", "->" + abc);
                }
            }
        }*/

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomWriting.this, DaumWebViewActivity.class);
                startActivity(intent);
            }
        });

        //달력 날짜 선택
        day_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RoomWriting.this, datepicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_title = title.getText().toString();
                price = medit.getText().toString();
                post_con = cedit.getText().toString();
                date = day_text.getText().toString();
                //date = year + "-" + month + "-" + day;

                if(title2.getText().toString().equals("보금자리")){
                    board_code = 88;
                }

                if (address==null || address.equals("") || price.equals("") || post_con.equals("") || date.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RoomWriting.this);
                    dialog = builder.setMessage("글 작성이 완료되지 않았습니다.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else {
                    final Api api = Api.Factory.INSTANCE.create();
                    api.Write(LoginActivity.user_ac, post_title, post_con, board_code).enqueue(new Callback<Write>() {
                        @Override
                        public void onResponse(Call<Write> call, Response<Write> response) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomWriting.this);
                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                                    .create();
                            dialog.show();
                        }

                        @Override
                        public void onFailure(Call<Write> call, Throwable t) {
                            Log.i("작성실패", t.getMessage());
                        }
                    });


                    api.roomWrite(address, price, date).enqueue(new Callback<Room>() {
                        @Override
                        public void onResponse(Call<Room> call, Response<Room> response) {
                            Log.i("write", date);

                            AlertDialog.Builder builder = new AlertDialog.Builder(RoomWriting.this);
                            dialog = builder.setMessage("작성 완료됨").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                                    .create();
                            dialog.show();
                        }

                        @Override
                        public void onFailure(Call<Room> call, Throwable t) {
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


