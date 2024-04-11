package com.example.anonymousgradingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase database;
    public DatabaseManager(Context context) {this.context = context;}
    public DatabaseManager open() throws SQLException{
        databaseHelper = new DatabaseHelper(this.context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    public void insertCourse(String course){
        ContentValues temp = new ContentValues();
        temp.put(DatabaseHelper.COURSE_ID, course);
        database.insert(DatabaseHelper.TABLENAMEcourses, null, temp);
    }
    public void insertExam(String course, String exam){
        ContentValues temp = new ContentValues();
        temp.put(DatabaseHelper.COURSE_ID, course);
        temp.put(DatabaseHelper.EXAM_ID, exam);
        database.insert(DatabaseHelper.TABLENAMEexams, null, temp);
    }
    public Cursor fetchCourse(){
        String[] columns = {DatabaseHelper.COURSE_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLENAMEcourses, columns, null,
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchExam(){
        String[] columns = {DatabaseHelper.COURSE_ID, DatabaseHelper.EXAM_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLENAMEexams, columns, null,
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchRoster(String courseName){
        String[] columns = {DatabaseHelper.STUDENT_ID, DatabaseHelper.NAME};
        Cursor cursor = database.query(courseName, columns, null,
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
}
