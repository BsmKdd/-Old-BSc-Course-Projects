package com.example.mygym;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class assignedWorkoutFragment extends Fragment
{

    private RecyclerView recyclerView;
    private workoutAdapter AWAdapter;
    private ArrayList<workoutItem> workoutItems;
    private ArrayList<workoutItem> filteredWorkoutItems;

    private ProgressBar assignedWorkoutProgress;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.LayoutManager layoutManager;
    private String token;
    private TextView emptyTextView;
    private Spinner rangeSpinner;
    private int spinnerPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.assigned_workout_fragment, container, false);

        assignedWorkoutProgress = rootView.findViewById(R.id.assignedWorkoutProgress);
        recyclerView = rootView.findViewById(R.id.assignedWorkoutPreview);
        swipeRefreshLayout = rootView.findViewById(R.id.assignedWorkoutSwipeRefreshLayout);
        emptyTextView = rootView.findViewById(R.id.emptyAssignedTextView);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        token = currentUser.getUid();

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                populate();
            }
        });
        rangeSpinner = rootView.findViewById(R.id.assignedWorkoutRangeSpinner);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        if(getActivity() != null)
        {
            recyclerView.setLayoutManager(layoutManager);
        }

        workoutItems = new ArrayList<>();
        filteredWorkoutItems = new ArrayList<>();
        
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        spinnerPosition = 3;


        populate();
        return rootView;
    }

    public void populate()
    {

        String memberToken = currentUser.getUid();

        String url = "https://gym-senior-2020.000webhostapp.com/returnAssignedWorkoutAction.php?token=" + memberToken;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {
                        workoutItems.clear();
                        swipeRefreshLayout.setRefreshing(false);
                        assignedWorkoutProgress.setVisibility(View.GONE);
                        try {
                            if(ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject move = ja.getJSONObject(i);
                                    int ID = Integer.parseInt(move.getString("workoutID"));
                                    String coachName = move.getString("coachName");
                                    String date = move.getString("date");
                                    int moveCounter = Integer.parseInt(move.getString("moveCount"));

                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date assignDate = formatter.parse(date);

                                    workoutItem workoutItem = new workoutItem(ID, null,
                                            null, coachName, assignDate, moveCounter);
                                    workoutItems.add(workoutItem);
                                }
                                AWAdapter = new workoutAdapter(workoutItems, getContext());
                                recyclerView.setAdapter(AWAdapter);
                                AWAdapter.notifyDataSetChanged();
                                emptyTextView.setVisibility(View.GONE);

                                rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                    {
                                        spinnerPosition = position;
                                        filterWorkouts(spinnerPosition);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                filterWorkouts(spinnerPosition);

                            } else {
                                emptyTextView.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException | ParseException e) {
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
                    assignedWorkoutProgress.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                }

            }
        });

        requestQueue.add(jsonRequest);
    }

    private void filterWorkouts(int position)
    {
        Log.i("positionLul", Integer.toString(position));
        filteredWorkoutItems.clear();
        if(position == 0) {
            filteredWorkoutItems.addAll(workoutItems);
        } else if(position == 1) {

            final Calendar currentDate = Calendar.getInstance();
            final Calendar workoutDate = Calendar.getInstance();
            currentDate.setTime(new Date());
            for(int i = 0; i < workoutItems.size(); i ++)
            {
                workoutDate.setTime(workoutItems.get(i).getAssignedDate());
                boolean sameDay = currentDate.get(Calendar.DAY_OF_YEAR) == workoutDate.get(Calendar.DAY_OF_YEAR) &&
                        currentDate.get(Calendar.YEAR) == workoutDate.get(Calendar.YEAR);
                if(sameDay)
                {
                    Log.i("sameday", "true");
                    filteredWorkoutItems.add(workoutItems.get(i));
                }
            }
        } else if(position == 2){
            for(int i = 0; i < workoutItems.size(); i ++)
            {
                DateTime currentDate = new DateTime(new Date());
                DateTime workoutDate = new DateTime(workoutItems.get(i).getAssignedDate());

                int daysDifference = Days.daysBetween(workoutDate, currentDate).getDays();
                if(daysDifference <= 7)
                {
                    filteredWorkoutItems.add(workoutItems.get(i));
                }
                Log.i("days", Integer.toString(daysDifference));
            }
        } else if(position == 3) {
            for(int i = 0; i < workoutItems.size(); i ++)
            {
                DateTime currentDate = new DateTime(new Date());
                DateTime workoutDate = new DateTime(workoutItems.get(i).getAssignedDate());

                int daysDifference = Days.daysBetween(workoutDate, currentDate).getDays();
                if(daysDifference <= 30)
                {
                    filteredWorkoutItems.add(workoutItems.get(i));
                }
                Log.i("days", Integer.toString(daysDifference));
            }
        }
        AWAdapter.filterList(filteredWorkoutItems);
    }

}
