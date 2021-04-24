package com.example.mygym;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
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

public class customizeWorkoutFragment extends Fragment
{
    private RecyclerView recyclerView;
    private currentMoveAdapter CMAdapter;
    private ArrayList<moveItem> moveItems;
    private ProgressBar customMovesProgressBar;
    private EditText moveFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.moves_display_fragment, container, false);

        customMovesProgressBar = rootView.findViewById(R.id.customMovesProgressBar);
        recyclerView = rootView.findViewById(R.id.customMovesPreview);

        moveFilter = rootView.findViewById(R.id.searchET);
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

        recyclerView.setHasFixedSize(true);
        if(getActivity() != null)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        moveItems = new ArrayList<>();

        String url = "https://gym-senior-2020.000webhostapp.com/returnMovesAction.php";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // Request a json response from the provided URL.
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {

                        try {
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
                            }

                            CMAdapter = new currentMoveAdapter(moveItems, getContext());
                            recyclerView.setAdapter(CMAdapter);
                            customMovesProgressBar.setVisibility(View.GONE);
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
                    else if (error instanceof ParseError)
                        Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });


        requestQueue.add(jsonRequest);

        return rootView;

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
        CMAdapter.filterList(filtered);
    }
}



