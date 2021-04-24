package com.example.mygym;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class feedbackFragment extends Fragment implements View.OnClickListener
{
    private EditText feedbackSubject, feedbackContent;
    private Button sendReport;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private String currentUid;
    private RelativeLayout callUsLayout;
    private TextView callUsPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feedback_fragment, container, false);

        feedbackSubject = rootView.findViewById(R.id.feedbackSubjectET);
        feedbackContent= rootView.findViewById(R.id.feedbackContentET);
        sendReport = rootView.findViewById(R.id.sendFeedback);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUid = currentUser.getUid();

        sendReport.setOnClickListener(this);

        callUsPhone = rootView.findViewById(R.id.callUsPhone);
        callUsLayout = rootView.findViewById(R.id.callUsLayout);
        callUsLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Uri phoneNum = Uri.parse("tel:"+callUsPhone.getText().toString());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNum);

                if(callIntent.resolveActivity(getContext().getPackageManager()) != null)
                    getContext().startActivity(callIntent);
                else Toast.makeText(getContext(), "This action can not be performed.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;    }

    @Override
    public void onClick(View v)
    {
        if(v == sendReport)
        {
            String subject = feedbackSubject.getText().toString();
            String content = feedbackContent.getText().toString();

            if(subject.trim().length() < 4)
            {
                final Snackbar snackBar = Snackbar.make(getView(), "Subject is too short.", Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call your action method here
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
                return;
            }

            if(content.trim().length() < 10)
            {
                final Snackbar snackBar = Snackbar.make(getView(), "Message is too short.", Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call your action method here
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
                return;
            }

            final JSONArray jsonSelect = new JSONArray();
            jsonSelect.put(subject);
            jsonSelect.put(content);
            jsonSelect.put(currentUid);
            jsonSelect.put(currentUser.getEmail());
            String url = "https://gym-senior-2020.000webhostapp.com/Mobile%20Actions/feedbackAction.php";
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response)
                        {
                            if(response.contains("Sent"))
                            {
                                final Snackbar snackBar = Snackbar.make(getView(), "Message sent...", Snackbar.LENGTH_INDEFINITE);
                                snackBar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Call your action method here
                                        snackBar.dismiss();
                                    }
                                });
                                snackBar.show();
                                feedbackSubject.getText().clear();
                                feedbackContent.getText().clear();
                            }

                            Log.i("message response", response);
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
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("jsonMessage", jsonSelect.toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
