package com.example.project_test.recipe;

import android.graphics.drawable.Drawable;

import org.w3c.dom.Text;

public class RecipeListData {
    private int img;
    private int count;
    private String title;

    public RecipeListData (int img, int count, String title) {
        this.img = img;
        this.count = count;
        this.title = title;
    }

    public int getImg() {
        return this.img;
    }
    public String getCount() {
        String cnt = Integer.toString(this.count);
        return cnt;
    }
    public String getTitle() {
        return  this.title;
    }
}
