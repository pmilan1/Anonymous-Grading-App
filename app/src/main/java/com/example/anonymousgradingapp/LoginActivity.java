package com.example.anonymousgradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signupButton, forgotPasswordButton, loginButton, bypassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        signupButton = (Button) findViewById(R.id.registerButton);
        forgotPasswordButton = (Button) findViewById(R.id.forgotButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        bypassButton = findViewById(R.id.bypassButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signIn(username.getText().toString(),
                        password.getText().toString(),
                        result -> Log.i("AmplifyRegister", "Result: " + result.toString()),
                        error -> Log.e("AmplifyRegister", "Error: " + error.toString()));
                Amplify.Auth.fetchAuthSession(
                        result -> Log.i("AmplifyRegister", "Result: " + result.toString()),
                        error -> Log.e("AmplifyRegister", "Error: " + error.toString())
                );
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // opens Registration page
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        bypassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}