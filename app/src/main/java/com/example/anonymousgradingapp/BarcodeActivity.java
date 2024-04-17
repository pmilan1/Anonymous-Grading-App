package com.example.anonymousgradingapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BarcodeActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    private Button buttonPrint, buttonExams, buttonScan, generateBarcodeBtn;
    private Spinner spinnerExams;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        buttonPrint = (Button) findViewById(R.id.buttonPrint);
        spinnerExams = (Spinner) findViewById(R.id.spinnerExams);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonExams = (Button) findViewById(R.id.examsButton);
        generateBarcodeBtn = (Button) findViewById(R.id.generateBarcodeBtn);

        sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        spinnerExams.setAdapter(adapter);

        loadExamsToSpinner();

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
        generateBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BarcodeGenerator.class);
                startActivity(i);
            }
        });
    }

    private void loadExamsToSpinner() {
        Set<String> set = sharedPreferences.getStringSet(ExamActivity.EXAM_KEY, null);

        if (set != null) {
            List<String> examsList = new ArrayList<>(set);
            adapter.addAll(examsList);
        }
    }
}