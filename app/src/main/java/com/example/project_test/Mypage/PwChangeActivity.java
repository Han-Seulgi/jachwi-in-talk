package com.example.project_test.Mypage;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;
import com.example.project_test.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PwChangeActivity {
    private Context context;
    String password, strpw1, strpw2, strckpw2;
    boolean bpw1 = false, bpw2 = false;

    public PwChangeActivity(Context context) {
        this.context = context;
    }

    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성
        final Dialog dlg = new Dialog(context);


        dlg.setContentView(R.layout.pwchangedialog);
        dlg.show();

        final EditText pw1 = (EditText) dlg.findViewById(R.id.pw1);
        final EditText pw2 = (EditText) dlg.findViewById(R.id.pw2);
        final EditText ckpw2 = (EditText) dlg.findViewById(R.id.ckpw2);
        final TextView ckpwTV = dlg.findViewById(R.id.ckpwTV);
        final TextView ckpwTV2 = dlg.findViewById(R.id.ckpwTV2);
        final Button cancel = dlg.findViewById(R.id.cancel);
        final Button submit = dlg.findViewById(R.id.submit);

//        strpw2 = pw2.getText().toString();
//        strckpw2 = ckpw2.getText().toString();

        //아이디를 이용해 비밀번호 정보 검색
        Api api = Api.Factory.INSTANCE.create();
        api.getUser(LoginActivity.user_ac).enqueue(new Callback<User>() {
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                password = user.password;
            }

            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        //비밀번호와 입력한 비밀번호가 같은지 검사
        pw1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false)
                {
                    strpw1 = pw1.getText().toString();

                    if(!(strpw1.equals(password))) {
                        ckpwTV.setVisibility(View.VISIBLE);
                        bpw1 = false;
                    }
                    else {
                        ckpwTV.setVisibility(View.GONE);
                        bpw1 =true;
                    }
                }
            }
        });

        //새비밀번호와 새비밀번호 확인이 같은지 검사
        pw2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false)
                {
                    strpw2 = pw2.getText().toString();
                    strckpw2 = ckpw2.getText().toString();

                    if(!(strpw2.equals(strckpw2))) {
                        ckpwTV2.setVisibility(View.VISIBLE);
                        bpw2 = false;
                    }
                    else {
                        ckpwTV2.setVisibility(View.GONE);
                        bpw2 = true;
                    }
                }
            }
        });

        //입력에 변화가 있을 때
        ckpw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //입력되는 텍스트에 변화가 있을 경우
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strpw2 = pw2.getText().toString();
                strckpw2 = ckpw2.getText().toString();

                if(!(strpw2.equals(strckpw2))) {
                    ckpwTV2.setVisibility(View.VISIBLE);
                    bpw2 = false;
                }
                else {
                    ckpwTV2.setVisibility(View.GONE);
                    bpw2 = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //취소버튼
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

        //확인버튼
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpw1 = pw1.getText().toString();
                strpw2 = pw2.getText().toString();
                strckpw2 = ckpw2.getText().toString();

                if ( strpw1.equals("") || strpw2.equals("") || strckpw2.equals("")) {
                    Toast.makeText(context, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(!bpw1 || !bpw2) {
                    Toast.makeText(context, "입력된 정보를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Api api = Api.Factory.INSTANCE.create();

                    api.changepassword(LoginActivity.user_ac, strckpw2).enqueue(new Callback<ValidateMypage>() {
                        public void onResponse(Call<ValidateMypage> call, Response<ValidateMypage> response) {

                            ValidateMypage validatemp = response.body();
                            boolean udpatepw = validatemp.updateInfo;

                            if (udpatepw) {
                                Toast.makeText(context, "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
                                dlg.dismiss();
                            } else {
                                Toast.makeText(context, "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
                                dlg.dismiss();
                            }
                        }

                        public void onFailure(Call<ValidateMypage> call, Throwable t) {
                        }
                    });


                }
            }
        });


    }
}
