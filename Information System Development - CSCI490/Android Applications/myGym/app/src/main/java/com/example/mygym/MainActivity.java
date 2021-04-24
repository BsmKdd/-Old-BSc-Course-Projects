package com.example.mygym;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, orderConfirm.confirmDialogListener {

    private DrawerLayout drawer;
    private BottomNavigationView bottomDrawer;
    private NavigationView navigationView;


    private int memberID;
    private String memberEmail;
    private String memberFName;
    private String memberLName;
    private String memberImage;

    private TextView drawerEmail;
    private TextView drawerName;
    private ImageView drawerImage;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireBaseDB = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;

    @Override
    protected void onStart()
    {
        super.onStart();


        if(currentUser == null)
        {
            sendToLogin();
        } else {
            mAuth.getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    boolean isActive;
                    boolean isAdmin;
                    if(getTokenResult.getClaims().get("activeMember") != null) isActive = true;
                    else isActive = false;
                    if(getTokenResult.getClaims().get("admin") != null) isAdmin = true;
                    else isAdmin = false;
                    Log.i("Claims", isActive + " " + isAdmin);

                    if (!(isActive || isAdmin)) {
                        sendToLogin();
                        finish();
                        mAuth.signOut();
                    } else {
                        final String token = currentUser.getUid();
                        final String currentEmail =  currentUser.getEmail();

                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                        String url = "https://gym-senior-2020.000webhostapp.com/loginAction.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response)
                                    {
                                        try
                                        {
                                            JSONObject memberDetails = new JSONObject(response);
                                            memberID = Integer.parseInt(memberDetails.getString("ID"));
                                            memberEmail = memberDetails.getString("Email");
                                            memberFName = memberDetails.getString("First Name");
                                            memberLName = memberDetails.getString("Last Name");
                                            memberImage = memberDetails.getString("Image");
                                            String ImageSrc = "https://gym-senior-2020.000webhostapp.com/Member%20Images/" + memberImage;
                                            Glide.with(MainActivity.this).load(ImageSrc).placeholder(R.drawable.personplaceholder)
                                                    .into(drawerImage);

                                            drawerEmail.setText(memberEmail);
                                            drawerName.setText(memberFName + " #" + memberID);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                if(MainActivity.this != null)
                                {
                                    if (error instanceof TimeoutError)
                                        Toast.makeText(MainActivity.this, "Time Out Error", Toast.LENGTH_SHORT).show();
                                    else if (error instanceof NoConnectionError)
                                        Toast.makeText(MainActivity.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                                    else if (error instanceof ServerError)
                                        Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    else if (error instanceof ParseError)
                                        Toast.makeText(MainActivity.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError
                            {
                                Map<String, String> params = new HashMap<>();
                                params.put("email", currentEmail);
                                params.put("token", token);
                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    }
                }
            });
        }
    }

    private void sendToLogin()
    {
        Intent loginIntent = new Intent(MainActivity.this, loginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();
        bundle.putString("memberID", String.valueOf(233));
        bundle.putString("memberFName", "potato");
        orderFragment orderFrag = new orderFragment();
        orderFrag.setArguments(bundle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        navigationView = findViewById(R.id.nav_view);

        View navHeader = navigationView.getHeaderView(0);

        drawerEmail = navHeader.findViewById(R.id.drawerEmail);
        drawerName = navHeader.findViewById(R.id.drawerName);
        drawerImage = navHeader.findViewById(R.id.drawerImage);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        bottomDrawer = findViewById(R.id.bottom_navigation);

        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState == null)
        {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new workoutsFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_workouts);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_workouts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new workoutsFragment()).commit();
                break;
            case R.id.nav_scanMachine:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new scanMachineFragment()).commit();;
                break;
            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new orderFragment()).commit();
                break;
            case R.id.nav_PT:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new personalTrainerFragment()).commit();
                break;
            case R.id.nav_feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new feedbackFragment()).commit();
                break;

            case R.id.nav_logout:

                Map<String, Object> removeFcm = new HashMap<>();
                removeFcm.put("fcm", "");
                //If you want to delete the empty field, replace the "" with FieldValue.delete()

                fireBaseDB.collection("users").document(mAuth.getCurrentUser().getUid()).update(removeFcm).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent loginIntent = new Intent(MainActivity.this, loginActivity.class);
                        mAuth.signOut();
                        startActivity(loginIntent);
                        finish();
                    }
                });

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        Fragment ff = getSupportFragmentManager().findFragmentById(R.id.fragment_container_bottom);
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else if ((ff instanceof customizeWorkoutFragment))
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new workoutsFragment()).commit();
        }
        else if (count != 0)
        {
            getSupportFragmentManager().popBackStack();
        }
        else if (count == 0)
        {
            super.onBackPressed();
            //additional code
        }else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public void orderConfirmed() {

    }

    public int getMemberID() {
        return memberID;
    }

    public String getMemberFName() {
        return memberFName;
    }
}

