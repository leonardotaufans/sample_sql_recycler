package com.leontaufan.wuttpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;
    private List<ModelPerson> personArrayList;
    private ViewSwitcher switcher;
    //    private View emptyState;
    private MySQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        emptyState = findViewById(R.id.empty_state);

        /* Akses ke SQLite dan memasukkannya ke variabel */
        helper = new MySQLiteHelper(this);
        personArrayList = helper.getAllPerson();

        /* Recycler View dan kawan-kawannya */
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyRecyclerAdapter(personArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        /* Floating Action Button */
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(view -> {
            addData();
        });


    }

    private void refreshStatus() {
        /* Dijalankan jika ada update. Array di-clear terlebih dahulu agar tidak muncul duplikat. */
        personArrayList.clear();
        personArrayList.addAll(helper.getAllPerson());

        /* Method dari RecyclerAdapter. Digunakan untuk melakukan notifikasi bila data sudah berubah. */
        adapter.notifyDataSetChanged();
    }


    private void addData() {
        /* Data placeholder */

        /* Menjalankan method refreshStatus() */
        refreshStatus();
    }
}