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
    private View emptyState;
    private MySQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyState = findViewById(R.id.empty_state);
        switcher = findViewById(R.id.switcher);
        helper = new MySQLiteHelper(this);
        personArrayList = helper.getAllPerson();
        Log.d("DEBUG_WUTT", personArrayList.toString());

        // Bagian ini tidak relevan, ini karena menggunakan switcher
        Animation animationIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation animationOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        switcher.setInAnimation(animationIn);
        switcher.setOutAnimation(animationOut);
        LottieAnimationView lottie = findViewById(R.id.lottieAnimationView);
        lottie.setAnimationFromUrl("https://assets8.lottiefiles.com/temp/lf20_Celp8h.json");
        FloatingActionButton fab = findViewById(R.id.fab_add);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyRecyclerAdapter(personArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        fab.setOnClickListener(view -> {
            addData();
        });

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }

    private void refreshStatus() {
        personArrayList.clear();
        personArrayList.addAll(helper.getAllPerson());
        adapter.notifyDataSetChanged();
    }

    

    private void addData() {
        helper.addPerson(new ModelPerson(new Random().nextInt(), "Leonardo", "21/12/1998", "L"));
        helper.addPerson(new ModelPerson(new Random().nextInt(), "Taufan", "21/12/1998", "L"));
        helper.addPerson(new ModelPerson(new Random().nextInt(), "Sontani", "21/12/1998", "L"));
        helper.addPerson(new ModelPerson(new Random().nextInt(), "Errenzii", "21/12/1998", "L"));
        refreshStatus();
    }
}