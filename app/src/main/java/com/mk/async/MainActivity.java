package com.mk.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class MainActivity extends AppCompatActivity {
    Gson gson;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private CategoryAdapter adapter;
    List<CategoryModel> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager  = new LinearLayoutManager(MainActivity.this,  HORIZONTAL,false);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        ((Async) new Async(this).execute("category","limit","6")).dataListener(new DataListener() {
            @Override
            public void onDataListener(String response) {
                gson = new Gson();
                categories = Arrays.asList(gson.fromJson(response,CategoryModel[].class));
                adapter = new CategoryAdapter(MainActivity.this, categories);
                recyclerView.setAdapter(adapter);
            }
        });

       // textView.setText(result);
   /*     gson = new Gson();
        categories = Arrays.asList(gson.fromJson(result,CategoryModel[].class));
        adapter = new CategoryAdapter(MainActivity.this, categories);
        recyclerView.setAdapter(adapter);*/


    }


}
