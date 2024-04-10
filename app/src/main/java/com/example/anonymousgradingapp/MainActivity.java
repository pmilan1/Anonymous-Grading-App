package com.example.anonymousgradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<String> coursesList;
    private ArrayAdapter<String> adapter;
    private Button buttonExams, barcodeMap, addCourse;
    private EditText courseName;
    private Spinner spinnerCourses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coursesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coursesList);

        spinnerCourses = (Spinner) findViewById(R.id.courseSpinner);
        spinnerCourses.setAdapter(adapter);

        courseName = (EditText) findViewById(R.id.courseNameEditText);
        addCourse = (Button) findViewById(R.id.addCourseButton);
        barcodeMap = (Button) findViewById(R.id.barcodeMapButton);
        buttonExams = findViewById(R.id.buttonExams);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName_ = courseName.getText().toString();
                if (!courseName_.isEmpty()) {
                    coursesList.add(courseName_);
                    adapter.notifyDataSetChanged();
                    courseName.setText("");
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
        barcodeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BarcodeMap.class);
                startActivity(i);
            }
        });
    }
}