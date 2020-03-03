package com.def.arfurniture.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.def.arfurniture.R;
import com.def.arfurniture.models.Users;
import com.def.arfurniture.register.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN:::";
    TextView txtRegister;
    Button btnLogin;
    EditText email, password;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtRegister = findViewById(R.id.txt_register);
        btnLogin = findViewById(R.id.loginButton);
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordId);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validateLogin() {
        final DatabaseReference ref = mDatabase.child("user").child("0");
        final Users[] user = {new Users()};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String dbEmail = (String) dataSnapshot.child("email").getValue();
                String dbPass = (String) dataSnapshot.child("password").getValue();
                Log.d(TAG, "onDataChange: " + dbEmail);
                Log.d(TAG, "onDataChange: " + dbPass);
                if (email.getText().toString().equals(dbEmail) && password.getText().toString().equals(dbPass)) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
