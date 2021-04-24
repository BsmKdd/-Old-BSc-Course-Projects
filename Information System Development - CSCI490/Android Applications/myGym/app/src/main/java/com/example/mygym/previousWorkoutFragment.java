package com.example.mygym;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class previousWorkoutFragment extends Fragment
{

    private RecyclerView recyclerView;
    private previousWorkoutAdapter PWAdapter;
    private ArrayList<previousWorkoutItem> previousWorkoutItems;
    private ArrayList<previousWorkoutItem> filteredPreviousWorkoutItems;
    private ProgressBar previousWorkoutProgress;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.LayoutManager layoutManager;
    private String token;
    private TextView emptyPreviousWorkoutTextView;
    private Spinner rangeSpinner;
    private int spinnerPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.previous_workout_fragment, container, false);

        previousWorkoutProgress = rootView.findViewById(R.id.previousWorkoutProgress);
        recyclerView = rootView.findViewById(R.id.previousWorkoutPreview);
        swipeRefreshLayout = rootView.findViewById(R.id.previousWorkoutSwipeRefreshLayout);
        emptyPreviousWorkoutTextView = rootView.findViewById(R.id.emptyPreviousWorkoutTextView);

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

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        if(getActivity() != null)
        {
            recyclerView.setLayoutManager(layoutManager);
        }


        // Get reference of widgets from XML layout

        previousWorkoutItems = new ArrayList<>();
        filteredPreviousWorkoutItems = new ArrayList<>();

        rangeSpinner = rootView.findViewById(R.id.previousWorkoutRangeSpinner);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        spinnerPosition = 3;

        populate();
        
        return rootView;

    }

    public void populate()
    {

        String memberToken = currentUser.getUid();

        String url = "https://gym-senior-2020.000webhostapp.com/returnPreviousWorkoutAction.php?token=" + memberToken;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {
                        previousWorkoutItems.clear();
                        swipeRefreshLayout.setRefreshing(false);
                        previousWorkoutProgress.setVisibility(View.GONE);
                        try {
                            if(ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject move = ja.getJSONObject(i);
                                    int ID = Integer.parseInt(move.getString("workoutID"));
                                    String date = move.getString("date");
                                    int moveCounter = Integer.parseInt(move.getString("moveCount"));

                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date finishDate = formatter.parse(date);

                                    previousWorkoutItem previousWorkoutItem = new previousWorkoutItem(ID, null,
                                            finishDate, moveCounter);
                                    previousWorkoutItems.add(previousWorkoutItem);
                                }
                                PWAdapter = new previousWorkoutAdapter(previousWorkoutItems, getContext());
                                recyclerView.setAdapter(PWAdapter);
                                PWAdapter.notifyDataSetChanged();
                                emptyPreviousWorkoutTextView.setVisibility(View.GONE);

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
                                emptyPreviousWorkoutTextView.setVisibility(View.VISIBLE);

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
                    previousWorkoutProgress.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                }

            }
        });

        requestQueue.add(jsonRequest);
    }

    private void filterWorkouts(int position)
    {
        Log.i("positionLul", Integer.toString(position));
        filteredPreviousWorkoutItems.clear();
        if(position == 0) {
            filteredPreviousWorkoutItems.addAll(previousWorkoutItems);
        } else if(position == 1) {

            final Calendar currentDate = Calendar.getInstance();
            final Calendar workoutDate = Calendar.getInstance();
            currentDate.setTime(new Date());
            for(int i = 0; i < previousWorkoutItems.size(); i ++)
            {
                workoutDate.setTime(previousWorkoutItems.get(i).getFinishedOn());
                boolean sameDay = currentDate.get(Calendar.DAY_OF_YEAR) == workoutDate.get(Calendar.DAY_OF_YEAR) &&
                        currentDate.get(Calendar.YEAR) == workoutDate.get(Calendar.YEAR);
                if(sameDay)
                {
                    Log.i("sameday", "true");
                    filteredPreviousWorkoutItems.add(previousWorkoutItems.get(i));
                }
            }
        } else if(position == 2){
            for(int i = 0; i < previousWorkoutItems.size(); i ++)
            {
                DateTime currentDate = new DateTime(new Date());
                DateTime workoutDate = new DateTime(previousWorkoutItems.get(i).getFinishedOn());

                int daysDifference = Days.daysBetween(workoutDate, currentDate).getDays();
                if(daysDifference <= 7)
                {
                    filteredPreviousWorkoutItems.add(previousWorkoutItems.get(i));
                }
                Log.i("days", Integer.toString(daysDifference));
            }
        } else if(position == 3) {
            for(int i = 0; i < previousWorkoutItems.size(); i ++)
            {
                DateTime currentDate = new DateTime(new Date());
                DateTime workoutDate = new DateTime(previousWorkoutItems.get(i).getFinishedOn());

                int daysDifference = Days.daysBetween(workoutDate, currentDate).getDays();
                if(daysDifference <= 30)
                {
                    filteredPreviousWorkoutItems.add(previousWorkoutItems.get(i));
                }
                Log.i("days", Integer.toString(daysDifference));
            }
        }
        PWAdapter.filterList(filteredPreviousWorkoutItems);
    }

}
