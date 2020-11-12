package com.example.project_test;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project_test.Writing.FoodWritingActivity;
import com.example.project_test.Writing.WritingActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteActivity extends AppCompatActivity {
    Toolbar toolbar;

    Button send;
    EditText idedit, cedit;
    ImageView idck;
    Boolean checkID = false;
    String id;

    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_note);

        send = findViewById(R.id.send);
        idedit = findViewById(R.id.idedit);
        cedit = findViewById(R.id.cedit);
        idck = findViewById(R.id.idck);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backbtn);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        idedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    id = idedit.getText().toString();

                    if (!id.isEmpty()) {
                        if(id.equals(LoginActivity.user_ac)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                            dialog = builder.setMessage("자신에게는 쪽지를 보낼 수 없습니다").setNegativeButton("확인", null).create();
                            dialog.show();
                        }
                        else {
                            //입력한 아이디가 db에 존재하는 확인
                            Api api = Api.Factory.INSTANCE.create();
                            api.getID(id).enqueue(new Callback<UserIdCheck>() {
                                @Override
                                public void onResponse(Call<UserIdCheck> call, Response<UserIdCheck> response) {
                                    UserIdCheck id = response.body();
                                    boolean ckid = id.ckid;

                                    //입력한 아이디 존재
                                    if (ckid) {
                                        idck.setVisibility(View.VISIBLE);
                                        checkID = true;
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                                        dialog = builder.setMessage("존재하지 않는 아이디입니다").setNegativeButton("확인", null).create();
                                        dialog.show();
                                        idck.setVisibility(View.GONE);
                                        checkID = false;
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserIdCheck> call, Throwable t) {

                                }
                            });
                        }
                    }
                    else {
                        idck.setVisibility(View.GONE);
                        checkID = false;
                    }
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id2 = idedit.getText().toString();
                String con = cedit.getText().toString();

                if (id2.equals("") || con.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                    dialog = builder.setMessage("모든정보를 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                else if (!checkID) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                    dialog = builder.setMessage("아이디를 다시 확인해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                else if(!(id.equals(id2))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                    dialog = builder.setMessage("아이디를 다시 확인해주세요").setNegativeButton("확인", null).create();
                    dialog.show();
                    idck.setVisibility(View.GONE);
                    checkID = false;
                }
                else {
                    Api api = Api.Factory.INSTANCE.create();
                    api.newNote(LoginActivity.user_ac, id, con).enqueue(new Callback<NewNote>() {
                        @Override
                        public void onResponse(Call<NewNote> call, Response<NewNote> response) {
                            NewNote note = response.body();
                            boolean newnote = note.newNote;

                            if(newnote){
                                Log.i("note", newnote+"");
                                AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                                dialog = builder.setMessage("전송완료").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).create();
                                dialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<NewNote> call, Throwable t) {
                            Log.i("note", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder logout = new AlertDialog.Builder(NoteActivity.this);
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
