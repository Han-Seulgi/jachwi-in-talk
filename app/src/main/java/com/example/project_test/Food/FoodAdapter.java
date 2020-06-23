package com.example.project_test.Food;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

class FoodAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> items = new ArrayList<Fragment>();


    public FoodAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment item) {
        items.add(item);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size() ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return "게시물";
        }
        else {
            return "스크랩";
        }

    }

}