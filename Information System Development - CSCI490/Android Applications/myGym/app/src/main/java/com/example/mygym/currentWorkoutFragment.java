package com.example.mygym;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class currentWorkoutFragment extends Fragment
{

    private RecyclerView recyclerView;
    private currentMoveAdapter CMAdapter;
    private ArrayList<moveItem> moveItems;
    private ProgressBar currentMovesProgressBar;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button finishWorkout;
    RecyclerView.LayoutManager layoutManager;
    private String token;
    private TextView emptyTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.current_workout_fragment, container, false);

        currentMovesProgressBar = rootView.findViewById(R.id.currentWorkoutProgressBar);
        recyclerView = rootView.findViewById(R.id.currentWorkoutPreview);
        swipeRefreshLayout = rootView.findViewById(R.id.currentworkoutSwipeRefreshLayout);
        finishWorkout = rootView.findViewById(R.id.currentWorkoutFinish);
        emptyTextView = rootView.findViewById(R.id.emptyTextView);

        finishWorkout.setVisibility(View.GONE);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                populate();
            }
        });

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        if(getActivity() != null)
        {
            recyclerView.setLayoutManager(layoutManager);
        }

        finishWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        moveItems = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        token = currentUser.getUid();

        populate();


        return rootView;
    }

    public void finish()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        swipeRefreshLayout.setRefreshing(true);
        String url = "https://gym-senior-2020.000webhostapp.com/finishWorkoutAction.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response)
                    {
                        populate();
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if(getContext() != null)
                {
                    if (error instanceof TimeoutError)
                        Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NoConnectionError)
                        Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ServerError)
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ParseError)
                        Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void populate()
    {

        String memberToken = currentUser.getUid();

        String url = "https://gym-senior-2020.000webhostapp.com/returnCurrentWorkoutAction.php?param1=" + memberToken;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {
                        moveItems.clear();
                        swipeRefreshLayout.setRefreshing(false);
                        currentMovesProgressBar.setVisibility(View.GONE);
                        try {
                            if(ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    Log.i("crap", ja.toString());
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
                                CMAdapter = new currentMoveAdapter(moveItems, getContext());
                                recyclerView.setAdapter(CMAdapter);
                                CMAdapter.notifyDataSetChanged();
                                finishWorkout.setVisibility(View.VISIBLE);
                                emptyTextView.setVisibility(View.GONE);
                            } else {
                                emptyTextView.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(getContext() != null)
                {
                    if (error instanceof TimeoutError)
                        Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NoConnectionError)
                        Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ServerError)
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ParseError) {
                        Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    currentMovesProgressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);


                }

            }
        });

        requestQueue.add(jsonRequest);
    }

}
