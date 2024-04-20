package com.example.anonymousgradingapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    public static String spinnerSelection;
    private ArrayAdapter<String> adapter;
    private Button buttonExams, rosterBtn, addCourse, buttonUpload;
    private EditText courseName;
    private Spinner spinnerCourses;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        coursesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coursesList);
        rosterList = new ArrayList<>();

        spinnerCourses = (Spinner) findViewById(R.id.courseSpinner);
        spinnerCourses.setAdapter(adapter);

        courseName = (EditText) findViewById(R.id.courseNameEditText);
        addCourse = (Button) findViewById(R.id.addCourseButton);
        rosterBtn = (Button) findViewById(R.id.rosterBtn);
        buttonExams = (Button) findViewById(R.id.buttonExams);
        buttonUpload = (Button) findViewById(R.id.uploadButton);

        //Check for storage permission
        if(!checkStoragePermissions()) {
            requestForStoragePermissions();
        }

        addCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                String courseName_ = courseName.getText().toString();
                if (!courseName_.isEmpty()) {
                    coursesList.add(courseName_);
                    adapter.notifyDataSetChanged();
                    courseName.setText("");
                    saveCoursesToSharedPreferences();
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
        loadCoursesFromSharedPreferences();

        rosterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RosterActivity.class);
                startActivity(i);
            }
        });

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFile();
                        spinnerSelection = parent.getItemAtPosition(position).toString();
                        saveRosterListToSharedPreferences(parent.getItemAtPosition(position).toString());
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveCoursesToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(coursesList);
        editor.putStringSet(COURSES_KEY, set);
        editor.apply();
    }

    private void loadCoursesFromSharedPreferences() {
        Set<String> set = sharedPreferences.getStringSet(COURSES_KEY, null);

        if (set != null) {
            coursesList.addAll(set);
            adapter.notifyDataSetChanged();
        }
    }
    private void saveRosterListToSharedPreferences(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(rosterList);
        editor.putStringSet(name, set);
        editor.apply();
        //Adds a new list to SharedPreferences with the course name
        rosterList.clear();
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
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                inputStream.close();

                saveToSharedPreferences(stringBuilder.toString());
            }
            else {
                Toast.makeText(this, "Unable to open CSV file", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToSharedPreferences(String csvContent) {
        SharedPreferences sharedPreferences = getSharedPreferences("RosterPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String[] lines = csvContent.split("\n");

        // Initialize lists to store student names and IDs
        List<String> studentNames = new ArrayList<>();
        List<String> studentIDs = new ArrayList<>();

        // Parse each line to extract student names and IDs
        for (String line : lines) {
            String[] parts = line.split(",");   // assuming comma-separation in CSV

            if (parts.length >= 2) {
                studentNames.add(parts[0].trim());
                studentIDs.add(parts[1].trim());
            }
        }


        // save student names and ids to SharedPreferences
        saveListToSharedPreferences("student_names_" + spinnerSelection, studentNames);
        saveListToSharedPreferences("student_ids_" + spinnerSelection, studentIDs);

        //editor.putString("csv_content", csvContent);
        //editor.apply();

        Toast.makeText(this, "CSV content saved to SharedPreferences", Toast.LENGTH_SHORT).show();
    }

    private void saveListToSharedPreferences(String key, List<String> list) {
        SharedPreferences sharedPreferences = getSharedPreferences("RosterPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> set = new HashSet<>(list);
        editor.putStringSet(key, set);
        editor.apply();
    }
}