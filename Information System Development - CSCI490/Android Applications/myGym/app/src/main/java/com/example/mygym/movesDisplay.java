package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class movesDisplay extends AppCompatActivity
{
    private ImageView moveImage;
    private ImageView moveClose;
    private TextView titleTextView;
    private TextView mainMuscleTextView;
    private TextView equipmentTextView;
    private TextView descriptionTextView;
    private Button addToCurrentButton;
    locationDialogue locationDialogue;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves_display);

        moveImage = findViewById(R.id.moveImage);

        ViewTreeObserver vto = moveImage.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                moveImage.getViewTreeObserver().removeOnPreDrawListener(this);
                moveImage.getLayoutParams().height = moveImage.getMeasuredWidth();
                moveImage.requestLayout();
                return true;
            }
        });

        mAuth = FirebaseAuth.getInstance();

        moveClose = findViewById(R.id.moveClose);
        titleTextView = findViewById(R.id.moveName);
        mainMuscleTextView = findViewById(R.id.moveMainMuscle);
        equipmentTextView = findViewById(R.id.moveEquipment);
        descriptionTextView = findViewById(R.id.moveDescription);
        addToCurrentButton = findViewById(R.id.addToCurrentWorkout);

        moveClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();

        titleTextView.setText(intent.getStringExtra("title"));
        mainMuscleTextView.setText(intent.getStringExtra("muscle"));
        equipmentTextView.setText(intent.getStringExtra("machineName"));
        descriptionTextView.setText(intent.getStringExtra("description"));

        equipmentTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDialogue();
            }
        });

        String machineName = intent.getStringExtra("machineName");
        String GifURL = intent.getStringExtra("Gif");
        String machineImageURL = intent.getStringExtra("machineImage");
        int floor = intent.getIntExtra("floor", 0);
        String section = intent.getStringExtra("section");
        final int moveID = intent.getIntExtra("id", 0);

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        Glide.with(this).asGif().load(GifURL).placeholder(R.drawable.personplaceholder)
                .into(moveImage);


        addToCurrentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateCurrentWorkout(moveID, currentUser.getUid());
            }
        });


        locationDialogue = new locationDialogue();
        Bundle bundle = new Bundle();
        bundle.putString("machineImage", machineImageURL);
        bundle.putInt("floor", floor);
        bundle.putString("section", section);
        bundle.putString("machineName", machineName);
        locationDialogue.setArguments(bundle);


    }

    private void updateCurrentWorkout(final int moveID, final String memberToken)
    {
        RequestQueue queue = Volley.newRequestQueue(movesDisplay.this);

        String url = "https://gym-senior-2020.000webhostapp.com/updateCurrentWorkoutAction.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("crap", response);
                        Toast.makeText(movesDisplay.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if(movesDisplay.this != null)
                {
                    if (error instanceof TimeoutError)
                        Toast.makeText(movesDisplay.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NoConnectionError)
                        Toast.makeText(movesDisplay.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ServerError)
                        Toast.makeText(movesDisplay.this, "Server Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ParseError)
                        Toast.makeText(movesDisplay.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(movesDisplay.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("memberToken", memberToken);
                params.put("moveID", Integer.toString(moveID));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void openDialogue()
    {
        locationDialogue.show(getSupportFragmentManager(), "Location Dialogue");
    }
}
