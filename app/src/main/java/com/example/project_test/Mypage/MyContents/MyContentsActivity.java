package com.example.project_test.Mypage.MyContents;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.project_test.R;
import com.google.android.material.tabs.TabLayout;

public class MyContentsActivity extends AppCompatActivity {

    TabLayout mTabLayout;

    MyContentsFragment1 fragment1;
    MyContentsFragment2 fragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycontents);

        fragment1 = new MyContentsFragment1();
        fragment2 = new MyContentsFragment2();

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("내가 쓴 게시물"));
        mTabLayout.addTab(mTabLayout.newTab().setText("내가 쓴 댓글"));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragment1;
                else if(position==1)
                    selected = fragment2;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
