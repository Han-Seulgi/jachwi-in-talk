package com.example.project_test.Mypage.MyContents;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.Api;
import com.example.project_test.LoginActivity;
import com.example.project_test.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyContentsFragment2 extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private MyCmtRecyclerAdapter adapter;

    String id;

    ArrayList<MyCmtListData> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mycontents2, container, false);

        id = LoginActivity.user_ac;

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MyCmtRecyclerAdapter();

        data = new ArrayList<>();

        //서버 연결
        Api api = Api.Factory.INSTANCE.create();
        api.myCmt(id).enqueue(new Callback<MyCmtList>() {
            @Override
            public void onResponse(Call<MyCmtList> call, Response<MyCmtList> response) {
                MyCmtList cmtList = response.body();
                List<CmtData> cmtData = cmtList.items;

                ArrayList<Integer> cmt_code1 = new ArrayList<>();
                ArrayList<String> cmt_con1 = new ArrayList<>();
                ArrayList<String> cmt_day1 = new ArrayList<>();

                ArrayList<String> id1 = new ArrayList<>();
                ArrayList<String> title1 = new ArrayList<>();
                ArrayList<String> day1 = new ArrayList<>();
                ArrayList<String> con1 = new ArrayList<>();

                ArrayList<Integer> bcode1 = new ArrayList<>();
                //리스트에 제목, 날짜, 작성자 아이디 넣기
                for (CmtData d:cmtData) {
                    cmt_code1.add(d.cmt_code);
                    cmt_con1.add(d.cmt_con);
                    cmt_day1.add(d.cmt_day);

                    id1.add(d.id);

                    title1.add(d.post_title);
                    con1.add(d.post_con);
                    day1.add(d.post_day);
                    bcode1.add(d.bcode);
                    Log.i("my","마페 All: " + d.toString());
                }

                //리스트를 배열로 바꾸기
                Integer[] cmt_code = cmt_code1.toArray(new Integer[cmt_code1.size()]);
                String[] cmt_con = cmt_con1.toArray(new String[con1.size()]);
                String[] cmt_day = cmt_day1.toArray(new String[con1.size()]);

                String[] id = id1.toArray(new String[id1.size()]);

                String title[] = title1.toArray(new String[title1.size()]);
                String con[] = con1.toArray(new String[con1.size()]);
                String day[] = day1.toArray(new String[day1.size()]);
                Integer bcode[] = bcode1.toArray(new Integer[bcode1.size()]);

                int img[] = new int[title1.size()];
                int i = 0;
                while (i < title.length) {
                    img[i] = R.drawable.foodboard;
                    data.add(new MyCmtListData(img[i], cmt_code[i], cmt_con[i], cmt_day[i], id[i], title[i], con[i], day[i], bcode[i]));
                    i++;
                }
                adapter.setData(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MyCmtList> call, Throwable t) {
                Log.i("my", t.getMessage());
            }
        }); //서버연결

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }
}
