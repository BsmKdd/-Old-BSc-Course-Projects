package com.example.mygym;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
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

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;

public class orderConfirm extends AppCompatDialogFragment
{
    private TextView mealName;
    private TextView mealPrice;
    private TextView saladName;
    private TextView saladPrice;
    private TextView drinkName;
    private TextView drinkPrice;
    private TextView barName;
    private TextView barPrice;
    private TextView totalPrice;
    private TextView totalCalories;
    private TextView totalProtein;
    private TextView totalSugar;
    private TextView totalFats;
    private TextView totalCarbohydrates;
    private TextView preperationTime;

    private Button cancelOrder;
    private Button confirmOrder;

    private confirmDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.confirm_order_layout,null);

        mealName = view.findViewById(R.id.mealName);
        mealPrice = view.findViewById(R.id.mealPrice);
        saladName = view.findViewById(R.id.saladName);
        saladPrice = view.findViewById(R.id.saladPrice);
        drinkName = view.findViewById(R.id.drinkName);
        drinkPrice = view.findViewById(R.id.drinkPrice);
        barName = view.findViewById(R.id.barName);
        barPrice = view.findViewById(R.id.barPrice);
        totalPrice = view.findViewById(R.id.totalPrice);
        totalCalories = view.findViewById(R.id.totalCaloriesNum);
        totalProtein = view.findViewById(R.id.totalProteinNum);
        totalSugar = view.findViewById(R.id.totalSugarNum);
        totalFats = view.findViewById(R.id.totalFatsNum);
        totalCarbohydrates = view.findViewById(R.id.totalCarbohydratesNum);
        preperationTime = view.findViewById(R.id.preparationTime);
        cancelOrder = view.findViewById(R.id.cancelOrderButton);
        confirmOrder = view.findViewById(R.id.confirmOrderButton);

        int prepTime = getArguments().getInt("prepTime");
        double mPrice = getArguments().getDouble("mealPrice");
        double sPrice = getArguments().getDouble("saladPrice");
        double dPrice = getArguments().getDouble("drinkPrice");
        String bPrice = getArguments().getString("barPrice");
        String tPrice = getArguments().getString("totalPrice");

        String tCalories = getArguments().getString("totalCalories");
        String tProtein = getArguments().getString("totalProtein");
        String tSugar = getArguments().getString("totalSugar");
        String tFats = getArguments().getString("totalFats");
        String tCarbohydrates = getArguments().getString("totalCarbohydrates");


        String mName = getArguments().getString("mealName");
        String sName = getArguments().getString("saladName");
        String dName = getArguments().getString("drinkName");
        String bName = getArguments().getString("barName");

        final ArrayList<Integer> selectedIDs = getArguments().getIntegerArrayList("selectedIDs");

        if(mName.equals("")) mealName.setVisibility(View.GONE);
        else mealName.setText("1x " + mName);

        if(sName.equals("")) saladName.setVisibility(View.GONE);
        else saladName.setText("1x " + sName);

        if(dName.equals("")) drinkName.setVisibility(View.GONE);
        else drinkName.setText("1x " + dName);

        if(bName.equals("")) barName.setVisibility(View.GONE);
        else barName.setText(bName);

        if(prepTime == 0) preperationTime.setText("Estimated Preparation Time: Instant");
        else preperationTime.setText("Estimated Preperation Time: " + prepTime + " minutes");

        if(mPrice == 0.0) mealPrice.setVisibility(View.GONE);
        else mealPrice.setText(mPrice + "$");

        if(sPrice == 0.0) saladPrice.setVisibility(View.GONE);
        else saladPrice.setText(sPrice + "$");

        if(dPrice == 0.0) drinkPrice.setVisibility(View.GONE);
        else drinkPrice.setText(dPrice + "$");

        if(bPrice.equals("0.0")) barPrice.setVisibility(View.GONE);
        else barPrice.setText(bPrice);

        if(tPrice.equals("0.0"))
        {
            totalPrice.setVisibility(View.GONE);
            view.findViewById(R.id.totalName).setVisibility(View.GONE);
        } else totalPrice.setText(tPrice + "$");

        if(tProtein.equals("0.0"))
        {
            totalProtein.setText(" 0");
        } else totalProtein.setText(" " + tProtein + "g");

        if(tSugar.equals("0.0"))
        {
            totalSugar.setText(" 0");
        } else totalSugar.setText(" " + tSugar + "g");

        if(tFats.equals("0.0"))
        {
            totalFats.setText(" 0");
        } else totalFats.setText(" " + tFats + "g");

        if(tCarbohydrates.equals("0.0"))
        {
            totalCarbohydrates.setText(" 0");
        } else totalCarbohydrates.setText(" " + tCarbohydrates + "g");

        if(tCalories.equals("0.0"))
        {
            totalCalories.setText(" 0");
        } else totalCalories.setText(" " + tCalories + "kcal");

        builder.setView(view);

        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("selectedIds", selectedIDs.toString());
                listener.orderConfirmed();
                getDialog().dismiss();
            }
        });

        return builder.create();
    }

    public interface confirmDialogListener
    {
        void orderConfirmed();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

        try {
            listener = (confirmDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement confirmDialogListener.");
        }
    }
}
