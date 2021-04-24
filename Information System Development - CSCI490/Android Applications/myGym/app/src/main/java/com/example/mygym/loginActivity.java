package com.example.mygym;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailLogin, passwordLogin;
    Button Login;
    private String Key = "gymMobile";
    TextView loginError;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireBaseDB = FirebaseFirestore.getInstance();
    private String token = "";
    FirebaseUser currentUser;
    private DocumentReference userRef;
    private ProgressBar loginProgressBar;



    public void sendToMain()
    {
        Intent mainItent = new Intent(loginActivity.this, MainActivity.class);
        startActivity(mainItent);
        finish();
    }

    @Override
    protected void onStart() {
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            mAuth.getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    boolean isActive;
                    boolean isAdmin;
                    if(getTokenResult.getClaims().get("activeMember") != null) isActive = true;
                    else isActive = false;
                    if(getTokenResult.getClaims().get("admin") != null) isAdmin = true;
                    else isAdmin = false;
                    Log.i("login Claims", isActive + " " + isAdmin);
                    if (isActive || isAdmin) {
                        loginError.setVisibility(View.INVISIBLE);
                        sendToMain();

                    } else {
                        loginError.setText("Your account is not activated.");
                        loginError.setVisibility(View.VISIBLE);
                        loginProgressBar.setVisibility(View.INVISIBLE);
                        mAuth.signOut();
                    }
                }
            });
        }

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginError = findViewById(R.id.errorLogin);

        Login = findViewById(R.id.loginButton);
        Login.setOnClickListener(this);

        emailLogin = findViewById(R.id.emailLoginET);
        passwordLogin = findViewById(R.id.passwordLoginET);
    }

    @Override
    public void onClick(View v) {
        if (v == Login) {
            final String email = emailLogin.getText().toString().trim();
            final String password = passwordLogin.getText().toString().trim();

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
            {
                loginProgressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            mAuth.getCurrentUser().getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                @Override
                                public void onSuccess(GetTokenResult getTokenResult) {
                                    boolean isActive;
                                    boolean isAdmin;
                                    if(getTokenResult.getClaims().get("activeMember") != null) isActive = true;
                                    else isActive = false;
                                    if(getTokenResult.getClaims().get("admin") != null) isAdmin = true;
                                    else isAdmin = false;

                                    if (isActive || isAdmin) {
                                        loginError.setVisibility(View.INVISIBLE);
                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                        if (!task.isSuccessful()) {
                                                            Log.w("welp", "getInstanceId failed", task.getException());
                                                            return;
                                                        }
                                                        mAuth = FirebaseAuth.getInstance();
                                                        token = FirebaseInstanceId.getInstance().getToken();
                                                        String currentUid = mAuth.getCurrentUser().getUid();

                                                        Map<String, Object> fcm = new HashMap<>();
                                                        fcm.put("fcm", token);
                                                        userRef = fireBaseDB.document("users/" + currentUid);
                                                        userRef.update("fcm", token).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                sendToMain();
                                                                loginProgressBar.setVisibility(View.INVISIBLE);
                                                            }
                                                        });
                                                    }
                                                });
                                    } else {
                                        loginError.setText("Your account is not activated.");
                                        loginError.setVisibility(View.VISIBLE);
                                        loginProgressBar.setVisibility(View.INVISIBLE);
                                        mAuth.signOut();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(loginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            loginProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        }
    }
}

