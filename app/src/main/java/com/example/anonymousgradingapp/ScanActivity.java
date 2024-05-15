package com.example.anonymousgradingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Exams;
import com.amplifyframework.datastore.generated.model.Grade;
import com.amplifyframework.datastore.generated.model.Roster;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

/* Used documentation from GeeksForGeeks and ZXing GitHub to implement barcode scanning API */

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanButton, examsButton, gradeButton;
    private TextView barcodeId;
    private EditText grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scanButton = findViewById(R.id.scanButton);
        barcodeId = findViewById(R.id.barcodeIdtextView);
        gradeButton = findViewById(R.id.gradeButton);
        examsButton = findViewById(R.id.examsButton);
        grade = findViewById(R.id.gradeEditText);

        // allow buttons to be accessible
        scanButton.setOnClickListener(this);
        examsButton.setOnClickListener(this);
        gradeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.scanButton) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this); // initialize scanner
            intentIntegrator.setPrompt("Scan a barcode or QR Code");    // bottom text of scanner
            intentIntegrator.setOrientationLocked(true);    // lock orientation to horizontal (default)
            intentIntegrator.initiateScan();                // start scanning
        }
        else if (v.getId() == R.id.examsButton) {
            Intent i = new Intent(getApplicationContext(), ExamActivity.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.gradeButton) {
            // add grade to database
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else {
                barcodeId.setText(intentResult.getContents());
                grade.setVisibility(View.VISIBLE);
                gradeButton.setVisibility(View.VISIBLE);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void addGrade(int barcodeID, int score){
        List<Roster> roster = MainActivity.course.getRoster();
        for (Roster r : roster) {
            List<Exams> exams = r.getExams();
            for (Exams e : exams) {
                if(e.getBarcode() == barcodeID){
                    Grade grade = Grade.builder()
                            .examGrade(score)
                            .exams(e)
                            .build();
                    Amplify.API.mutate(ModelMutation.create(grade),
                            response -> Log.i("GraphQL", "Grade with id: " + response.getData().getId()),
                            error -> Log.e("GraphQL", "Grade Create failed", error)
                    );
                }
            }
        }
    }
}