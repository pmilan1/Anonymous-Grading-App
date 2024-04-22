package com.example.anonymousgradingapp;

import static com.example.anonymousgradingapp.MainActivity.spinnerSelection;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/* This file is exclusively for testing purposes. DELETE once fully implemented */

public class BarcodeGenerator extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_generator);

        tableLayout = findViewById(R.id.barcodeTableLayout);

        // Retrieve class names from SharedPreferences
        List<String> classNames = getClassNamesFromSharedPreferences();

        // Generate and display barcode for each class name
        if (classNames != null && !classNames.isEmpty()) {
            for (String className : classNames) {
                TableRow tableRow = new TableRow(this);

                // Create TextView to display class name
                TextView classNameTextView = new TextView(this);
                classNameTextView.setText(className);
                classNameTextView.setPadding(16, 8, 16, 8);
                tableRow.addView(classNameTextView);

                // Generate and display barcode image
                String randomNum = Integer.toString(generateRandomNumber());
                ImageView barcodeImageView = new ImageView(this);
                Bitmap barcodeBitmap = generateBarcode(randomNum);
                barcodeImageView.setImageBitmap(barcodeBitmap);
                barcodeImageView.setPadding(6,8,6,8);
                tableRow.addView(barcodeImageView);

                tableLayout.addView(tableRow);
            }
        }
    }

    private List<String> getClassNamesFromSharedPreferences() {
        // Retrieve class names from SharedPreferences, modify as per your implementation
        SharedPreferences sharedPreferences = getSharedPreferences("RosterPref", MODE_PRIVATE);
        Set<String> classNamesSet = sharedPreferences.getStringSet("student_names_", null);
        if (classNamesSet != null) {
            return new ArrayList<>(classNamesSet);
        }
        return null;
    }

    private Bitmap generateBarcode(String data) {
        Code128Writer writer = new Code128Writer();
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, 2);

        try {
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.CODE_128, 512, 512, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(99999999 - 10000000 + 1) + 10000000;
    }
}
