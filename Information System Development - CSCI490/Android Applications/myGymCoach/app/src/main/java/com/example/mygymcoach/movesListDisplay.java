package com.example.mygymcoach;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class movesListDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private moveAdapter MAdapter;
    private TextView memberFName;
    private TextView memberLName;
    private ImageView memberImage;
    private ArrayList<moveItem> moveItems;
    private ProgressBar customMovesProgressBar;
    private Button assignWorkoutButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestoreDB;
    FirebaseUser currentUser;
    private String currentUid;
    private String userUid;
    private EditText moveFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moves_display_fragment);

        customMovesProgressBar = findViewById(R.id.movesProgressBar);
        recyclerView = findViewById(R.id.movesPreview);
        assignWorkoutButton = findViewById(R.id.assignWorkoutButton);
        memberFName = findViewById(R.id.assignedMemberFName);
        memberLName = findViewById(R.id.assignedMemberLName);
        memberImage = findViewById(R.id.assignedMemberImage);
        assignWorkoutButton = findViewById(R.id.assignWorkoutButton);
        moveFilter = findViewById(R.id.searchET);

        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUid = currentUser.getUid();


        final Intent memberEntered = getIntent();

        userUid = memberEntered.getStringExtra("token");

        moveFilter.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filter(s.toString());
            }
        });

        memberFName.setText(memberEntered.getStringExtra("First Name"));
        memberLName.setText(memberEntered.getStringExtra("Last Name"));

        String ImageSrc = memberEntered.getStringExtra("Image");

        Glide.with(this).load(ImageSrc).placeholder(R.drawable.personplaceholder)
                .into(memberImage);



        recyclerView.setHasFixedSize(true);
        if (movesListDisplay.this != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(movesListDisplay.this));
        }

        moveItems = new ArrayList<>();

        String url = "https://gym-senior-2020.000webhostapp.com/returnMovesAction.php";
        // Request a json response from the provided URL.
        final RequestQueue requestQueue = Volley.newRequestQueue(movesListDisplay.this);

        JsonArrayRequest jsonRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {

                        try {
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject move = ja.getJSONObject(i);
                                int ID = Integer.parseInt(move.getString("ID"));
                                int machineID = Integer.parseInt(move.getString("machineID"));
                                String Name = move.getString("Name");
                                String Image1 = move.getString("Image1");
                                String Image2 = move.getString("Image2");
                                String Gif = move.getString("Gif");
                                String Description = move.getString("Description");
                                String muscleGroup1 = move.getString("muscleGroup1");
                                String machineName = move.getString("machineName");
                                int floor = Integer.parseInt(move.getString("floor"));
                                String section = move.getString("section");
                                String machineImage = move.getString("machineImage");

                                String Image1URL = "https://gym-senior-2020.000webhostapp.com/Moves%20Images/" + Image1;
                                String Image2URL = "https://gym-senior-2020.000webhostapp.com/Moves%20Images/" + Image2;
                                String GifURL = "https://gym-senior-2020.000webhostapp.com/Moves%20Gifs/" + Gif;
                                String machineImageURL = "https://gym-senior-2020.000webhostapp.com/Machines%20Images/" + machineImage;

                                moveItem moveItem = new moveItem(ID, machineID, Name, Image1URL,
                                        Image2URL, GifURL, machineImageURL, Description,
                                        muscleGroup1, floor, section, machineName);
                                moveItems.add(moveItem);
                            }

                            MAdapter = new moveAdapter(moveItems, movesListDisplay.this);
                            recyclerView.setAdapter(MAdapter);
                            customMovesProgressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (movesListDisplay.this != null) {
                    if (error instanceof TimeoutError)
                        Toast.makeText(movesListDisplay.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NoConnectionError)
                        Toast.makeText(movesListDisplay.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ServerError)
                        Toast.makeText(movesListDisplay.this, "Server Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ParseError)
                        Toast.makeText(movesListDisplay.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(movesListDisplay.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });


        requestQueue.add(jsonRequest);

        final ArrayList<Integer> selectedMoves = new ArrayList<>();
        assignWorkoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = memberEntered.getIntExtra("ID", 0);


                final JSONArray jsonSelect = new JSONArray();
                jsonSelect.put(id);
                jsonSelect.put(currentUid);


                for(int i = 0; i < moveItems.size(); i++){
                    if(moveItems.get(i).isSelected()) jsonSelect.put(moveItems.get(i).getMoveId());
                }

                String url = "https://gym-senior-2020.000webhostapp.com/Mobile%20Actions/assignWorkoutAction.php";
                // Instantiate the RequestQueue.

                StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response)
                            {
                                String coachFName = "";
                                String coachLName = "";
                                JSONObject coachDetails = null;
                                try {
                                    coachDetails = new JSONObject(response);
                                    coachFName = coachDetails.getString("First Name");
                                    coachLName = coachDetails.getString("Last Name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.i("Coach Name", coachFName);

                                Map<String, Object> notificationMessage = new HashMap<>();
                                notificationMessage.put("title", "You Have Been Assigned a Workout!");
                                notificationMessage.put("message",coachFName + " " + coachLName + " has assigned you a workout, " +
                                        "refresh your assigned workouts tab to see it.");
                                notificationMessage.put("from", currentUid);

                                firestoreDB.collection("users/" + userUid + "/Notifications")
                                        .add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(movesListDisplay.this, "Workout Assigned!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(movesListDisplay.this,
                                                "An error occurred while sending the notification",
                                                Toast.LENGTH_SHORT).show();
                                        Log.i("notification", e.getMessage());
                                    }
                                });
                            }
                        }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if(movesListDisplay.this != null)
                        {
                            if (error instanceof TimeoutError)
                                Toast.makeText(movesListDisplay.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof NoConnectionError)
                                Toast.makeText(movesListDisplay.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof ServerError)
                                Toast.makeText(movesListDisplay.this, "Server Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof ParseError)
                                Toast.makeText(movesListDisplay.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(movesListDisplay.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("moves", jsonSelect.toString());
                        return params;
                    }
                };


                requestQueue.add(stringRequest);

            }
        });

    }

    private void filter(String toString)
    {
        String filterText = toString;
        ArrayList<moveItem> filtered = new ArrayList<>();
        String[] Split = filterText.split(" ");
        for(moveItem item: moveItems)
        {
            int match = 0;
            for(int i = 0; i < Split.length; i++)
            {
                if(item.getMachineName().toLowerCase().contains(Split[i].toLowerCase()) ||
                        item.getMuscleGroup1().toLowerCase().contains(Split[i].toLowerCase()) ||
                        item.getMoveName().toLowerCase().contains(Split[i].toLowerCase()))
                {
                    match++;

                }
            }
            if(match == Split.length) filtered.add(item);
        }
        MAdapter.filterList(filtered);
    }
}

