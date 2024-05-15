package com.example.anonymousgradingapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Course;
import com.amplifyframework.datastore.generated.model.Instructor;
import com.amplifyframework.datastore.generated.model.Roster;

import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.core.Amplify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_NAME = "MyPrefs";
    public static final String COURSES_KEY = "courses";
    private static final int PICK_CSV_FILE = 1;
    private static final int REQUEST_CODE = 101;

    public List<String> coursesList, rosterList;
    private String spinnerSelection;
    private ArrayAdapter<String> adapter;
    private Button buttonExams, rosterBtn, addCourse, buttonUpload, signOut;
    private EditText courseName;
    private Spinner spinnerCourses;

    public static Course course;

//    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coursesList = new ArrayList<>();
        rosterList = new ArrayList<>();

        spinnerCourses = (Spinner) findViewById(R.id.courseSpinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCourses.setAdapter(adapter);

        courseName = (EditText) findViewById(R.id.courseNameEditText);
        addCourse = (Button) findViewById(R.id.addCourseButton);
        rosterBtn = (Button) findViewById(R.id.rosterBtn);
        buttonExams = (Button) findViewById(R.id.buttonExams);
        buttonUpload = (Button) findViewById(R.id.uploadButton);
        signOut = (Button) findViewById(R.id.signOutButton);

        //Check for storage permission
        if(!checkStoragePermissions()) {
            requestForStoragePermissions();
        }

        getCourses();

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName_ = courseName.getText().toString();
                if (!courseName_.isEmpty()) {
                    coursesList.add(courseName_);
                    adapter.notifyDataSetChanged();
                    courseName.setText("");
                    addCourse(courseName_);
                    getCourses();
                }
            }
        });

        buttonExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ExamActivity.class);
                startActivity(i);
            }
        });

        rosterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RosterActivity.class);
                i.putExtra("courseName", spinnerCourses.getSelectedItem().toString());
                startActivity(i);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // upload button
                buttonUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFile();
                        spinnerSelection = parent.getItemAtPosition(position).toString();
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Stuff for storage permission
    private static final int STORAGE_PERMISSION_CODE = 23;
    public boolean checkStoragePermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11 (R) or above
            return Environment.isExternalStorageManager();
        }else {
            return false;
        }
    }
    private void requestForStoragePermissions() {
        //Android is 11 (R) or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);
            }catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);
            }
        }
    }
    private final ActivityResultLauncher<Intent> storageActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>(){

                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                                //Android is 11 (R) or above
                                if(Environment.isExternalStorageManager()){
                                    //Manage External Storage Permissions Granted
                                    //Log.d(TAG, "onActivityResult: Manage External Storage Permissions Granted");
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Storage Permissions Denied", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                //Below android 11

                            }
                        }
                    });
    //End stuff for storage permission

    //Use built-in file explorer to find a CSV file
    private void openFile(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        String[] mimeTypes = {"text/csv", "text/comma-separated-values", "application/csv"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, REQUEST_CODE);
    }


    //Get file location from address provided in intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri csvUri = data.getData();

                if (csvUri != null) {
                    getCSVFromUri(csvUri); // read the CSV file and save contents to SharedPreferences
                }
                else {
                    Toast.makeText(this, "Unable to retrieve CSV file", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "No data recieved", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Reads CSV
    private void getCSVFromUri(Uri csvUri){
        try {
            InputStream inputStream = getContentResolver().openInputStream(csvUri);

            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] temp;
                    temp = line.split(",");
                    addRoster(temp[0], temp[1]);
                }
                inputStream.close();
            }
            else {
                Toast.makeText(this, "Unable to open CSV file", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }
    }

    private void addCourse(String courseName){
        course = Course.builder()
                .coursename(courseName)
                .instructor(LoginActivity.instructor)
                .build();
        Amplify.API.mutate(ModelMutation.create(course),
                response -> Log.i("GraphQL", "Course with id: " + response.getData().getId()),
                error -> Log.e("GraphQL", "Course Create failed", error)
        );
    }
    private void addRoster(String studentName, String studentID){
        Roster roster = Roster.builder()
                .studentName(studentName)
                .studentId(studentID)
                .course(course)
                .build();
        Amplify.API.mutate(ModelMutation.create(roster),
                response -> Log.i("GraphQL", "Roster - Student with id: " + response.getData().getId()),
                error -> Log.e("GraphQL", "Roster Create failed", error)
        );
    }

    private void getCourses() {
        Amplify.API.query(
                ModelQuery.list(Course.class),
                response -> {
                    if (response.hasData()) {
                        List<String> courseNames = new ArrayList<>();
                        for (Course course : response.getData()) {
                            courseNames.add(course.getCoursename());
                        }
                        runOnUiThread(() -> {
                            adapter.clear();
                            adapter.addAll(courseNames);
                            adapter.notifyDataSetChanged();
                        });
                    } else {
                        Log.e("getCourses", "No data retreived");
                    }
                },
                error -> Log.e("getCourses", "Query failed", error)
        );
    }
}