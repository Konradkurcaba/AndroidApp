package com.example.konrad.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME = "diets_db";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE Diets(id INTEGER PRIMARY KEY AUTOINCREMENT,tittle TEXT,summary TEXT,description TEXT,dateTimeStamp LONG,imagePath String)";
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
        values.put("imagePath", diet.getImagePath());
        long id = db.insert("Diets",null,values);
        return id;
    }
    public void deleteDiet(int idMeal)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Diets","id=?",new String[]{String.valueOf(idMeal)});
    }

    public void getDiets(List<Diet> list)
    {

        list.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Diets",new String[]{"id","tittle","summary","description","dateTimeStamp","imagePath"},null,null,null,null,null);
        if(cursor != null && cursor.moveToFirst() ) {

            do {
                Diet diet = new Diet(cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getLong(4),cursor.getString(5));
                list.add(diet);
                 diet.setId(cursor.getInt(0));

            } while (cursor.moveToNext());

        }

        Collections.sort(list,(Diet firstMeal, Diet secondMeal) -> {

            int firstMealIntValue = mealToIntValueConversion(firstMeal.getTitle());
            int secondMealIntValue = mealToIntValueConversion(secondMeal.getTitle());

            return firstMealIntValue - secondMealIntValue;



        });

    }

    private int mealToIntValueConversion(String title)
    {
        int mealIntValue = 0;

        switch(title) {
            case "Sniadanie":
                mealIntValue = 1;
                break;
            case "II Sniadanie":
                mealIntValue = 2;
                break;
            case "Obiad":
                mealIntValue = 3;
                break;
            case "Podwieczorek":
                mealIntValue = 4;
                break;
            case "Kolacja":
                mealIntValue = 5;
                break;
            default: mealIntValue = 6;

        }
        return mealIntValue;
    }
    private long dateToMilliseconds(String date)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }
}
