package com.example.mygym;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class workoutAdapter extends RecyclerView.Adapter<workoutAdapter.ViewHolder>
{

    private List<workoutItem> workoutItems;
    private Context context;

    public workoutAdapter(List<workoutItem> workoutItems, Context context)
    {
        this.workoutItems = workoutItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_listing, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        final workoutItem workoutItem= workoutItems.get(position);

        holder.assignedBy.setText(workoutItem.getAssignedBy());
        final int workoutID = workoutItem.getWorkoutID();
        SimpleDateFormat formatter = new SimpleDateFormat("E, MMMM dd YYYY 'at' hh:mm a");
        String date = formatter.format(workoutItem.getAssignedDate());
        holder.assignedDate.setText(date);
        holder.movesCount.setText(Integer.toString(workoutItem.getMovesCount()));

        Glide.with(context).load(workoutItem.getWorkoutImage()).placeholder(R.drawable.workoutplaceholder).into(holder.workoutImage);
        holder.workoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent displayMovesIntent = new Intent(context, scannedMachineDetails.class);
                displayMovesIntent.putExtra("workoutID", workoutID);
                context.startActivity(displayMovesIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return workoutItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public RelativeLayout workoutLayout;
        public TextView assignedBy;
        public TextView assignedDate;
        public TextView movesCount;
        public ImageView workoutImage;

        public ViewHolder(View itemView)
        {
            super(itemView);

            workoutLayout = itemView.findViewById(R.id.workoutLayout);
            assignedBy = itemView.findViewById(R.id.assignedBy);
            assignedDate = itemView.findViewById(R.id.assignedDate);
            workoutImage = itemView.findViewById(R.id.workoutImage);
            movesCount = itemView.findViewById(R.id.movesCount);
        }
    }

    public void filterList(ArrayList<workoutItem> filteredList)
    {
        this.workoutItems = filteredList;
        notifyDataSetChanged();
    }

}
