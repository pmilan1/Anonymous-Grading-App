package com.example.anonymousgradingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Mysqlite.db";
    static final int DATABASE_VERSION = 1;
    static final String ENTRY_ID = "ID";
    static final String STUDENT_ID = "STUDENTID";
    static final String NAME = "NAME";
    static final String COURSE_ID = "COURSE";
    static final String EXAM_ID = "EXAM";
    static final String TABLENAME = "UserTable"; //Make a special call to make a roster for each course
    static final String TABLENAMEcourses = "CoursesTable";
    static final String TABLENAMEexams = "ExamsTable";
    static final String CREATE_TABLE_QUERY = "(" + ENTRY_ID + " INTEGER PRIMARY_KEY AUTOINCREMENT, "
            + STUDENT_ID + " TEXT NOT NULL, " + NAME + " TEXT NOT NULL);";
    static final String CREATE_TABLE_Courses = "CREATE TABLE " + TABLENAMEcourses
            + "(" + ENTRY_ID + " INTEGER PRIMARY_KEY AUTOINCREMENT, "
            + COURSE_ID + " TEXT NOT NULL);";
    static final String CREATE_TABLE_Exams = "CREATE TABLE " + TABLENAMEexams
            + "(" + ENTRY_ID + " INTEGER PRIMARY_KEY AUTOINCREMENT, "
            + COURSE_ID + " TEXT NOT NULL, " + EXAM_ID + " TEXT NOT NULL);";
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Exams);
        db.execSQL(CREATE_TABLE_Courses);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAMEcourses+";");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAMEexams+";");
        onCreate(db);
    }

    public void createRoster(SQLiteDatabase db, String courseName){
        db.execSQL("CREATE TABLE "+ courseName + CREATE_TABLE_QUERY);
    }
}
