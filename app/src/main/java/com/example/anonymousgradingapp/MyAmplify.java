package com.example.anonymousgradingapp;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class MyAmplify extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("AmplifyRegister", "Initialized Amplify");
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("AmplifyRegister", "Could not initialize Amplify", e);
        }
    }
}
