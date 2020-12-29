package com.leontaufan.wuttpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;
    private ArrayList<ModelPerson> personArrayList = new ArrayList<>();
    private ViewSwitcher switcher;
    private View emptyState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyState = findViewById(R.id.empty_state);
        switcher = findViewById(R.id.switcher);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyRecyclerAdapter(personArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Bagian ini tidak relevan, ini karena menggunakan switcher
        Animation animationIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation animationOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        switcher.setInAnimation(animationIn);
        switcher.setOutAnimation(animationOut);
        LottieAnimationView lottie = findViewById(R.id.lottieAnimationView);
        lottie.setAnimationFromUrl("https://assets8.lottiefiles.com/temp/lf20_Celp8h.json");
        refreshStatus();
        Button empty = findViewById(R.id.empty_btn);
        empty.setOnClickListener(view -> {
            addData();
        });
    }

    private void refreshStatus() {
        if (adapter.getItemCount() == 0 && switcher.getCurrentView() == recyclerView) {
            switcher.showNext();
        } else if (adapter.getItemCount() > 0 && switcher.getCurrentView() == emptyState) {
            switcher.showPrevious();
        }
    }

    private void addData() {
        personArrayList.add(new ModelPerson(10250, "Leonardo", "21/12/1998", "L"));
        personArrayList.add(new ModelPerson(12357, "Taufan", "21/12/1998", "L"));
        personArrayList.add(new ModelPerson(15642, "Sontani", "21/12/1998", "L"));
        personArrayList.add(new ModelPerson(18956, "Errenzii", "21/12/1998", "L"));
        refreshStatus();
    }
}