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

    private List<String> coursesList;
    private ArrayAdapter<String> adapter;
    private Button buttonExams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coursesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coursesList);

        Spinner spinnerCourses = findViewById(R.id.courseSpinner);
        spinnerCourses.setAdapter(adapter);

        EditText editTextCourseName = findViewById(R.id.addCourseEditText);
        Button buttonAddCourse = findViewById(R.id.addCourseButton);
        buttonExams = findViewById(R.id.buttonExams);

        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = editTextCourseName.getText().toString();
                if (!courseName.isEmpty()) {
                    coursesList.add(courseName);
                    adapter.notifyDataSetChanged();
                    editTextCourseName.setText("");
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
    }
}