package com.example.mygymcoach;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class assignFragment extends Fragment implements View.OnClickListener {

    EditText memberID;
    Button createWorkoutoutBtn;

    public assignFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assign, container, false);

        createWorkoutoutBtn = rootView.findViewById(R.id.createWorkoutButton);
        memberID = rootView.findViewById(R.id.assignIDET);

        createWorkoutoutBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(final View v)
    {
        if(v == createWorkoutoutBtn){
            v.setEnabled(false);
            if(!TextUtils.isEmpty(memberID.getText().toString())){
                final int id = Integer.parseInt(memberID.getText().toString());

                RequestQueue queue = Volley.newRequestQueue(getContext());

                String url = "https://gym-senior-2020.000webhostapp.com/Mobile%20Actions/returnMemberDetailsAction.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response)
                            {
                                Log.i("welp", response);
                                try
                                {

                                    JSONObject memberDetails = new JSONObject(response);

                                    if(memberDetails.has("error")) {
                                        Toast.makeText(getContext(), memberDetails.getString("error"), Toast.LENGTH_SHORT).show();
                                        v.setEnabled(true);
                                    } else {
                                        int memberID = Integer.parseInt(memberDetails.getString("ID"));
                                        final String memberFName = memberDetails.getString("First Name");
                                        final String memberLName = memberDetails.getString("Last Name");
                                        final String memberImage = memberDetails.getString("Image");
                                        final String memberToken = memberDetails.getString("token");
                                        String ImageSrc = "https://gym-senior-2020.000webhostapp.com/Member%20Images/" + memberImage;
                                        Intent movesIntent = new Intent(getActivity(), movesListDisplay.class);
                                        movesIntent.putExtra("ID", memberID);
                                        movesIntent.putExtra("First Name", memberFName);
                                        movesIntent.putExtra("Last Name", memberLName);
                                        movesIntent.putExtra("Image", ImageSrc);
                                        movesIntent.putExtra("token", memberToken);
                                        startActivity(movesIntent);
                                        v.setEnabled(true);

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    v.setEnabled(true);
                                    Toast.makeText(getContext(), "ID does not exist.", Toast.LENGTH_SHORT).show();
                                }
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
                            v.setEnabled(true);
                        }
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", Integer.toString(id));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

        }
    }


}


