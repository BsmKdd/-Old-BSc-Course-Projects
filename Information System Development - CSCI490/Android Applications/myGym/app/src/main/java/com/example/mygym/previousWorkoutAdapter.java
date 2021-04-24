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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class previousWorkoutAdapter extends RecyclerView.Adapter<previousWorkoutAdapter.ViewHolder>
{

    private List<previousWorkoutItem> previousWorkoutItems;
    private Context context;

    public previousWorkoutAdapter(List<previousWorkoutItem> previousWorkoutItems, Context context)
    {
        this.previousWorkoutItems = previousWorkoutItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_workout_listing, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        final previousWorkoutItem previousWorkoutItem = previousWorkoutItems.get(position);

        final int workoutID = previousWorkoutItem.getWorkoutID();
        SimpleDateFormat formatter = new SimpleDateFormat("E, MMMM dd YYYY 'at' hh:mm a");
        String date = formatter.format(previousWorkoutItem.getFinishedOn());
        holder.finishedOn.setText(date);
        holder.movesCount.setText(Integer.toString(previousWorkoutItem.getMovesCount()));
        holder.previousWorkoutLayout.setOnClickListener(new View.OnClickListener() {
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
        return previousWorkoutItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public RelativeLayout previousWorkoutLayout;
        public TextView finishedOn;
        public TextView movesCount;

        public ViewHolder(View itemView)
        {
            super(itemView);

            previousWorkoutLayout = itemView.findViewById(R.id.previousWorkoutLayout);
            finishedOn = itemView.findViewById(R.id.finishDate);
            movesCount = itemView.findViewById(R.id.previousMovesCount);
        }
    }

    public void filterList(ArrayList<previousWorkoutItem> filteredList)
    {
        this.previousWorkoutItems = filteredList;
        notifyDataSetChanged();
    }

}
