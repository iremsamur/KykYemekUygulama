package com.example.kykyemekuygulama;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String VERİTABANİ_ADİ="kykappdatabase";
    private static final int  SURUM=1;
    public Database(Context c){
        super(c,VERİTABANİ_ADİ,null,SURUM);

    }
    @Override
    public void onCreate(SQLiteDatabase db1) {

        db1.execSQL("CREATE TABLE students (studentID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,studentName TEXT NOT NULL,studentSurname Text NOT NULL,studentTC TEXT NOT NULL,studentMail Text NOT NULL,studentPhoneNumber Text NOT NULL,studentAddress Text NOT NULL,studentPassword Text NOT NULL)");
        db1.execSQL("CREATE TABLE admins (adminID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,adminName TEXT NOT NULL,adminSurname TEXT NOT NULL,adminTC TEXT NOT NULL,adminPhone TEXT NOT NULL,adminPassword Text NOT NULL)");
        //db1.execSQL("CREATE TABLE dorms (dormID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,dormName TEXT NOT NULL,addressID INTEGER NOT NULL, FOREIGN KEY(addressID) REFERENCES addresses(addressID))");

        db1.execSQL("CREATE TABLE dorms (dormID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,dormName TEXT NOT NULL)");
        //db1.execSQL("CREATE TABLE addresses (addressID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,addressDetail TEXT NOT NULL,cityID INTEGER NOT NULL, FOREIGN KEY(cityID) REFERENCES cities(cityID))");
        db1.execSQL("CREATE TABLE cities (cityID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,cityName TEXT NOT NULL)");
        db1.execSQL("CREATE TABLE meals (mealID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,mealName TEXT NOT NULL,mealAmount INTEGER NOT NULL,mealPrice INTEGER NOT NULL, mealTime TEXT NOT NULL,mealCategory TEXT NOT NULL,mealDate TEXT NOT NULL,mealIngredient TEXT NOT NULL)");

        

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS students ");
        db.execSQL("DROP TABLE IF EXISTS admins");
        db.execSQL("DROP TABLE IF EXISTS dorms");
        db.execSQL("DROP TABLE IF EXISTS addresses");
        db.execSQL("DROP TABLE IF EXISTS cities");
        db.execSQL("DROP TABLE IF EXISTS meals");

        onCreate(db);
    }
}
