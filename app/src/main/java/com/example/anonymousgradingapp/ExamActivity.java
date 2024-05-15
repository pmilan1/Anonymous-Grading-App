package com.example.anonymousgradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Course;
import com.amplifyframework.datastore.generated.model.Exams;
import com.amplifyframework.datastore.generated.model.Roster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ExamActivity extends AppCompatActivity {

    public static final String EXAM_KEY = "exams";

    private List<String> randomNums;
    public List<String> examsList;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> examAdapter;
    private Button coursesButton, buttonCreateExam, buttonBarcodes;
    private EditText examName;
    private Spinner courses;
    public Exams exam;
//    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        coursesButton = (Button) findViewById(R.id.coursesButton);
        buttonCreateExam = (Button) findViewById(R.id.buttonCreateExam);
        buttonBarcodes = (Button) findViewById(R.id.buttonBarcodes);
        examName = (EditText) findViewById(R.id.editTextExamName);
        courses = (Spinner) findViewById(R.id.spinnerCourses);
        randomNums = new ArrayList<>();
        examsList = new ArrayList<>();
//        sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        examAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, examsList);


        courses.setAdapter(adapter);

//        loadCoursesIntoSpinner();   // load courses into spinner

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
        courses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonCreateExam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String exam = examName.getText().toString();
                        if (!exam.isEmpty()) {
                            examsList.add(exam + " (" + parent.getItemAtPosition(position).toString() + ")");
                            examName.setText("");
                            examAdapter.notifyDataSetChanged();
                            addExam(exam);
//                            saveExamsToSharedPreferences();
                            Toast.makeText(ExamActivity.this, "Added exam: " + exam, Toast.LENGTH_LONG).show();
//                            Set<String> set = sharedPreferences.getStringSet(parent.getItemAtPosition(position).toString(), null);
//                            if(set != null){
//                                List<String> roster = new ArrayList<>(set);
//                                randomNums(roster.size());
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                Set<String> examNums = new HashSet<>(randomNums);
//                                editor.putStringSet(exam + " (" + parent.getItemAtPosition(position).toString() + ")", examNums);
//                                editor.apply();
//                            }
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private int randomNums(){
//        int counter = classSize;
        int max = 99999999;
        int min = 10000000;
        Random random = new Random();
        Integer rand = random.nextInt(max - min + 1) + min;
//        while(counter < 0){
//            Integer rand = random.nextInt(max - min + 1) + min;
//            randomNums.add(rand.toString());
//            counter--;
//        }
        return rand;
    }
//    private void saveExamsToSharedPreferences() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Set<String> existingExams = sharedPreferences.getStringSet(EXAM_KEY, new HashSet<>());
//        existingExams.addAll(examsList);
//        editor.putStringSet(EXAM_KEY, existingExams);
//        editor.apply();
//    }

    private void loadCoursesIntoSpinner() {
//        Set<String> set = sharedPreferences.getStringSet(MainActivity.COURSES_KEY, null);
        List<Course> temp = LoginActivity.instructor.getCourses();
        List<String> set = new ArrayList<>();;
        try {
            for (Course c : temp) {
                set.add(c.getCoursename());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (set != null) {
            List<String> coursesList = new ArrayList<>(set);
            adapter.addAll(coursesList);
        }
    }
    private void addExam(String examName){
        List<Roster> temp = MainActivity.course.getRoster();
        for(Roster r : temp) {
            exam = Exams.builder()
                    .examName(examName)
                    .roster(r)
                    .barcode(randomNums())
                    .build();
            Amplify.API.mutate(ModelMutation.create(exam),
                    response -> Log.i("GraphQL", "Exam with id: " + response.getData().getId()),
                    error -> Log.e("GraphQL", "Exam Create failed", error)
            );
        }

    }
}
