package com.example.project_test;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_test.Emergency.MsgNumList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    ImageButton back;
    ImageView idCheckImg, pwCheckImg, nameCheckImg, emailCheckImg;
    Button submit, btnCheck;
    EditText idET, pwET, pwckET, nameET, emailET;
    Boolean checkValidate = false, checkPw = false, checkName = false, checkEmail = false;

    String id, pw, pwck, name, email;

    private AlertDialog dialog;

    Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        back = findViewById(R.id.back);
        btnCheck = findViewById(R.id.btnCheck);
        submit = findViewById(R.id.submit);

        idET = findViewById(R.id.idET);
        pwET = findViewById(R.id.pwET);
        pwckET = findViewById(R.id.pwckET);
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);

        idCheckImg = findViewById(R.id.idck);
        pwCheckImg = findViewById(R.id.pwckck);
        nameCheckImg = findViewById(R.id.nameck);
        emailCheckImg = findViewById(R.id.emailck);

        spinner = findViewById(R.id.emailSpinner);

        ArrayList<String> items = new ArrayList<String>();
        items.add("naver.com");
        items.add("gmail.com");
        items.add("daum.net");
        items.add("hanmail.com");
        items.add("nate.com");
        items.add("선택하세요");

        MySpinnerAdapter adapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());

        /*pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        비밀번호 *모양
        pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );*/

        //focus가 이동했을때 유효성검사
        //id
        idET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    id = idET.getText().toString();

                    if (!id.isEmpty()) {
                        Api api = Api.Factory.INSTANCE.create();

                        api.validateUser(id).enqueue(new Callback<ValidateID>() {
                            public void onResponse(Call<ValidateID> call, Response<ValidateID> response) {

                                ValidateID validateID = response.body();

                                boolean valiID = validateID.newID;
                                if (valiID) {
                                    idCheckImg.setVisibility(View.VISIBLE);
                                    checkValidate = true;
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                    dialog = builder.setMessage("이미 가입된 아이디입니다.").setNegativeButton("확인", null).create();
                                    dialog.show();
                                    idCheckImg.setVisibility(View.GONE);
                                    checkValidate = false;
                                }
                            }

                            public void onFailure(Call<ValidateID> call, Throwable t) {
                                Log.i("validate", t.getMessage());
                            }
                        });
                    } else {
                        idCheckImg.setVisibility(View.GONE);
                        checkValidate = false;
                    }
                }
            }
        });
        //pw
        pwckET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    pw = pwET.getText().toString();
                    pwck = pwckET.getText().toString();

                    if (!pw.isEmpty() || !pwck.isEmpty()) {
                        if (pw.equals(pwck)) {
                            pwCheckImg.setVisibility(View.VISIBLE);
                            checkPw = true;
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                            dialog = builder.setMessage("비밀번호가 일치하지 않습니다.").setNegativeButton("확인", null).create();
                            dialog.show();
                            pwCheckImg.setVisibility(View.GONE);
                            checkPw = false;
                        }
                    } else {
                        pwCheckImg.setVisibility(View.GONE);
                        checkPw = false;
                    }

                }
            }
        });

        //실시간?
        /*pwET.addTextChangedListener(textWatcher);*/

        //name
        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    name = nameET.getText().toString();

                    if (!name.isEmpty()) {
                        Api api = Api.Factory.INSTANCE.create();

                        api.validateName(name).enqueue(new Callback<ValidateName>() {
                            public void onResponse(Call<ValidateName> call, Response<ValidateName> response) {

                                ValidateName validateName = response.body();

                                boolean valiName = validateName.newName;
                                if (valiName) {
                                    nameCheckImg.setVisibility(View.VISIBLE);
                                    checkName = true;
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                    dialog = builder.setMessage("중복되는 닉네임입니다.").setNegativeButton("확인", null).create();
                                    dialog.show();
                                    nameCheckImg.setVisibility(View.GONE);
                                    checkName = false;
                                }
                            }

                            public void onFailure(Call<ValidateName> call, Throwable t) {
                                Log.i("validate", t.getMessage());
                            }
                        });
                    } else {
                        nameCheckImg.setVisibility(View.GONE);
                        checkName = false;
                    }
                }
            }
        });
        //email
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString() + "@" + spinner.getSelectedItem().toString();

                Log.i("testst", email);

                if (!emailET.getText().toString().isEmpty()&&!spinner.getSelectedItem().toString().equals("선택하세요")) {
                    Api api = Api.Factory.INSTANCE.create();

                    api.validateEmail(email).enqueue(new Callback<ValidateEmail>() {
                        public void onResponse(Call<ValidateEmail> call, Response<ValidateEmail> response) {

                            ValidateEmail validateEmail = response.body();

                            boolean valiEmail = validateEmail.newEmail;
                            if (valiEmail) {
                                emailCheckImg.setVisibility(View.VISIBLE);
                                checkEmail = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("이미 가입된 이메일입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                emailCheckImg.setVisibility(View.GONE);
                                checkEmail = false;
                            }
                        }

                        public void onFailure(Call<ValidateEmail> call, Throwable t) {
                            Log.i("validate", t.getMessage());
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("메일주소를 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    emailCheckImg.setVisibility(View.GONE);
                    checkEmail = false;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {         //뒤로가기 눌렀을 경우 로그인 화면으로
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {        //완료버튼 눌렀을 경우 ...?
            @Override
            public void onClick(View v) {
                id = idET.getText().toString();
                pw = pwET.getText().toString();
                name = nameET.getText().toString();
                email = emailET.getText().toString() + "@" + spinner.getSelectedItem().toString();

                //비밀번호 확인 후에도 비밀번호ET에서 변경할 경우 검사가 x이므로
                pwck = pwckET.getText().toString();
                    if (pw.equals(pwck)) {
                        pwCheckImg.setVisibility(View.VISIBLE);
                        checkPw = true;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                        dialog = builder.setMessage("비밀번호가 일치하지 않습니다.").setNegativeButton("확인", null).create();
                        dialog.show();
                        pwCheckImg.setVisibility(View.GONE);
                        checkPw = false;
                    }

                 if (id.equals("") || pw.equals("") || name.equals("") || email.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);

                    dialog = builder.setMessage("모든정보를 입력해주세요.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else if (!checkValidate || !checkPw || !checkName || !checkEmail) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);

                    dialog = builder.setMessage("입력된 정보를 다시 확인해주세요.").setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                 else {
                    final Api api = Api.Factory.INSTANCE.create();

                    api.joinUser(id, pw, name, email).enqueue(new Callback<Join>() {
                         public void onResponse(Call<Join> call, Response<Join> response) {
                             AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                             dialog = builder.setMessage("가입완료").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     finish();
                                 }
                             })
                                     .create();
                             dialog.show();

                             //emergency 테이블에 추가
                             api.addemergency(id).enqueue(new Callback<Join>() {
                                 @Override
                                 public void onResponse(Call<Join> call, Response<Join> response) {
                                     Join jn = response.body();

                                     boolean jn2 = jn.success;

                                     Log.i("ssss", jn2+"");
                                 }

                                 @Override
                                 public void onFailure(Call<Join> call, Throwable t) {
                                     Log.i("add", t.getMessage());
                                 }
                             });
                         }

                         public void onFailure(Call<Join> call, Throwable t) {
                             Log.i("validate", t.getMessage());
                             AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                             dialog = builder.setMessage("가입실패").setNegativeButton("확인", null)
                                     .create();
                             dialog.show();
                         }
                     });
                }

            }
        });
    }


        /*TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.i("text","before : "+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i("text","ontext : "+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                {
                        Log.i("text","after : "+s.toString());
                        //pw = pwET.getText().toString();
                    pwck = pwckET.getText().toString();

                    if (!s.toString().isEmpty() || !pwck.isEmpty()) {
                        if (s.equals(pwck)) {
                            pwCheckImg.setVisibility(View.VISIBLE);
                            checkPw = true;
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                            dialog = builder.setMessage("비밀번호가 일치하지 않습니다.").setNegativeButton("확인", null).create();
                            dialog.show();
                            pwCheckImg.setVisibility(View.GONE);
                            checkPw = false;
                        }
                    } else {
                        pwCheckImg.setVisibility(View.GONE);
                        checkPw = false;
                    }
            }
        }??
    };*/


}
