package com.example.project_test.Food;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivityTab1 extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private FoodRecyclerAdapter adapter;

    ArrayList<FoodListData> data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.activity_food_tab1, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new FoodRecyclerAdapter();

        data = new ArrayList<>();
        Log.i("foood","또 뭔데 ");
        //서버 연결
        Api api = Api.Factory.INSTANCE.create();
        api.getFoodList(22).enqueue(new Callback<FoodPostList>() {
            @Override
            public void onResponse(Call<FoodPostList> call, Response<FoodPostList> response) {
                FoodPostList postList = response.body();
                List<PostData> postData = postList.items;

                ArrayList<String> title1 = new ArrayList<>();
                ArrayList<String> day1 = new ArrayList<>();
                ArrayList<String> id1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();

                //리스트에 제목, 날짜, 작성자 아이디 넣기
                for (PostData d:postData) {
                    title1.add(d.post_title);
                    day1.add(d.post_day);
                    id1.add(d.id);
                    con1.add(d.post_con);
                    Log.i("foood","맛집 All: " + d.toString());
                }

                //리스트를 배열로 바꾸기, 이미지 배열 생성
                String title[] = title1.toArray(new String[title1.size()]);
                String day[] = day1.toArray(new String[day1.size()]);
                String[] id = id1.toArray(new String[id1.size()]);
                String[] con = con1.toArray(new String[con1.size()]);
                Integer img[] = new Integer[title1.size()];

                int i = 0;
                while (i < title.length) {
                    img[i] = R.drawable.foodboard;
                    data.add(new FoodListData(img[i], title[i], day[i], id[i], con[i]));
                    i++;
                }
                adapter.setData(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FoodPostList> call, Throwable t) {
                Log.i("foood", t.getMessage());
            }
        }); //서버연결

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }
}

