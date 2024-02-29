package com.example.anonymousgradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExamActivity extends AppCompatActivity {

    private Button coursesButton, buttonCreateExam, buttonScan, buttonPrint;
    private EditText examName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        coursesButton = findViewById(R.id.coursesButton);
        buttonCreateExam = findViewById(R.id.buttonCreateExam);
        buttonScan = findViewById(R.id.buttonScan);
        buttonPrint = findViewById(R.id.buttonPrint);
        examName = findViewById(R.id.editTextExamName);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ScanActivity.class);
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
    }

}