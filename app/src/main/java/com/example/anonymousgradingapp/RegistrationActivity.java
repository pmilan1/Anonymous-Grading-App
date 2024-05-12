package com.example.anonymousgradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button registerButton, cancelButton, confirmButton;
    private EditText username, password, confirmPassword, confirmCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextPassword2);
        confirmCode = findViewById(R.id.editTextConfirmCode);
        confirmButton = findViewById(R.id.buttonConfirm);
        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);

        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerButton) {
            Log.i("AmplifyRegister", "Registering");
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                AuthSignUpOptions options = AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), username.getText().toString())
                        .build();
                Amplify.Auth.signUp(username.getText().toString(),
                        password.getText().toString(),
                        options,
                        result -> Log.i("AmplifyRegister", "Result " + result.toString()),
                        error -> Log.e("AmplifyRegister", "Error " + error.toString()));

                confirmCode.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        }
        else if (v.getId() == R.id.buttonConfirm) {
            Amplify.Auth.confirmSignUp(username.getText().toString(),
                    confirmCode.getText().toString(),
                    result -> Log.i("AmplifyRegister", "Result: " + result.toString()),
                    error -> Log.e("AmplifyRegister", "Error: " + error.toString()));
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.cancelButton) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }
}