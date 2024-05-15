package com.example.anonymousgradingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.datastore.generated.model.Course;
import com.amplifyframework.datastore.generated.model.Roster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        String courseName = getIntent().getStringExtra("courseName");   // retrieve selected course name from intent extras

        loadRoster(courseName); // load roster data for selected course
    }

    private void loadRoster(String courseName) {
        List<Roster> students = MainActivity.course.getRoster();
//        List<String> studentNames = getListFromSharedPreferences(courseName + "_student_names");
//        List<String> studentIds = getListFromSharedPreferences(courseName + "_student_ids");
        List<String> studentNames = new ArrayList<>();
        List<String> studentIds = new ArrayList<>();
        for (Roster r : students){
            studentNames.add(r.getStudentName());
            studentIds.add(r.getStudentId());
        }

        if (studentNames != null && studentIds != null && studentNames.size() == studentIds.size()) {
            TableLayout tableLayout = findViewById(R.id.tableLayout);

            for (int i = 0; i < studentNames.size(); i++) {
                TableRow tableRow = new TableRow(this);

                TextView nameTextView = new TextView(this);
                nameTextView.setText(studentNames.get(i));
                nameTextView.setPadding(16,8,16,8);
                tableRow.addView(nameTextView);

                TextView idTextView = new TextView(this);
                idTextView.setText(studentIds.get(i));
                idTextView.setPadding(16,8,16,8);
                tableRow.addView(idTextView);

                tableLayout.addView(tableRow);
            }
        }
        else {
            Toast.makeText(this, "No student data found for this class", Toast.LENGTH_SHORT).show();
        }
    }

//    private List<String> getListFromSharedPreferences(String key) {
//        SharedPreferences sharedPreferences = getSharedPreferences("RosterPref", MODE_PRIVATE);
//        Set<String> set = sharedPreferences.getStringSet(key, null);
//
//        if (set != null) {
//            return new ArrayList<>(set);
//        }
//        return null;
//    }
}