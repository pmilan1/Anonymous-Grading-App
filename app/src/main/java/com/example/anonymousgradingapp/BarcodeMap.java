package com.example.anonymousgradingapp;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BarcodeMap extends AppCompatActivity {

    private ListView listView_;
    private String[] student_ids = {"1", "2"};
    private String[] barcodes = {"199239", "100329"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_map);

        listView_ = (ListView) findViewById(R.id.listView);

        BarcodeAdapter adapter_ = new BarcodeAdapter(getApplicationContext(), student_ids, barcodes);
        listView_.setAdapter(adapter_);
    }
}