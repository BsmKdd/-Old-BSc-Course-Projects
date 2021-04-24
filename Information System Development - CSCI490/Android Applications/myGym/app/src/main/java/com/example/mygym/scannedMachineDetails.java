package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class scannedMachineDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private currentMoveAdapter CMAdapter;
    private ArrayList<moveItem> moveItems;
    private ProgressBar scannedMachineProgressBar;
    private TextView scannedMachineTitle, errorTV;
    private RelativeLayout justTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_machine_details);

        recyclerView = findViewById(R.id.scannedMachinePreview);
        scannedMachineProgressBar = findViewById(R.id.scannedMachineProgressBar);
        recyclerView = findViewById(R.id.scannedMachinePreview);
        scannedMachineTitle = findViewById(R.id.scannedMachineTitle);
        justTitle = findViewById(R.id.justTitle);
        errorTV = findViewById(R.id.errorText);
        recyclerView.setHasFixedSize(true);
        if (this != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        moveItems = new ArrayList<>();

        Intent codeScanned = getIntent();
        String code;
        int workoutID;
        String url = null;

        if(codeScanned.getStringExtra("scannedQR") !=null )
        {
            code = codeScanned.getStringExtra("scannedQR");
            url = "https://gym-senior-2020.000webhostapp.com/returnMachineMovesAction.php?param1="+code;

        } else if (codeScanned.getIntExtra("workoutID", 0) != -1)
        {
            workoutID = codeScanned.getIntExtra("workoutID", 0);
            url = "https://gym-senior-2020.000webhostapp.com/returnWorkoutMovesAction.php?param1="+workoutID;
            justTitle.setVisibility(View.GONE);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {

                        try {
                            scannedMachineProgressBar.setVisibility(View.VISIBLE);

                            for (int i = 0;i < ja.length();i++) {
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
                                scannedMachineTitle.setText(machineName);
                            }

                            CMAdapter = new currentMoveAdapter(moveItems, scannedMachineDetails.this);
                            recyclerView.setAdapter(CMAdapter);
                            scannedMachineProgressBar.setVisibility(View.GONE);
                            errorTV.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorTV.setVisibility(View.VISIBLE);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(scannedMachineDetails.this != null)
                {
                    if (error instanceof TimeoutError)
                        Toast.makeText(scannedMachineDetails.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NoConnectionError)
                        Toast.makeText(scannedMachineDetails.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ServerError)
                        Toast.makeText(scannedMachineDetails.this, "Server Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ParseError)
                        Toast.makeText(scannedMachineDetails.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(scannedMachineDetails.this, "Error", Toast.LENGTH_SHORT).show();
                    scannedMachineProgressBar.setVisibility(View.GONE);
                    errorTV.setVisibility(View.VISIBLE);
                }

            }
        });


        requestQueue.add(jsonRequest);

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}

