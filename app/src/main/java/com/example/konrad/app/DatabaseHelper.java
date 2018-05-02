package com.example.konrad.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME = "diets_db";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE Diets(id INTEGER PRIMARY KEY AUTOINCREMENT,tittle TEXT,summary TEXT,description TEXT,dateTimeStamp LONG)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Diets");
        // Create tables again
        onCreate(db);
    }

    public long instertDiet(Diet diet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tittle",diet.getTitle());
        values.put("summary",diet.getSummary());
        values.put("description",diet.getDesctiption());
        values.put("dateTimeStamp",diet.getMealDate());
        long id = db.insert("Diets",null,values);
        return id;
    }

    public void getDiets(List<Diet> list)
    {
        list.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Diets",new String[]{"tittle","summary","description","dateTimeStamp"},null,null,null,null,null);
        if(cursor != null && cursor.moveToFirst() ) {

            do {
                Diet diet = new Diet(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3));
                list.add(diet);

            } while (cursor.moveToNext());

        }

    }
}
