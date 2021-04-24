package com.example.mygym;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

public class personalTrainerFragment extends Fragment
{
    private RecyclerView recyclerView;
    private personalTrainerAdapter PTAdapter;
    private ArrayList<personalTrainerItem> personalTrainerItems;
    private ProgressBar trainersProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.personal_trainer_fragment, container, false);


        trainersProgressBar = rootView.findViewById(R.id.trainersProgressBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.personalTrainersPreview);
        recyclerView.setHasFixedSize(true);
        if(getActivity() != null)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        personalTrainerItems = new ArrayList<>();
        PTAdapter = new personalTrainerAdapter(personalTrainerItems, getContext());
        recyclerView.setAdapter(PTAdapter);


        String url = "https://gym-senior-2020.000webhostapp.com/showTrainersAction.php";
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        // Request a json response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {
                        try {
                            for (int i = 0;i < ja.length();i++) {
                                JSONObject trainer = ja.getJSONObject(i);
                                int ID = Integer.parseInt(trainer.getString("ID"));
                                String Name = trainer.getString("Name");
                                String Description = trainer.getString("Description");
                                String Email = trainer.getString("Email");
                                String Phone = trainer.getString("Phone");
                                String Title = trainer.getString("Title");
                                String ProfileImage = trainer.getString("ProfileImage");
                                String BannerImage = trainer.getString("BannerImage");
                                String ProfileImageURL = ProfileImage;
                                String BannerImageURL = BannerImage;

                                personalTrainerItem trainerItem = new personalTrainerItem(ID, Name, Description,Email,
                                        Phone, Title, BannerImageURL, ProfileImageURL);
                                personalTrainerItems.add(trainerItem);
                            }

                            PTAdapter.notifyDataSetChanged();
                            trainersProgressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof TimeoutError)
                    Toast.makeText(getActivity(), "Time Out Error", Toast.LENGTH_SHORT).show();
                else if(error instanceof NoConnectionError)
                    Toast.makeText(getActivity(), "No Connection Error", Toast.LENGTH_SHORT).show();
                else if(error instanceof ServerError)
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                else if(error instanceof ParseError)
                    Toast.makeText(getActivity(), "Parsing Error", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);



        return rootView;


    }
}
