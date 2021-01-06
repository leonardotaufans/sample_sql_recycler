package com.leontaufan.wuttpad;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SecondActivity extends AppCompatActivity {

    FrameLayout frameForFragment;
    boolean isfirstFragment = true;

    /**
     * Untuk melihat salah satu penggunaan utama dari Fragment, lihat tutorial ini:
     * https://guides.codepath.com/android/fragment-navigation-drawer
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /* Inisialisasi layout */
        frameForFragment = findViewById(R.id.frame_for_fragment);
        Button switchPos = findViewById(R.id.btn_next_fragment);

        /* Inisialisasi Fragment (opsional, lihat alternatif di baris 41) */
        FirstFragment first = new FirstFragment();
        SecondFragment second = new SecondFragment();

        /*
         * Inisialisasi FragmentManager yang digunakan untuk mengganti Fragment.
         * Alternatif dapat dilihat di baris 43
         */
        FragmentManager manager = getSupportFragmentManager();

        /*
         * Default value. Setiap pergantian FragmentTransaction harus diakhiri dengan commit.
         * Dapat diganti dengan:
         * manager.beginTransaction().add(R.id.frame_for_fragment, new FirstFragment()).commit();
         * Jika tidak ingin menginisalisasi FragmentManager, dapat diganti dengan:
         * getSupportFragmentManager().beginTransaction.add(R.id.frame_for_fragment, first).commit();
         * Ini hanya disarankan jika Fragment hanya perlu diganti sekali saja.
         */
        manager.beginTransaction().add(R.id.frame_for_fragment, first).commit();

        switchPos.setOnClickListener(view -> {
            if (isfirstFragment) {
                /*
                 * Setiap mengganti Fragment yang sudah di commit, harus dimulai ulang dari
                 * FragmentManager.beginTransaction()
                 * dan di-replace dengan Fragment tujuan. Agar bisa kembali ke Fragment sebelumnya,
                 * ditambahkan addToBackStack(null)
                 */
                manager.beginTransaction()
                        .replace(R.id.frame_for_fragment, second)
                        .addToBackStack(null)
                        .commit();
                switchPos.setText("Previous Fragment");
                isfirstFragment = false;
            } else {
                /*
                 * Untuk kembali ke Fragment sebelumnya, digunakan FragmentManager.popBackStack()
                 */
                manager.popBackStack();
                switchPos.setText("Next Fragment");
                isfirstFragment = true;
            }
        });

    }
}