package com.example.project_test;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;


public class MainActivity extends ActivityGroup {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //탭 만들기
        createTab();
    }


    private void createTab() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup(getLocalActivityManager());

        //탭 아이콘~
        ImageView tab1 = new ImageView(this);
        tab1.setImageResource(R.drawable.board);
        ImageView tab2 = new ImageView(this);
        tab2.setImageResource(R.drawable.room);
        ImageView tab3 = new ImageView(this);
        tab3.setImageResource(R.drawable.event);
        ImageView tab4 = new ImageView(this);
        tab4.setImageResource(R.drawable.emergency);

        tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator(tab1)
                .setContent(new Intent(this,BoardActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator(tab2)
                .setContent(new Intent(this, RoomActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("TAB3").setIndicator(tab3)
                .setContent(new Intent(this, EventActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("TAB4").setIndicator(tab4)
                .setContent(new Intent(this, EmergencyActivity.class)));

    }




}

