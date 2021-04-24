package com.example.mygym;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class locationDialogue extends AppCompatDialogFragment
{
    private ImageView schema;
    private ImageView machineImage;
    private TextView machineLocaiton;
    private ProgressBar machineProgressBar;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.location_layout,null);

        schema = view.findViewById(R.id.schemaImage);
        machineImage = view.findViewById(R.id.machineImage);
        machineLocaiton = view.findViewById(R.id.notNeededTextId);
        machineProgressBar = view.findViewById(R.id.machineProgressbar);

        String machineImageURL = getArguments().getString("machineImage");
        Glide.with(this).load(machineImageURL).placeholder(R.drawable.personplaceholder).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Error loading machine image", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                machineProgressBar.setVisibility(View.GONE);
                return false;
            }
        })
                .into(machineImage);

        ViewTreeObserver vto = machineImage.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                machineImage.getViewTreeObserver().removeOnPreDrawListener(this);
                machineImage.getLayoutParams().height = machineImage.getMeasuredWidth();
                machineImage.requestLayout();
                return true;
            }
        });


        int floor = getArguments().getInt("floor");
        String section = getArguments().getString("section");
        String machineName = getArguments().getString("machineName");

        switch (section)
        {
            case "A": schema.setImageResource(R.drawable.f1sa); break;
            case "B": schema.setImageResource(R.drawable.f1sb); break;
            case "C": schema.setImageResource(R.drawable.f1sc); break;
            case "D": schema.setImageResource(R.drawable.f1sd); break;
            case "Cardio": schema.setImageResource(R.drawable.f1scardio); break;
        }

        machineLocaiton.setText(machineName + " is found on floor " + floor + " in section " +section);


        builder.setView(view).setTitle("Equipment Location").setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
