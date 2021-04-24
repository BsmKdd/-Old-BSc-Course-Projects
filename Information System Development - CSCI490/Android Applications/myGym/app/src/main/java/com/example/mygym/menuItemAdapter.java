package com.example.mygym;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class menuItemAdapter extends RecyclerView.Adapter<menuItemAdapter.ViewHolder>
{

    private List<menuItem> menuItems;
    private Context context;

    public menuItemAdapter(List<menuItem> menuItems, Context context)
    {
        this.menuItems = menuItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitem_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        final menuItem menuItem = menuItems.get(position);

        holder.menuItemsLayout.setTag(menuItem.getMenuItemId());
        holder.menuItemName.setText(menuItem.getMenuItemName());

        Glide.with(context).load(menuItem.getMenuItemImage()).listener(new RequestListener<Drawable>()
        {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.menuItemProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.menuItemProgressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.menuItemImage);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("You can only select one meal, salad, and drink at a time.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert11 = builder1.create();

        if(menuItem.getSelected() == 0)
        {
            holder.menuItemsLayout.setForeground(null);
            holder.menuItemAdd.setEnabled(true);
        } else holder.menuItemsLayout.setForeground(new ColorDrawable(ContextCompat.getColor(context, R.color.selectedMenuItem)));


        holder.menuItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackBar = Snackbar.make(holder.menuItemsLayout, "Item added to your order", Snackbar.LENGTH_SHORT);
                snackBar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call your action method here
                        snackBar.dismiss();
                    }
                });

                boolean MealORSaladORDrink = false;
                if(!menuItem.getMenuItemType().equalsIgnoreCase("bar"))
                {
                    for(int i = 0; i < menuItems.size(); i++)
                    {
                        if(menuItems.get(i).getSelected() > 0)
                        {
                            alert11.show();
                            MealORSaladORDrink = true;
                        }
                    }
                    if(!MealORSaladORDrink)
                    {
                        menuItem.setSelected(1);
                        holder.menuItemsLayout.setForeground(new ColorDrawable(ContextCompat.getColor(context, R.color.selectedMenuItem)));
                        holder.menuItemAdd.setEnabled(false);
                        snackBar.show();
                    }
                } else {
                    menuItem.setSelected(menuItem.getSelected() + 1);
                    holder.menuItemsLayout.setForeground(new ColorDrawable(ContextCompat.getColor(context, R.color.selectedMenuItem)));
                    snackBar.show();
                }
            }
        });


        holder.menuItemsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent menuItemDetails = new Intent(holder.itemView.getContext(), menuItemDisplay.class);
                menuItemDetails.putExtra("menuItemName", menuItem.getMenuItemName());
                menuItemDetails.putExtra("menuItemDescription", menuItem.getMenuItemDescription());
                menuItemDetails.putExtra("Protein", menuItem.getMenuItemProtein());
                menuItemDetails.putExtra("Sugar", menuItem.getMenuItemSugar());
                menuItemDetails.putExtra("Fats", menuItem.getMenuItemFats());
                menuItemDetails.putExtra("Carbohydrates", menuItem.getMenuItemCarbohydrates());
                menuItemDetails.putExtra("Calories", menuItem.getMenuItemCalories());
                menuItemDetails.putExtra("Price", menuItem.getMenuItemPrice());
                menuItemDetails.putExtra("prepTime", menuItem.getMenuPreparation());
                menuItemDetails.putExtra("menuItemImage", menuItem.getMenuItemImage());
                context.startActivity(menuItemDetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView menuItemName;
        public ImageView menuItemImage;
        public FrameLayout menuItemsLayout;
        public Button menuItemAdd;
        public ProgressBar menuItemProgressBar;

        public ViewHolder(View itemView)
        {
            super(itemView);
            menuItemsLayout = itemView.findViewById(R.id.menuItemLayout);
            menuItemName = itemView.findViewById(R.id.menuItemName);
            menuItemAdd = itemView.findViewById(R.id.menuItemAdd);
            menuItemImage = itemView.findViewById(R.id.menuItemImage);
            menuItemProgressBar = itemView.findViewById(R.id.menuItemProgressBar);
        }
    }

    public void filterList(ArrayList<menuItem> filteredList)
    {
        this.menuItems = filteredList;
        notifyDataSetChanged();
    }
}
