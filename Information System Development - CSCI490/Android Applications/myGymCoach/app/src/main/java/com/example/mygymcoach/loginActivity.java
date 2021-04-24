package com.example.mygymcoach;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText emailLogin, passwordLogin;
    TextView loginError;
    Button Login;
    private String Key = "gymMobile";
    private FirebaseAuth mAuth;

    private ProgressBar loginProgressBar;

    public void sendToMain()
    {
        Intent mainItent = new Intent(loginActivity.this, MainActivity.class);
        startActivity(mainItent);
        finish();
    }

    @Override
    protected void onStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            sendToMain();
        }

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginProgressBar = findViewById(R.id.loginProgressBar);

        Login = findViewById(R.id.loginButton);
        Login.setOnClickListener(this);

        emailLogin = findViewById(R.id.emailLoginET);
        passwordLogin = findViewById(R.id.passwordLoginET);
        loginError = findViewById(R.id.errorLogin);

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
                                    boolean isCoach;
                                    boolean isAdmin;
                                    if(getTokenResult.getClaims().get("coach") != null) isCoach = true;
                                    else isCoach = false;
                                    if(getTokenResult.getClaims().get("admin") != null) isAdmin = true;
                                    else isAdmin = false;

                                    if (isCoach || isAdmin) {
                                        loginError.setVisibility(View.INVISIBLE);
                                        sendToMain();
                                    } else {
                                        loginError.setText("Only Coaches and Admins can login.");
                                        loginError.setVisibility(View.VISIBLE);
                                        mAuth.signOut();
                                    }
                                }
                            });
                            loginProgressBar.setVisibility(View.INVISIBLE);
                        } else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                loginError.setText(e.getMessage());
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                loginError.setText(e.getMessage());

                            } catch(FirebaseAuthUserCollisionException e) {
                                loginError.setText(e.getMessage());

                            } catch(Exception e) {
                                loginError.setText(e.getMessage());
                            }
                            loginError.setVisibility(View.VISIBLE);
                            loginProgressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            }




        }
    }
}

