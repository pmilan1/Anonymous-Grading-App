package com.example.anonymousgradingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/* Used documentation from GeeksForGeeks and ZXing GitHub to implement barcode scanning API */

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanButton, examsButton;
    private TextView barcodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scanButton = findViewById(R.id.scanButton);
        examsButton = findViewById(R.id.examsButton);
        barcodeId = findViewById(R.id.barcodeIdtextView);

        scanButton.setOnClickListener(this);   // add listener to button
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
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}