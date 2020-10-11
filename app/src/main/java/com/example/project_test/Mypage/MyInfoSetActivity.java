package com.example.project_test.Mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Api;
import com.example.project_test.JoinActivity;
import com.example.project_test.LoginActivity;
import com.example.project_test.MainActivity;
import com.example.project_test.MySpinnerAdapter;
import com.example.project_test.R;
import com.example.project_test.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoSetActivity extends AppCompatActivity {

    Button pwchange, submit, cancle, outBtn, btnCheck;
    EditText idET, nameET, emailET;
    View pwchangeView;
    Spinner spinner;
    Boolean checkName = true, checkEmail = true;
    String id, name, email, fname, femail, name2, email2;
    Toolbar toolbar;
    ImageView nameck, emailck;

    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoset);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pwchange = findViewById(R.id.pwchng);
        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancel);
        outBtn = findViewById(R.id.outBtn);
        idET = findViewById(R.id.idET);
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        spinner = findViewById(R.id.emailSpinner);
        nameck = findViewById(R.id.nameck);
        emailck = findViewById(R.id.emailck);
        btnCheck = findViewById(R.id.btnCheck);




        //이메일
        final ArrayList<String> items = new ArrayList<String>();
        items.add("naver.com");
        items.add("gmail.com");
        items.add("daum.net");
        items.add("hanmail.com");
        items.add("nate.com");
        items.add("선택하세요");

        MySpinnerAdapter adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //아이디는 변경 못함
        id = LoginActivity.user_ac;
        idET.setText(id);


        //아이디를 이용해 유저 정보 검색
        Api api = Api.Factory.INSTANCE.create();
        api.getUser(LoginActivity.user_ac).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                //초기 이름과 이메일
                fname = user.name;
                femail = user.email;
                name = user.name;
                name2 = user.name;
                email = user.email;
                email2 = user.email;

                nameET.setText(fname);
                String emailstr[] = femail.split("@");
                emailET.setText(emailstr[0]);
                int cnt=0;
                for (String i:items) {
                    if(i.equals(emailstr[1])) {
                        spinner.setSelection(cnt);
                    }
                    cnt++;
                }
            }

            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        //취소버튼
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfoSetActivity.this);
                dialog.setTitle("수정취소");
                dialog.setMessage("정보수정을 취소하시겠습니까?");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        //name 검사
        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    name = nameET.getText().toString();

                    if (!name.isEmpty()) {
                        Api api = Api.Factory.INSTANCE.create();

                        api.mypagename(name).enqueue(new Callback<ValidateMypage>() {
                            public void onResponse(Call<ValidateMypage> call, Response<ValidateMypage> response) {

                                ValidateMypage validatemp = response.body();

                                boolean valiName = validatemp.newName;
                                if (valiName || name.equals(fname)) {
                                    nameck.setVisibility(View.VISIBLE);
                                    checkName = true;
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);
                                    dialog = builder.setMessage("중복되는 닉네임입니다.").setNegativeButton("확인", null).create();
                                    dialog.show();
                                    nameck.setVisibility(View.GONE);
                                    checkName = false;
                                }
                            }

                            public void onFailure(Call<ValidateMypage> call, Throwable t) {
                                Log.i("validate", t.getMessage());
                            }
                        });
                    } else {
                        nameck.setVisibility(View.GONE);
                        checkName = false;
                    }
                }
            }
        });


        //이메일 검사
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString() + "@" + spinner.getSelectedItem().toString();

                Log.i("testst", email);

                if (!emailET.getText().toString().isEmpty()) {
                    Api api = Api.Factory.INSTANCE.create();

                    api.mypageemail(email).enqueue(new Callback<ValidateMypage>() {
                        public void onResponse(Call<ValidateMypage> call, Response<ValidateMypage> response) {

                            ValidateMypage validatemp = response.body();

                            boolean valiEmail = validatemp.newEmail;
                            if (valiEmail || email.equals(femail)) {
                                emailck.setVisibility(View.VISIBLE);
                                checkEmail = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);
                                dialog = builder.setMessage("이미 가입된 이메일입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                emailck.setVisibility(View.GONE);
                                checkEmail = false;
                            }
                        }

                        public void onFailure(Call<ValidateMypage> call, Throwable t) {
                            Log.i("validate", t.getMessage());
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);
                    dialog = builder.setMessage("메일주소를 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    emailck.setVisibility(View.GONE);
                    checkEmail = false;
                }
            }
        });

        pwchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder pwch = new AlertDialog.Builder(MyInfoSetActivity.this);
//                pwchangeView = (View) View.inflate(MyInfoSetActivity.this, R.layout.pwchangedialog, null);
//                pwch.setView(pwchangeView);
//
//                pwch.setPositiveButton("변경", null);
//                pwch.setNegativeButton("취소", null);
//
//                pwch.show();
                final String ps = "";

                PwChangeActivity customDialog = new PwChangeActivity(MyInfoSetActivity.this);
                customDialog.callFunction(ps);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name2 = nameET.getText().toString();
                email2 = emailET.getText().toString() + "@" + spinner.getSelectedItem().toString();


                if ( name2.equals("") || email2.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);

                    dialog = builder.setMessage("모든정보를 입력해주세요.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else if ( !checkName || !checkEmail) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);

                    dialog = builder.setMessage("입력된 정보를 다시 확인해주세요.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                //중복검사한 값과 최종 입력값이 같은지 확인
                else if (!(email2.equals(email)) || !(name2.equals(name))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);

                    dialog = builder.setMessage("닉네임과 이메일 중복을 다시 확인해주세요").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else {
                    Api api = Api.Factory.INSTANCE.create();

                    api.updateUser(id, name2, email2).enqueue(new Callback<ValidateMypage>() {
                        public void onResponse(Call<ValidateMypage> call, Response<ValidateMypage> response) {

                            ValidateMypage updateInfo = response.body();
                            boolean update = updateInfo.updateInfo;

                            Log.i("ggg", update+"");

                            if(update) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);
                                dialog = builder.setMessage("수정완료").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                        .create();
                                dialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoSetActivity.this);
                                dialog = builder.setMessage("수정실패").setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }

                        public void onFailure(Call<ValidateMypage> call, Throwable t) {

                        }
                    });
                }
            }
        });

        outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfoSetActivity.this);
                dialog.setTitle("회원탈퇴");
                dialog.setMessage("회원탈퇴 하시겠습니까?");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Api api = Api.Factory.INSTANCE.create();

                        api.deleteUser(id).enqueue(new Callback<ValidateMypage>() {
                            @Override
                            public void onResponse(Call<ValidateMypage> call, Response<ValidateMypage> response) {
                                ValidateMypage validatemp = response.body();
                                boolean delete = validatemp.delete;

                                if(delete)
                                {
                                    Intent i = new Intent(MyInfoSetActivity.this , LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<ValidateMypage> call, Throwable t) {

                            }
                        });

                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });

    }

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
