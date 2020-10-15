package com.example.project_test.SharenRent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.Meet.MeetActivity;
import com.example.project_test.Mypage.MyPageActivity;
import com.example.project_test.R;
import com.example.project_test.Writing.MeetWritingActivity;
import com.example.project_test.Writing.ShareWritingActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView rv1, rv2;
    private SRCategoryRecyclerAdapter adapter1;
    private ShareRecyclerAdapter adapter2;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager layoutManager2;

    TextView tabtitle;
    Button writing;

    //어댑터에 보낼 데이터
    ArrayList<String> category = new ArrayList<>();
    ArrayList<ShareListData> data;

    //글목록
    private  int[] img = {R.drawable.chocolate, R.drawable.rrimg1, R.drawable.rrimg2, R.drawable.rrimg3, R.drawable.chocolate, R.drawable.rrimg1, R.drawable.rrimg2, R.drawable.rrimg3};
    private String[] title = {"초콜릿", "계란", "계란", "빵", "초콜릿", "계란", "계란", "빵"};
    private String[] price = {"2000", "3000", "500", "50000", "2000", "3000", "500", "50000"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        //상단탭
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ↓툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mypage);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabtitle = findViewById(R.id.tabtitle);
        writing = findViewById(R.id.writing);

        //받아오기
        Intent intent = getIntent();

        String tt = intent.getStringExtra("탭이름");
        tabtitle.setText(tt);

        //카테고리
        rv1 = findViewById(R.id.rv1);
        rv1.setHasFixedSize(true);
        adapter1 = new SRCategoryRecyclerAdapter();

        Api api = Api.Factory.INSTANCE.create();
        api.getCategory(44).enqueue(new Callback<CategoryData>() {
            @Override
            public void onResponse(Call<CategoryData> call, Response<CategoryData> response) {
                CategoryData cgList = response.body();
                List<Category> cg = cgList.items;

                //리스트에 넣기
                for (Category s:cg) {
                    category.add(s.tag);
                }

                adapter1.setData(category);
                rv1.setAdapter(adapter1);
            }
            @Override
            public void onFailure(Call<CategoryData> call, Throwable t) {
                Log.i("abced",t.getMessage());
            }
        });

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv1.setLayoutManager(layoutManager);

        //글목록
        rv2 = findViewById(R.id.rv2);
        rv2.setHasFixedSize(true);

        layoutManager2 = new GridLayoutManager(this,2);
        rv2.setLayoutManager(layoutManager2);

        adapter2 = new ShareRecyclerAdapter();

        data = new ArrayList<>();
        int i = 0;
        while (i < title.length) {
            data.add(new ShareListData(img[i], title[i], price[i]));
            i++;
        }
        adapter2.setData(data);
        rv2.setAdapter(adapter2);

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareActivity.this, ShareWritingActivity.class);
                startActivity(intent);

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
}
