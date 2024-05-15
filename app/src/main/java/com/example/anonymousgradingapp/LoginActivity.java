package com.example.anonymousgradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Instructor;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signupButton, loginButton;
    private Handler handler;
    public static Instructor instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        signupButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        handler = new Handler(Looper.getMainLooper());

        Amplify.Auth.signOut(
                signOutResult -> {
                    if (signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                        Log.i("AmplifyRegister", "Sign out successful");
                    }
                    else {
                        Log.e("AmplifyRegister", "Sign out failed");
                    }
                }
        );

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signIn(
                        username.getText().toString(),
                        password.getText().toString(),

                        result -> {
                            Log.i("AmplifyRegister", "Result: " + result.toString());

                            Amplify.Auth.fetchAuthSession(
                                    authSessionResult -> {
                                        Log.i("AmplifyRegister", "Result: " + authSessionResult.toString());

                                        handler.post(() -> {
                                            if (authSessionResult.isSignedIn()) {
                                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                if(getInstructor(username.getText().toString()) == null){
                                                    setInstructor(username.getText().toString());
                                                }
                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                                                i.putExtra("username", username.getText().toString());
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Incorrect Login", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    },
                                    authSessionError -> {
                                        Log.e("AmplifyRegister", "Error " + authSessionError.toString());
                                        handler.post(() -> Toast.makeText(getApplicationContext(), "Error: " + authSessionError.toString(), Toast.LENGTH_SHORT).show());
                                    }
                            );
                        },
                        error -> {
                            Log.e("AmplifyRegister", "Error: " + error.toString());
                            handler.post(() -> Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show());
                        });
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // opens Registration page
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
            }
        });

    }
    private String getInstructor(String username) {
        String result = null;
        try{
            result = instructor.getUsername();
        }catch(Exception ignored){

        }
        return result;
    }
    private void setInstructor(String username) {
        instructor = Instructor.builder()
                .username(username)
                .build();
        Amplify.API.mutate(
                ModelMutation.create(instructor),
                response -> Log.i("GraphQL", "Instructor with id: " + response.getData().getId()),
                error -> Log.e("GraphQL", "Instructor Create failed", error)
        );
    }
}