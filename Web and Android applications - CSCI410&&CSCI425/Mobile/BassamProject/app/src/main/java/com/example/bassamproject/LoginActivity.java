package com.example.bassamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView signupTV;
    EditText emailLogin, passwordLogin;
    Button Login;
    public String key = "BassamMobile";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupTV = findViewById(R.id.signupTV);
        signupTV.setOnClickListener(this);

        Login = findViewById(R.id.loginButton);
        Login.setOnClickListener(this);

        emailLogin = findViewById(R.id.emailLoginET);
        passwordLogin = findViewById(R.id.passwordLoginET);

    }

    @Override
    public void onClick(View v)
    {
        if(v == signupTV)
        {
            Intent intent = new Intent(this,SignupActivity.class);
            startActivity(intent);
        }

        if(v == Login)
        {
            final String email = emailLogin.getText().toString().trim();
            final String password = passwordLogin.getText().toString().trim();
            final Intent intentMain = new Intent(this,MainActivity.class);


            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

            String url = "https://bassampreviouses.000webhostapp.com/loginAction.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response)
                    {
                        if(response.equals("user"))
                        {
                            startActivity(intentMain);
                        } else Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
            }, new Response.ErrorListener(){
                @Override
                        public void onErrorResponse(VolleyError error)
                {
                    if(error instanceof TimeoutError)
                        Toast.makeText(LoginActivity.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof NoConnectionError)
                        Toast.makeText(LoginActivity.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof ServerError)
                        Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    else if(error instanceof ParseError)
                        Toast.makeText(LoginActivity.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("key", key);
                    return params;
                }
            };
            queue.add(stringRequest);
        };
    }

}

