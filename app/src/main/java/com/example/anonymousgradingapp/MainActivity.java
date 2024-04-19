package com.example.anonymousgradingapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileDescriptor;
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

    public List<String> coursesList, rosterList;
    private ArrayAdapter<String> adapter;
    private Button buttonExams, barcodeMap, addCourse, buttonUpload;
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
        barcodeMap = (Button) findViewById(R.id.barcodeMapButton);
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

        barcodeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BarcodeMap.class);
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
        intent.setType("text/csv"); //MIME type for csv file
        String[] mimeTypes = {"text/csv", "text/comma-separated-values", "application/csv"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, PICK_CSV_FILE);
    }


    //Get file location from address provided in intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CSV_FILE && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if(data != null){
                uri = data.getData(); //Gets file location from intent
                if(uri != null){
                    getCSVFromUri(uri); //Calls function to read the csv
                }
            }
        }
    }
    //Reads CSV
    private void getCSVFromUri(Uri uri){
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if(inputStream != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                line = reader.readLine(); //Skips the header line
                while ((line = reader.readLine()) != null){
                    rosterList.add(line);
                }
                inputStream.close();
                Toast.makeText(MainActivity.this, "CSV file read successful!", Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "CSV file read error", Toast.LENGTH_LONG).show();
        }
    }
}