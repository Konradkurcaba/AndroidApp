package com.example.konrad.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.MyViewHolder> {

    private List<Diet> dietList;
    private MainActivity m;

    public class MyViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        public TextView title, summary;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            summary = (TextView) view.findViewById(R.id.summary);

        }

        @Override
        public void onClick(View v) { // start DietProperties activity
        Intent myIntent = new Intent(m, DietProperties.class);
        myIntent.putExtra("title",dietList.get(getAdapterPosition()).getTitle());
        myIntent.putExtra("summary",dietList.get(getAdapterPosition()).getSummary());
        myIntent.putExtra("description",dietList.get(getAdapterPosition()).getDesctiption());
        myIntent.putExtra("mealDate",dietList.get(getAdapterPosition()).getMealDate());
        m.startActivity(myIntent);
        }
    }
    public DietAdapter(List<Diet> dietList,MainActivity m)
    {
        this.dietList = dietList;
        this.m = m;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diet_list_row,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Diet diet = dietList.get(position);
        holder.title.setText(diet.getTitle());
        holder.summary.setText(diet.getSummary());
    }

    @Override
    public int getItemCount() {
        return dietList.size();
    }



}

