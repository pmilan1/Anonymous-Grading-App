package com.example.anonymousgradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExamActivity extends AppCompatActivity {

    private List<String> coursesList;
    public List<String> examsList;
    private ArrayAdapter<String> adapter;
    private Button coursesButton, buttonCreateExam, buttonBarcodes;
    private EditText examName;
    private Spinner courses;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        coursesButton = (Button) findViewById(R.id.coursesButton);
        buttonCreateExam = (Button) findViewById(R.id.buttonCreateExam);
        buttonBarcodes = (Button) findViewById(R.id.buttonBarcodes);
        examName = (EditText) findViewById(R.id.editTextExamName);
        courses = (Spinner) findViewById(R.id.spinnerCourses);

        sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        courses.setAdapter(adapter);

        loadCoursesIntoSpinner();   // load courses into spinner

        buttonBarcodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BarcodeActivity.class);
                startActivity(i);
            }
        });
        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        buttonCreateExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exam = examName.getText().toString();
                if (!exam.isEmpty()) {
                    examsList.add(exam);
                    examName.setText("");
                }
            }
        });
    }

    private void loadCoursesIntoSpinner() {
        Set<String> set = sharedPreferences.getStringSet(MainActivity.COURSES_KEY, null);

        if (set != null) {
            List<String> coursesList = new ArrayList<>(set);
            adapter.addAll(coursesList);
        }
    }

}