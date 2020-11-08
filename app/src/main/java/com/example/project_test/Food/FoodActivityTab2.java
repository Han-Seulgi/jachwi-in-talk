package com.example.project_test.Food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

import java.util.ArrayList;

public class FoodActivityTab2 extends Fragment{

    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private FoodRecyclerAdapter adapter;

    ArrayList<FoodListData> data;
    ArrayList<FoodListData> cdata;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.activity_food_tab1, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new FoodRecyclerAdapter();

        data = new ArrayList<>();
        cdata = new ArrayList<>();
        //서버 연결
        /*Api api = Api.Factory.INSTANCE.create();
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

                cdata.addAll(data);
            }

            @Override
            public void onFailure(Call<FoodPostList> call, Throwable t) {
                Log.i("foood", t.getMessage());
            }
        }); //서버연결

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);*/

        return v;
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
                //모든 데이터의 입력받은 단어가 포함되어 있으면
                if(cdata.get(i).getTitle().contains(txt)) {
                    //검색된 데이터를 리스트에 추가
                    data.add(cdata.get(i));
                }
            }
        }
        //리스트 데이터가 변경되었으므로 어댑터 갱신
        adapter.notifyDataSetChanged();
    }
}
