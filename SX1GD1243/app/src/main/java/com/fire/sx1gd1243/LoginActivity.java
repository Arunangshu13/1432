package com.fire.sx1gd1243;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText emailField, passwordField;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //instantiation processS
        loginBtn=findViewById(R.id.login);
        emailField=findViewById(R.id.editTextEmailAddress);
        passwordField=findViewById(R.id.editTextPassword);
        fauth=FirebaseAuth.getInstance();

        //adding the listerner to login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailField.getText().toString().trim();
                String password=passwordField.getText().toString();
                if(email.equals("") || password.equals("") ) {
                    Toast.makeText(LoginActivity.this, "Empty password or email field. ", Toast.LENGTH_LONG).show();
                }
                else {
                    fauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                                    emailField.setText("");
                                    passwordField.setText("");
                                    Intent intent=new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                }
                        }
                    });
                }
            }
        });
    }
}