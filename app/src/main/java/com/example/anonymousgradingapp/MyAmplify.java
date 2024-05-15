package com.example.anonymousgradingapp;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Course;

public class MyAmplify extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("AmplifyRegister", "Initialized Amplify");
        }
        catch (AmplifyException e) {
            e.printStackTrace();
            Log.e("AmplifyRegister", "Could not initialize Amplify", e);
        }
        getCourses();
    }
    private void getCourses() {
        Amplify.API.query(
                ModelQuery.list(Course.class),
                response -> {
                    if (response.hasData()) {
                        for (Course course : response.getData()) {
                            Log.i("getCourses", "Course Name: " + course.getCoursename());
                        }
                    } else {
                        Log.e("getCourses", "No data retrieved");
                    }
                },
                error -> Log.e("getCourses", "Query failed", error)
        );
    }
}
