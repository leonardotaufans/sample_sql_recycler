package com.leontaufan.wuttpad;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    /**
     * Data personList akan diambil dari MainActivity saat inisialisasi RecyclerView
     * {@link MainActivity}
     */
    private List<ModelPerson> personList;

    /**
     * Constructor ini akan mengisi data personList di atas
     * @param personList datang dari atas sana ^
     */
    public MyRecyclerAdapter(List<ModelPerson> personList) {
        this.personList = personList;
    }

    /**
    Secara berurutan, yang harus dibaca adalah
     1. MyViewHolder (untuk inisialisasi layout RecyclerView) {@link MyViewHolder}
     2. onCreateViewHolder (yang menampilkan layout dari viewholder) {@link MyRecyclerAdapter#onCreateViewHolder}
     3. onBindViewHolder (untuk memasukkan data ke dalam layout {@link MyRecyclerAdapter#onBindViewHolder}

     onCreateViewHolder bertugas untuk memunculkan child layout (di kasus ini person_card.xml) ke dalam RecyclerView.
     */
    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.person_card, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * onBindViewHolder digunakan untuk menginput data yang ada di child layout.
     *
     * @param holder datang dari MyViewHolder {@link MyViewHolder} yang memegang View.
     * @param position datang dari RecyclerView yang menunjukkan
     *                 posisi view yang sedang diisi.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(personList.get(position).getName());
        holder.birth.setText(personList.get(position).getDateOfBirth());
        holder.gender.setText(personList.get(position).getGender());
        holder.update.setOnClickListener(view -> {
            // todo: Melakukan update pada database
            Log.d("DEBUG", "Update!");

        });
        holder.delete.setOnClickListener(view -> {
            //todo: Melakukan delete pada database
            Log.d("DEBUG", "Delete!");
        });
    }

    /**
     * getItemCount digunakan untuk menghitung jumlah item yang ada di RecyclerView. Nilai diambil
     * dari {@link #personList}
     * @return harus memeriksa null jika data masih benar-benar kosong.
     */
    @Override
    public int getItemCount() {
        return (personList != null) ? personList.size() : 0;
    }

    /**
     * MyViewHolder adalah child class dari ViewHolder (tentunya)
     * yang ditujukan untuk mengendalikan child layout.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, birth, gender;
        private Button update, delete;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.person_name);
            birth = view.findViewById(R.id.person_dateofbirth);
            gender = view.findViewById(R.id.person_gender);
            update = view.findViewById(R.id.update_btn);
            delete = view.findViewById(R.id.delete_btn);
        }
    }
}
