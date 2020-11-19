package com.example.project_test.Emergency;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.Mypage.KeywordList;
import com.example.project_test.Mypage.KwRecyclerAdapter;
import com.example.project_test.Mypage.MyInfoSetActivity;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergencySet extends AppCompatActivity {

    Button addmsg, setcall, syssubmit, msgtest, calltest;
    EditText editmsg, editcall;
    SeekBar sensor, volume;
    String callnum;

    private RecyclerView recyclerView;
    public LinearLayoutManager layoutManager;
    private MsgRecyclerAdapter adapter;

    ArrayList<String> mn = new ArrayList<>();

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eset);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        addmsg = findViewById(R.id.addmsg);
        editmsg=findViewById(R.id.editmsg);
        setcall = findViewById(R.id.setcall);
        editcall = findViewById(R.id.editcall);
        syssubmit = findViewById(R.id.syssubmit);
        sensor = findViewById(R.id.sensor);
        volume = findViewById(R.id.volume);
        msgtest = findViewById(R.id.msgtest);
        calltest = findViewById(R.id.calltest);
        recyclerView = findViewById(R.id.msglist);

        adapter = new MsgRecyclerAdapter();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //받아오기
        Intent intent = getIntent();

        final String ID = intent.getStringExtra("아이디");

        //자동 하이픈(-) 설정(핸드폰의 언어 기본설정이 한국어여야 함)
        editmsg.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        editcall.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        //전화번호, 시스템설정값 가져오기
        final Api api = Api.Factory.INSTANCE.create();
        api.getemergency(ID).enqueue(new Callback<MsgNumList>() {
            @Override
            public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                MsgNumList mnl = response.body();
                List<MsgNumData> mData = mnl.items;

                callnum = mData.get(0).callnum;
                int syssensor = mData.get(0).syssensor;
                int sysvolume = mData.get(0).sysvolume;

                editcall.setText(callnum);
                sensor.setProgress(syssensor);
                volume.setProgress(sysvolume);
            }

            @Override
            public void onFailure(Call<MsgNumList> call, Throwable t) {

            }
        });

        //문자번호 가져오기
        api.getmsgnum(ID).enqueue(new Callback<MsgNumList>() {
            @Override
            public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                MsgNumList mnl = response.body();
                List<MsgNumData> mData = mnl.items;

                for (MsgNumData d:mData) {
                    mn.add(d.msgnum);
                }
                adapter.setData(mn);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MsgNumList> call, Throwable t) {

            }
        });

        //문자연락처와 전화연락처 edittext가 포커스를 가졌을 때 입력된 값 지우기
        editmsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true)
                    editmsg.setText("");
            }
        });
        editcall.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true)
                    editcall.setText("");
            }
        });

        //+버튼 클릭
        addmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력된 내용 가져옴
                String strmsg = editmsg.getText().toString();
                editmsg.setText("");
                editmsg.clearFocus();

                if(strmsg.equals(""))
                {
                    AlertDialog dialog2;
                    AlertDialog.Builder builder = new AlertDialog.Builder(EmergencySet.this);
                    dialog2 = builder.setMessage("번호를 입력하세요").setNegativeButton("확인", null).create();
                    dialog2.show();
                }

                else {
                    //리스트에 넣고 리사이클러뷰에 추가
                    mn.add(strmsg);
                    adapter.notifyDataSetChanged();

                    //db에 넣기
                    api.addmsgnum(strmsg, ID).enqueue(new Callback<MsgNumList>() {
                        @Override
                        public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                            MsgNumList mnl = response.body();
                            Boolean update = mnl.update;

                            if (update) {
                                Log.i("넣기", update + "");
                                //키보드 내리기
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editcall.getWindowToken(), 0);
                            }
                        }

                        @Override
                        public void onFailure(Call<MsgNumList> call, Throwable t) {

                        }
                    });
                    Log.i("확인", mn.get(0));
                }
            }
        });

        //확인 버튼 클릭
        setcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력된 내용 가져옴
                String strcall = editcall.getText().toString();
                editcall.clearFocus();
                Log.i("전화", strcall);

                if(strcall.equals(""))
                {
                    AlertDialog dialog2;
                    AlertDialog.Builder builder = new AlertDialog.Builder(EmergencySet.this);
                    dialog2 = builder.setMessage("번호를 입력하세요").setNegativeButton("확인", null).create();
                    dialog2.show();
                }
                else {
                    //db에 입력
                    api.addcallnum(strcall, ID).enqueue(new Callback<MsgNumList>() {
                        @Override
                        public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                            MsgNumList mnl = response.body();
                            Boolean update = mnl.update;

                            if (update) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editcall.getWindowToken(), 0);

                            }
                        }

                        @Override
                        public void onFailure(Call<MsgNumList> call, Throwable t) {

                        }
                    });
                }
            }
        });

        //문자테스트 버튼
        msgtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //문자번호 가져오기
                api.getmsgnum(ID).enqueue(new Callback<MsgNumList>() {
                    @Override
                    public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                        MsgNumList mnl = response.body();
                        List<MsgNumData> mData = mnl.items;

                        for (MsgNumData d:mData) {
                            mn.add(d.msgnum);
                        }
                        if(mn.size() == 0) {

                            AlertDialog dialog;
                            AlertDialog.Builder builder = new AlertDialog.Builder(EmergencySet.this);
                            dialog = builder.setMessage("설정에서 연락처를 저장하세요").setNegativeButton("확인", null).create();
                            dialog.show();
                        }
                        else {
                            try {
                                //String to = "01000000000"; //마이페이지에서 설정한 문자 번호
                                String message = "서울특별시 서대문구 홍은2동 user 님이 위험에 빠졌습니다.!(실제 사용시 설정한 모든 연락처로 자동 전송됩니다)"; // 사용자의 위치

                                String to = mn.get(0);
                                Uri smsUri = Uri.parse("tel:" + to);
                                Intent it = new Intent(Intent.ACTION_VIEW, smsUri);
                                it.putExtra("address", to);
                                it.putExtra("sms_body", message);
                                it.setType("vnd.android-dir/mms-sms");
                                startActivity(it);

                                Toast.makeText(getApplicationContext(), "전송완료", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)  {
                                Toast.makeText(getApplicationContext(), "전송실패", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgNumList> call, Throwable t) {

                    }
                });
            }
        });

        //전화테스트 버튼
        calltest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.getemergency(ID).enqueue(new Callback<MsgNumList>() {
                    @Override
                    public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                        MsgNumList mnl = response.body();
                        List<MsgNumData> mData = mnl.items;

                        callnum = mData.get(0).callnum;

                        if(callnum == null || callnum.equals(""))
                        {
                            AlertDialog dialog;
                            AlertDialog.Builder builder = new AlertDialog.Builder(EmergencySet.this);
                            dialog = builder.setMessage("설정에서 연락처를 저장하세요").setNegativeButton("확인", null).create();
                            dialog.show();
                        }
                        else {
                            final Intent intent2 = new Intent(Intent.ACTION_CALL);
                            intent2.setData(Uri.parse("tel:" + callnum));
                            try {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(EmergencySet.this);
                                dialog.setTitle("전화테스트");
                                dialog.setMessage("설정한 연락처로 전화하겠습니까?");
                                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(intent2);
                                    }
                                });
                                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                dialog.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(EmergencySet.this, "전화걸기 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgNumList> call, Throwable t) {

                    }
                });


            }
        });

        //시스템설정완료 버튼
        syssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력된 내용 가져옴
                int sen = sensor.getProgress();
                int vol = volume.getProgress();
                Log.i("시스템", sen+"/"+vol);

                //db에 입력
                api.setsystem(sen, vol, ID).enqueue(new Callback<MsgNumList>() {
                    @Override
                    public void onResponse(Call<MsgNumList> call, Response<MsgNumList> response) {
                        MsgNumList mnl = response.body();
                        Boolean update = mnl.update;

                        if (update) {
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgNumList> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
