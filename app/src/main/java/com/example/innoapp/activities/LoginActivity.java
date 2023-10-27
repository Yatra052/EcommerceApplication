package com.example.innoapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.innoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView signupText;
    EditText email, password;
    Button loginButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signupText = findViewById(R.id.signupText);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();
    }


    public void loginButton(View view) {
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password is too short, enter minimum 6 character", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }

                else
                {
                    Toast.makeText(LoginActivity.this,"Error :" + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

//        startActivity(new Intent(LoginActivity.this, MainActivity.class));



    public void signn(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }


}