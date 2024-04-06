package com.example.anonymousgradingapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

public class ScanActivity extends AppCompatActivity {

    private Button buttonPicture, examsButton;
    private TextView barcodeTextView;
    private ImageView imageView;
    public static int code = 5;
    private BarcodeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        examsButton = (Button) findViewById(R.id.examsButton);
        buttonPicture = (Button) findViewById(R.id.buttonPicture);
        imageView = (ImageView) findViewById(R.id.imageView);
        barcodeTextView = (TextView) findViewById(R.id.barcodeTextView);

        // Create a barcode scanner instance
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                        .build();
        scanner = BarcodeScanning.getClient(options);

        examsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ExamActivity.class);
                startActivity(i);
            }
        });
        buttonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(myIntent);
            }
        });

    }
    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if(o.getResultCode() == RESULT_OK){
                                Intent data = o.getData();
                                Bitmap image = (Bitmap) data.getExtras().get("data");
                                imageView.setImageBitmap(image);

                                // process image for barcode scanning
                                processImageForBarcode(image);
                            }
                        }
                    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == code && resultCode == RESULT_OK){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);

            Toast.makeText(getApplicationContext(), "Processing Image...", Toast.LENGTH_LONG).show();

            // process image for barcode scanning
            processImageForBarcode(image);
        }
    }

    private void processImageForBarcode(Bitmap image) {
        InputImage inputImage = InputImage.fromBitmap(image, 0);
        scanner.process(inputImage)
                .addOnSuccessListener(barcodes -> {
                    // task completed successfully
                    for (Barcode barcode : barcodes) {
                        // extract barcoe information
                        String rawValue = barcode.getRawValue();
                        Log.d("BarcodeValue", rawValue);
                        Toast.makeText(getApplicationContext(), "Barcode Processed", Toast.LENGTH_LONG).show();

                        barcodeTextView.setText(rawValue);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();    // task failed with an exception
                    Toast.makeText(getApplicationContext(), "Invalid Barcode", Toast.LENGTH_LONG).show();
                });
    }
}