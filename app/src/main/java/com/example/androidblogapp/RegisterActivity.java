package com.example.androidblogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText emailField, fullNameField, bioField, passwordField, confirmPasswordField;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView loginTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginTxtView = findViewById(R.id.loginTxtView);
        registerBtn = findViewById(R.id.registerBtn);
        emailField = findViewById(R.id.emailField);
        fullNameField = findViewById(R.id.fullNameField);
        bioField = findViewById(R.id.bioField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        loginTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "LOADING...", Toast.LENGTH_LONG).show();
                final String fullName = fullNameField.getText().toString().trim();
                final String email = emailField.getText().toString().trim();
                final String bio = bioField.getText().toString().trim();
                final String password = passwordField.getText().toString().trim();
                final String confirmPassword = confirmPasswordField.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(password)) {
                    if(!confirmPassword.equals(password)) {
                        Toast.makeText(RegisterActivity.this, "Confirm password doesn't match.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("fullName").setValue(fullName);
                            current_user_db.child("bio").setValue(bio);
                            current_user_db.child("emailField").setValue(email);
                            current_user_db.child("avatarURL").setValue("Default");
                            Toast.makeText(RegisterActivity.this, "Registeration Succesfull", Toast.LENGTH_SHORT).show();
                            Intent regIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            //TODOS: PROFILE Activity
                            regIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(regIntent);
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }
}
