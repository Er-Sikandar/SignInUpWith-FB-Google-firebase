package com.example.firebasereglogapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasereglogapp.ModelCustom;
import com.example.firebasereglogapp.R;

import java.util.ArrayList;

public class MyReAdapter extends RecyclerView.Adapter<MyReAdapter.ViewHolder> {
    ArrayList<ModelCustom> dataSet=new ArrayList<>();
    Context context;

    public MyReAdapter(ArrayList<ModelCustom> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view= LayoutInflater.from(parent.getContext())
        .inflate(R.layout.re_layout,parent,false);
        return new MyReAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReAdapter.ViewHolder holder, int position) {
    ModelCustom modelCustom=dataSet.get(position);
     holder.name.setText(modelCustom.getName());
     holder.lastname.setText(modelCustom.getLastname());
     holder.age.setText(modelCustom.getAge());

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,lastname,age;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        name=itemView.findViewById(R.id.text1);
        lastname=itemView.findViewById(R.id.text2);
        age=itemView.findViewById(R.id.text3);
        }
    }
}
