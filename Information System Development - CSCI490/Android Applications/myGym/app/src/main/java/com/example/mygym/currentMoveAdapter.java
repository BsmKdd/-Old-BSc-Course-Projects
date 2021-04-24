package com.example.mygym;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

public class currentMoveAdapter extends RecyclerView.Adapter<currentMoveAdapter.ViewHolder>
{

    private List<moveItem> moveItems;
    private Context context;

    public currentMoveAdapter(List<moveItem> moveItems, Context context)
    {
        this.moveItems = moveItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moves_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        final moveItem moveItem = moveItems.get(position);

        holder.currentMovesLayout.setTag(moveItem.getMoveId());
        holder.moveTitle.setText(moveItem.getMoveName());

        Glide.with(context).load(moveItem.getMoveImage1()).listener(new RequestListener<Drawable>()
        {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.moveProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.moveProgressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.moveThumbnail);


        holder.currentMovesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent moveDetails = new Intent(holder.itemView.getContext(), movesDisplay.class);
                moveDetails.putExtra("id", moveItem.getMoveId());
                moveDetails.putExtra("title", moveItem.getMoveName());
                moveDetails.putExtra("muscle", moveItem.getMuscleGroup1());
                moveDetails.putExtra("description", moveItem.getMoveDescription());
                moveDetails.putExtra("image1", moveItem.getMoveImage1());
                moveDetails.putExtra("image2", moveItem.getMoveImage2());
                moveDetails.putExtra("Gif", moveItem.getMoveGif());
                moveDetails.putExtra("floor", moveItem.getFloor());
                moveDetails.putExtra("section", moveItem.getSection());
                moveDetails.putExtra("machineImage", moveItem.getMachineImage());
                moveDetails.putExtra("machineName", moveItem.getMachineName());
                context.startActivity(moveDetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moveItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView moveTitle;
        public ImageView moveThumbnail;
        public RelativeLayout currentMovesLayout;
        public ProgressBar moveProgressBar;
        public ViewHolder(View itemView)
        {
            super(itemView);
            currentMovesLayout = itemView.findViewById(R.id.customMovesLayout);
            moveTitle = itemView.findViewById(R.id.custom_move_name);
            moveProgressBar = itemView.findViewById(R.id.moveProgessBar);
            moveThumbnail = itemView.findViewById(R.id.custom_move_thumbnail);
        }
    }

    public void filterList(ArrayList<moveItem> filteredList)
    {
        this.moveItems = filteredList;
        notifyDataSetChanged();
    }
}
