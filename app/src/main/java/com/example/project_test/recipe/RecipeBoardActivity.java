package com.example.project_test.recipe;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_test.R;

import java.util.ArrayList;

public class RecipeBoardActivity extends AppCompatActivity {
    private RecyclerView rv;
    private RecipeRecyclerAdapter adapter;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_recipe);

        RecyclerView rv = findViewById(R.id.rv);

        ArrayList<RecipeListData> data = new ArrayList<>();

        int i = 0;
        while(i<10) {
            data.add(new RecipeListData(R.drawable.food2,i,i+"번 게시글"));
            i++;
        }

        layoutManager = new GridLayoutManager(this,2);
        rv.setLayoutManager(layoutManager);

        adapter = new RecipeRecyclerAdapter();
        adapter.setData(data);
        rv.setAdapter(adapter);

    }
}
