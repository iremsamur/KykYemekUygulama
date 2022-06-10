package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FoodManagement extends AppCompatActivity {

    private Database v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management);
        //kaydol();
        Button button = findViewById(R.id.getir);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kaydol();
                Spinner spinner2 = (Spinner)findViewById(R.id.spinnerdorm);
                List <Dorm> dorms = getAllLabels();
                ArrayAdapter<Dorm> dataAdapter = new ArrayAdapter<Dorm>(getApplicationContext(),android.R.layout.simple_spinner_item, dorms);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(dataAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        long databaseID = adapterView.getSelectedItemId();
                        Toast.makeText(getApplicationContext(),"ID : "+databaseID,Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });

    }

    public List <Dorm> getAllLabels(){
        List<Dorm> dorm = new ArrayList<Dorm>();
        // Select All Query
        String selectQuery = "SELECT  * FROM dorms";// tAnsco is your table name?

        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ( cursor.moveToFirst () ) {
            do {
                //dorm.add ( new Dorm ( cursor.getString(0) , cursor.getString(1) ) );
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning labels
        return dorm;
    }
    private void kaydol(){
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();

        veriler.put("dormName","ankara");
        veriler.put("dormName","izmir");


        db.insertOrThrow("dorms",null,veriler);//veritabanına ekleme yapıyor



    }
}