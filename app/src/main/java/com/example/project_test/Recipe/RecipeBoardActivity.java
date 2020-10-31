package com.example.project_test.Recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.Writing.WritingActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeBoardActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView rv;
    private RecipeRecyclerAdapter adapter;
    private GridLayoutManager layoutManager;

    TextView tabTitle;
    SearchView search;

    ArrayList<RecipeListData> data;
    //검색을 위한 전체 데이터 리스트 복사본
    ArrayList<RecipeListData> cdata;

    public static Context CONTEXT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_recipe);

        CONTEXT = this;

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button writing = findViewById(R.id.writing);//글쓰기 버튼
        search = findViewById(R.id.search);
        tabTitle = findViewById(R.id.title);
        final String tt = tabTitle.getText().toString();


        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        adapter = new RecipeRecyclerAdapter();

        /*final ArrayList<String> comment1 = new ArrayList<>();//댓글수,추천수
        final ArrayList<String> like1 = new ArrayList<>();*/

        data = new ArrayList<>();
        //복사본
        cdata = new ArrayList<>();

        //서버 연결
        Api api = Api.Factory.INSTANCE.create();
        api.getRecipeList(11).enqueue(new Callback<RecipePostList>() {
            @Override
            public void onResponse(Call<RecipePostList> call, Response<RecipePostList> response) {
                RecipePostList postList = response.body();
                List<PostData> postData = postList.items;

                ArrayList<String> title1 = new ArrayList<>();
                ArrayList<String> day1 = new ArrayList<>();
                ArrayList<String> id1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();
                ArrayList<Integer> code1 = new ArrayList<>();



                //리스트에 제목, 날짜, 작성자 아이디 넣기
                for (PostData d:postData) {
                    title1.add(d.post_title);
                    day1.add(d.post_day);
                    id1.add(d.id);
                    con1.add(d.post_con);
                    code1.add(d.post_code);
                    Log.i("abc","요리 All: " + d.toString());
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                final String[] title = title1.toArray(new String[title1.size()]);
                String[] day = day1.toArray(new String[day1.size()]);
                String[] id = id1.toArray(new String[id1.size()]);
                String[] con = con1.toArray(new String[con1.size()]);
                final int[] img = new int[title1.size()];
                final Integer[] code = code1.toArray(new Integer[code1.size()]);
                /*int[] comment_cnt = new int[title1.size()];
                int[] like_cnt = new int[title1.size()];*/

                //넘어온 데이터의 사이즈에 맞춰 이미지 생성(?), 리사이클러뷰 데이터파일에 데이터 넘기기
                int i = 0;
                while (i < title.length) {
                    img[i] = R.drawable.recipe;
                    data.add(new RecipeListData(img[i], title[i], day[i], id[i], tt, con[i], code[i]));
                    i++;
                }
                adapter.setData(data);
                rv.setAdapter(adapter);

                //복사본에 모든 데이터 저장
                cdata.addAll(data);

                ExtendedFloatingActionButton fab = findViewById(R.id.fab);
                fab.extend();
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RecipeBoardActivity.this, RecipeRandom.class);
                        intent.putExtra("img",img);
                        intent.putExtra("title",title);
                        intent.putExtra("size",title.length);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<RecipePostList> call, Throwable t) {
                Log.i("abcdef", t.getMessage());
            }
        }); //서버연결


        layoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(layoutManager);


        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeBoardActivity.this, WritingActivity.class);
                startActivity(intent);

            }
        });


        //검색
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("search", newText);
                msearch(newText);
                return true;
            }
        });
    }

    //상단탭 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    //메뉴액션 --home:마이페이지 --message:쪽지함
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent mypage_itnt = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(mypage_itnt);
                return true;
            case android.R.id.message:
                //쪽지함 화면
                return true;
        }
        return true;
    }

    //검색을 수행하는 메소드
    public void msearch(String txt) {

        //문자 입력시마다 리스트를 지우고 새로 뿌림
        data.clear();

        //문자 입력이 없을때는 모든 데이터 보여줌
        if(txt.length() == 0) {
            data.addAll(cdata);
        }

        //문자 입력
        else{
            //데이터 리스트 복사본의 모든 데이터 검색
            for(int i = 0; i<cdata.size(); i++) {
                //모든 데이터의 입력받은 단어가 포함되어 있으면 true 반환
                if(cdata.get(i).getTitle().contains(txt)) {
                    //검색된 데이터를 리스트에 추가
                    data.add(cdata.get(i));
                }
            }
        }
        //리스트 데이터가 변경되었으므로 어댑터 갱신
        adapter.notifyDataSetChanged();
    }

    /*@Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }*/
}
