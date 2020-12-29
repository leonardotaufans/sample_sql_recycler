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

    private List<ModelPerson> personList;

    public MyRecyclerAdapter(List<ModelPerson> personList) {
        this.personList = personList;
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.person_card, parent, false);
        return new MyViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return (personList != null) ? personList.size() : 0;
    }

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
