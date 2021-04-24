package com.example.mygym;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class menuItemDisplay extends AppCompatActivity
{
    private ImageView menuItemImage;
    private TextView menuItemName;
    private TextView menuItemDescription;
    private TextView menuItemProtein;
    private TextView menuItemSugar;
    private TextView menuItemFats;
    private TextView menuItemCarbohydrates;
    private TextView menuItemCalories;
    private TextView menuItemPrice;
    private TextView menuItemPreparationTime;

    private ImageView menuItemClose;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuitem_display);

        menuItemImage = findViewById(R.id.menuItemImage);

        ViewTreeObserver vto = menuItemImage.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                menuItemImage.getViewTreeObserver().removeOnPreDrawListener(this);
                menuItemImage.getLayoutParams().height = menuItemImage.getMeasuredWidth();
                menuItemImage.requestLayout();
                return true;
            }
        });

        menuItemClose = findViewById(R.id.menuItemClose);
        menuItemClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menuItemName = findViewById(R.id.menuItemName);
        menuItemDescription = findViewById(R.id.menuItemDescription);
        menuItemPreparationTime = findViewById(R.id.menuItemPreparation);
        menuItemPrice = findViewById(R.id.menuItemPrice);
        menuItemProtein = findViewById(R.id.menuItemProtein);
        menuItemSugar = findViewById(R.id.menuItemSugar);
        menuItemFats = findViewById(R.id.menuItemFats);
        menuItemCarbohydrates = findViewById(R.id.menuItemCarbohydrates);
        menuItemCalories = findViewById(R.id.menuItemCalories);


        Intent intent = getIntent();


        menuItemName.setText(intent.getStringExtra("menuItemName"));
        menuItemDescription.setText(intent.getStringExtra("menuItemDescription"));

        menuItemProtein.setText("Protein: " + (intent.getIntExtra("Protein", 0)) + "g");
        menuItemSugar.setText("Sugar: " + intent.getIntExtra("Sugar", 0) + "g");
        menuItemFats.setText("Fats: " + intent.getIntExtra("Fats", 0) + "g");
        menuItemCarbohydrates.setText("Carbohydrates: " + intent.getIntExtra("Carbohydrates", 0) + "g");
        menuItemCalories.setText("Calories: " + intent.getIntExtra("Calories", 0) + "kcal");
        menuItemPreparationTime.setText("Preparation: " + intent.getIntExtra("prepTime", 0) + " minutes");
        menuItemPrice.setText(intent.getDoubleExtra("Price", 0) + "$");

        String itemImage = intent.getStringExtra("menuItemImage");

        Glide.with(this).load(itemImage).listener(new RequestListener<Drawable>()
        {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(menuItemDisplay.this, "Image Failed to Load", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).placeholder(R.drawable.personplaceholder).into(menuItemImage);

    }
}
