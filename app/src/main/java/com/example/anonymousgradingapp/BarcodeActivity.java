package com.example.anonymousgradingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BarcodeActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    private Button buttonExams, buttonScan, generateBarcodeBtn;
    private Spinner spinnerExams;
    private SharedPreferences sharedPreferences;
    private String spinnerSelection;
    private String courseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

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
                i.putExtra("courseName", courseName);
                startActivity(i);
            }
        });
        spinnerExams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelection = parent.getItemAtPosition(position).toString();

                if (spinnerSelection != null) {
                    Log.d("SPINNER", spinnerSelection);
                }
                else {
                    Log.e("SPINNER", "No selection.");
                }
                extractCourseName(spinnerSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void extractCourseName(String tiedName) {
        String patternString = "\\((.*?)\\)";   // regular expression to find content inside parenthesis
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(tiedName);

        if (matcher.find()) {
            courseName = matcher.group(1);   // extract content inside parenthesis
            Log.d("SPINNER", "Extracted Course Name: " + courseName);
        }
        else {
            Log.e("SPINNER", "No content found inside parenthesis");
        }
    }

    private void loadExamsToSpinner() {
        Set<String> set = sharedPreferences.getStringSet(ExamActivity.EXAM_KEY, null);

        if (set != null) {
            List<String> examsList = new ArrayList<>(set);
            adapter.addAll(examsList);
        }
    }
}