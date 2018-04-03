package com.example.konrad.app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.MyViewHolder> {

    private List<Diet> dietList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, summary;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            summary = (TextView) view.findViewById(R.id.summary);


        }
    }
    public DietAdapter(List<Diet> dietList)
    {
        this.dietList = dietList;
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

