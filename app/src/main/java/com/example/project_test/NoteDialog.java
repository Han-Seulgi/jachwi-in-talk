package com.example.project_test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.Mypage.ValidateMypage;
import com.example.project_test.R;
import com.example.project_test.Recipe.PostData;
import com.example.project_test.Recipe.RecipeBoardActivity;
import com.example.project_test.Recipe.RecipeListData;
import com.example.project_test.Recipe.RecipePostList;
import com.example.project_test.Recipe.RecipeRandom;
import com.example.project_test.Recipe.RecipeRecyclerAdapter;
import com.example.project_test.User;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteDialog {
    private Context context;

    public NoteDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성
        final Dialog dlg = new Dialog(context);


        dlg.setContentView(R.layout.dialog_note);
        dlg.show();


        final RecyclerView rv;
        final NoteRecyclerAdapter adapter;
        final LinearLayoutManager layoutManager;

        final ArrayList<NoteListData> data = new ArrayList<>();

        final Button submit = dlg.findViewById(R.id.submit);
        rv = dlg.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        adapter = new NoteRecyclerAdapter();

        //서버 연결
        Api api = Api.Factory.INSTANCE.create();
        api.getnote(LoginActivity.user_ac).enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                Note note = response.body();
                List<NoteData> noteData = note.items;

                ArrayList<String> sid1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();
                ArrayList<String> day1 = new ArrayList<>();

                //리스트에 작성자, 내용, 날짜 넣기
                for (NoteData n:noteData) {
                    sid1.add(n.sid);
                    con1.add(n.note_con);
                    day1.add(n.note_day);
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                final String[] sid = sid1.toArray(new String[sid1.size()]);
                final String[] con = con1.toArray(new String[con1.size()]);
                final String[] day = day1.toArray(new String[day1.size()]);

                Log.i("note1", sid.length+"");

                //리사이클러뷰 데이터파일에 데이터 넘기기
                int i = 0;
                while (i < sid.length) {
                    data.add(new NoteListData(sid[i], con[i],day[i]));
                    i++;
                }
                adapter.setData(data);
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Log.i("abcdef", t.getMessage());
            }
        }); //서버연결

        layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);

        //확인버튼
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });


    }
}
