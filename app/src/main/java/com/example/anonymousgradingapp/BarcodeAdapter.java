package com.example.anonymousgradingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BarcodeAdapter extends BaseAdapter {
    private Context context;    // refers to main UI thread
    private String[] student_ids;
    private String[] barcodes;
    private LayoutInflater inflater;

    public BarcodeAdapter(Context context, String[] student_ids, String[] barcodes) {
        this.context = context;
        this.student_ids = student_ids;
        this.barcodes = barcodes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return student_ids.length;
    }

    @Override
    public Object getItem(int position) {
        return student_ids[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_barcode_list_item, null);
        TextView studentID = (TextView) convertView.findViewById(R.id.studentIdtextView);
        TextView barcodeID = (TextView) convertView.findViewById(R.id.barcodeIdtextView);
        studentID.setText(student_ids[position]);
        barcodeID.setText(barcodes[position]);

        return convertView;
    }
}
