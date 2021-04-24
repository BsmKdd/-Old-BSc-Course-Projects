package com.example.bassamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener
{
    TextView signinTV;
    Button signupBtn;
    EditText fName, lName, usernameSignup,emailSignup, passwordSignup, confirmPasswordSignup, universityET;
    Spinner universitySpinner;
    Boolean submittable = true;
    private ArrayList<universityItem> mUniversityList;
    private universityAdapter mAdapter;

    universityItem selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        signinTV = findViewById(R.id.TVSignup);
        signinTV.setOnClickListener(this);

        signupBtn = findViewById(R.id.signupButton);
        signupBtn.setOnClickListener(this);

        fName = findViewById(R.id.fNameSignUpET);
        lName = findViewById(R.id.lNameSignUpET);
        usernameSignup = findViewById(R.id.usernameSignUpET);
        emailSignup = findViewById(R.id.emailSignUpET);
        passwordSignup = findViewById(R.id.passwordSignupET);
        universityET = findViewById(R.id.universityET);
        confirmPasswordSignup = findViewById(R.id.confirmPasswordSignupET);

        initList();

        universitySpinner = findViewById(R.id.universitySpinner);

        mAdapter = new universityAdapter(this, mUniversityList);
        universitySpinner.setAdapter(mAdapter);


        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                selectedItem = (universityItem)universitySpinner.getSelectedItem();
                if(position == 0)
                {
                    TextView spinnerTV = findViewById(R.id.spinnerTV);
                    spinnerTV.setTextColor(Color.parseColor("#666666"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initList()
    {
        mUniversityList = new ArrayList<>();
        universityItem university1 = new universityItem("Select Univerity:", "");
        universityItem university2 = new universityItem("American University of Beirut", "AUB");
        universityItem university3 = new universityItem("Lebanese International University", "LIU");
        universityItem university4 = new universityItem("Lebanese American University", "LAU");
        universityItem university5 = new universityItem("American University of Science and Technology", "AUST");

        mUniversityList.add(university1);
        mUniversityList.add(university2);
        mUniversityList.add(university3);
        mUniversityList.add(university4);
        mUniversityList.add(university5);
    }

    @Override
    public void onClick(View v)
    {
        if(v == signinTV)
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        if(v == signupBtn)
        {
            submittable = true;
            final String first = fName.getText().toString();
            final String last = lName.getText().toString();
            final String username = usernameSignup.getText().toString();
            final String email = emailSignup.getText().toString();
            final String university = selectedItem.getmUniversityValue();
            final String password = passwordSignup.getText().toString();
            String confirmPassword = confirmPasswordSignup.getText().toString();
            final Intent intent = new Intent(this, MainActivity.class);
            final String Key = "BassamMobile";

            if(first.isEmpty())
            {
                fName.setError("This field is required");
                submittable = false;
            }

            if(last.isEmpty())
            {
                lName.setError("This field is required");
                submittable = false;
            }

            if(username.isEmpty())
            {
                usernameSignup.setError("This field is required");
                submittable = false;
            }

            if(email.isEmpty())
            {
                emailSignup.setError("This field is required");
                submittable = false;
            }

            if(university.isEmpty())
            {
                universityET.setError("Please select a university.");
                submittable = false;
            } else universityET.setError(null);


            if(password.isEmpty())
            {
                passwordSignup.setError("This field is required");
                submittable = false;
            }


            if(confirmPassword.isEmpty())
            {
                confirmPasswordSignup.setError("This field is required");
                submittable = false;
            }

            if(!password.equals(confirmPassword))
            {
                confirmPasswordSignup.setError("Passwords do not match.");
                submittable = false;
            }

            if(!isEmailValid(email))
            {
                emailSignup.setError("Not a valid email address.");
                submittable = false;
            }

            if(!isPasswordValid(password))
            {
                passwordSignup.setError("6-20 characters with at least 1 digit, lower and UPPER case letters.");
                submittable = false;
            }

            final RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);

            String url = "https://bassampreviouses.000webhostapp.com/signupEmailAction.php";
            String url1 = "https://bassampreviouses.000webhostapp.com/signupAction.php";

            final StringRequest signupRequest = new StringRequest(Request.Method.POST,url1,
                    new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response)
                        {
                            Toast.makeText(SignupActivity.this, response.trim(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                        Toast.makeText(SignupActivity.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof NoConnectionError)
                        Toast.makeText(SignupActivity.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof ServerError)
                        Toast.makeText(SignupActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof ParseError)
                        Toast.makeText(SignupActivity.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("username", username);
                    params.put("fName", first);
                    params.put("lName", last);
                    params.put("university", university);
                    params.put("password", password);
                    params.put("key",Key );
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("User-Agent","Android");
                    return headers;
                }
            };

            StringRequest emailCheckRequest = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response)
                        {
                            if(!response.contains("Success"))
                            {
                                emailSignup.setError("Email is taken.");
                                submittable = false;
                                Toast.makeText(SignupActivity.this, "Email is taken.", Toast.LENGTH_SHORT).show();
                            }
                            if(submittable)
                            {
                                startActivity(intent);
                                queue.add(signupRequest);

                            }
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                        Toast.makeText(SignupActivity.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof NoConnectionError)
                        Toast.makeText(SignupActivity.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof ServerError)
                        Toast.makeText(SignupActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof ParseError)
                        Toast.makeText(SignupActivity.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("key",Key );
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("User-Agent","Android");
                    return headers;
                }
            };
            queue.add(emailCheckRequest);
        }
    }

    public static boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password)
    {
       String expression = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,20})";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
