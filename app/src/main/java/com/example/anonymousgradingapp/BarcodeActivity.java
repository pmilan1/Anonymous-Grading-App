package com.example.anonymousgradingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BarcodeActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    private Button buttonPrint, buttonExams, buttonScan;
    private Spinner spinnerExams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        buttonPrint = (Button) findViewById(R.id.buttonPrint);
        spinnerExams = (Spinner) findViewById(R.id.spinnerExams);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonExams = (Button) findViewById(R.id.examsButton);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(i);
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