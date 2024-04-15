package com.example.anonymousgradingapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeGenerator extends AppCompatActivity {

    private EditText et;
    private Button bt;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_generator);

        et = findViewById(R.id.et1);
        bt = findViewById(R.id.btn1);
        img = findViewById(R.id.img1);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genBarcode();
            }
        });
    }

    private void genBarcode() {
        String inputValue = et.getText().toString().trim(); // get input value from EditText

        if (!inputValue.isEmpty()) {
            MultiFormatWriter mwriter = new MultiFormatWriter();    // encore the input value

            try {
                BitMatrix matrix = mwriter.encode(inputValue, BarcodeFormat.CODE_128, img.getWidth(), img.getHeight()); // generate barcode matrix
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(matrix); // creating bitmap to represent barcode

                // Iterate through matrix and set pixels in bitmap
                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = 0; j < img.getHeight(); j++) {
                        bitmap.setPixel(i, j, matrix.get(i, j) ? Color.BLACK : Color.WHITE);
                    }
                }
                img.setImageBitmap(bitmap); // setting bitmap as the image resource of ImageView

            } catch (Exception e) {
                Toast.makeText(this, "Exception " + e, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            et.setError("Please enter a value");    // show error message if EditText is empty
        }
    }
}