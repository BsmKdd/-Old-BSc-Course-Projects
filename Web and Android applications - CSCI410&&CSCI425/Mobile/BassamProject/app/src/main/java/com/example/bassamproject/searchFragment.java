package com.example.bassamproject;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class searchFragment extends Fragment implements View.OnClickListener {


    public searchFragment()
    {
        // Required empty public constructor
    }


    private RecyclerView recyclerView;
    private examListAdapter adapter;
    private ArrayList<examItem> examItems;
    private EditText searchET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);


        searchET = rootView.findViewById(R.id.searchET);
        searchET.addTextChangedListener(new TextWatcher()
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

        recyclerView = (RecyclerView)rootView.findViewById(R.id.searchView);
        recyclerView.setHasFixedSize(true);
        if(getActivity() != null)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        examItems = new ArrayList<>();

        String url = "https://bassampreviouses.000webhostapp.com/SearchPageAction.php";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // Request a json response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {
                        try {
                            for (int i = 0;i < ja.length();i++) {
                                JSONObject exam = ja.getJSONObject(i);
                                String fileDir = exam.getString("ID");
                                String university = exam.getString("University");

                                String school = exam.getString("School");
                                for (int j = 0; i < school.length(); j++){
                                    if (Character.isUpperCase(school.charAt(j))) {
                                        school = school.substring(j);
                                        break;
                                    }
                                }

                                String major = exam.getString("Major");
                                for (int k = 0; i < major.length(); k++) {
                                    if (Character.isUpperCase(major.charAt(k))) {
                                        major = major.substring(k);
                                        break;
                                    }

                                }
                                int year;
                                if(!exam.getString("Year").equalsIgnoreCase(""))
                                    year = Integer.parseInt(exam.getString("Year"));
                                else year = 0;
                                String semester = exam.getString("Semester");
                                String number = exam.getString("Time");

                                String dateStr = exam.getString("UploadDate");
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date uploadDate = formatter.parse(dateStr);

                                examItem examItem = new examItem(
                                        fileDir, university, school, major,
                                        year, semester,number, uploadDate);
                                examItems.add(examItem);
                                adapter = new examListAdapter(examItems, getContext());
                                recyclerView.setAdapter(adapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("error", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

        return rootView;
    }

    private void filter(String toString)
    {
        String filterText = toString;
        ArrayList<examItem> filtered = new ArrayList<>();
        String[] Split = filterText.split(" ");
        for(examItem item: examItems)
        {
            int match = 0;
            for(int i = 0; i < Split.length; i++)
            {
                if(item.getUniversity().toLowerCase().contains(Split[i].toLowerCase()) ||
                        item.getMajor().toLowerCase().contains(Split[i].toLowerCase()) ||
                        item.getSemester().toLowerCase().contains(Split[i].toLowerCase()) ||
                        Integer.toString(item.getYear()).toLowerCase().contains(Split[i].toLowerCase())||
                        item.getNumber().toLowerCase().contains(Split[i].toLowerCase()) ||
                        item.getUploadDate().toString().toLowerCase().contains(Split[i].toLowerCase()))

                {
                    match++;

                }
            }
            if(match == Split.length) filtered.add(item);
        }
        adapter.filterList(filtered);
    }

    @Override
    public void onClick(View v)
    {

    }


}


