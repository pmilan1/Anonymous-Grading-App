package com.example.anonymousgradingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        SharedPreferences sharedPreferences = getSharedPreferences("RosterPref", MODE_PRIVATE);
        String csvContent = sharedPreferences.getString("csv_content", "");

        List<String> rows = Arrays.asList(csvContent.split("\n"));

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (String row : rows) {
            String[] columns = row.split(",");

            if (columns.length != 2) {
                continue;
            }

            TableRow tableRow = new TableRow(this); // create new row

            for (String column : columns) {
                TextView textView = new TextView(this);
                textView.setText(column);
                textView.setPadding(16, 8, 16, 8);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
    }
}