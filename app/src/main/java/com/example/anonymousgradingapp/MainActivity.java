package com.example.anonymousgradingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_NAME = "MyPrefs";
    public static final String COURSES_KEY = "courses";

    public List<String> coursesList;
    private ArrayAdapter<String> adapter;
    private Button buttonExams, barcodeMap, addCourse;
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

        spinnerCourses = (Spinner) findViewById(R.id.courseSpinner);
        spinnerCourses.setAdapter(adapter);

        courseName = (EditText) findViewById(R.id.courseNameEditText);
        addCourse = (Button) findViewById(R.id.addCourseButton);
        barcodeMap = (Button) findViewById(R.id.barcodeMapButton);
        buttonExams = findViewById(R.id.buttonExams);

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

        /*
        private void loadSpinnerData() {
            DatabaseManager db = new DatabaseManager(getApplicationContext());

        }
         */
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
}