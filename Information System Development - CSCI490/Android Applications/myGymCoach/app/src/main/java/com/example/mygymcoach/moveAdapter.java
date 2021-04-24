package com.example.mygymcoach;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class moveAdapter extends RecyclerView.Adapter<moveAdapter.ViewHolder>
{

    private List<moveItem> moveItems;
    private Context context;

    public moveAdapter(List<moveItem> moveItems, Context context)
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

        holder.movesLayout.setTag(moveItem.getMoveId());
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

        holder.selected.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        holder.selected.setChecked(moveItem.isSelected());
        holder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                moveItem.setSelected(isChecked);
            }
        });

        holder.movesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                moveItem.setSelected(!moveItem.isSelected());
                holder.selected.setChecked(moveItem.isSelected());
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
        public RelativeLayout movesLayout;
        public ProgressBar moveProgressBar;
        public CheckBox selected;
        public ViewHolder(View itemView)
        {
            super(itemView);
            movesLayout = itemView.findViewById(R.id.movesLayout);
            moveTitle = itemView.findViewById(R.id.move_name);
            moveProgressBar = itemView.findViewById(R.id.moveProgessBar);
            moveThumbnail = itemView.findViewById(R.id.move_thumbnail);
            selected = itemView.findViewById(R.id.move_checkBox);
        }
    }

    public void filterList(ArrayList<moveItem> filteredList)
    {
        this.moveItems = filteredList;
        notifyDataSetChanged();
    }

}
