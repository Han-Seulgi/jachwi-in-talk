package com.example.project_test.Recipe;

public class RecipeListData {
    private int img;
    private int count;
    private String title;
    private String tabTitle;

    public RecipeListData(int img, int count, String title, String tabTitle) {
        this.img = img;
        this.count = count;
        this.title = title;
        this.tabTitle = tabTitle;
    }

    public int getImg() {
        return this.img;
    }

    public String getCount() {
        String cnt = Integer.toString(this.count);
        return cnt;
    }

    public String getTitle() {
        return this.title;
    }
    public String getTabTitle() {return this.tabTitle;}
}
