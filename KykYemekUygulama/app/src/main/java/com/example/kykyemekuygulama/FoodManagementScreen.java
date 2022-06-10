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

public class FoodManagementScreen extends AppCompatActivity {

    private Database v1;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management_screen);

        button = (Button)findViewById(R.id.getir);
        v1 = new Database(this);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinneryurt);
        List<Dorm> dorms = getAllLabels();
        ArrayAdapter<Dorm> dataAdapter = new ArrayAdapter<Dorm>(this,android.R.layout.simple_spinner_item, dorms);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try{
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

        }catch(Exception ex){
            ex.printStackTrace();
        }
        //deneme 2
        Spinner spinner3 = (Spinner)findViewById(R.id.spinnertest);
        List<String> dorms2 = getAllLabels2();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dorms2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter2);
        /*
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                List<Dorm> dorms4 = getAllLabels();
                String id="";

                String databaseID = adapterView.getSelectedItem().toString();
                for(Dorm tmp:dorms4){
                    String dormName = tmp.getDormName();
                    if(dormName.equals(databaseID)){
                        id=tmp.getId().toString();


                    }

                }
                Toast.makeText(getApplicationContext(),"ID : "+databaseID,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"ID : "+id,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        */






        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kaydol();
                Spinner spinner2 = (Spinner)findViewById(R.id.spinnerdorm);
                List<Dorm> dorms = getAllLabels();
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
        */

    }
    public List <Dorm> getAllLabels(){
        //kaydol();
        List<Dorm> dorm = new ArrayList<Dorm>();
        String[] dormNames = {"Cebeci Kyk Yurdu","Güvenlik Kyk Yurdu"};



        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();
        for(int i=0;i<dormNames.length;i++){
            veriler = new ContentValues();
            veriler.put("dormName",dormNames[i]);

        }
       // veriler.put("dormName","test1");//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir


        try{
            db.insertOrThrow("dorms",null,veriler);//veritabanına ekleme yapıyor
            boolean control = true;

        }catch(Exception ex){
            ex.printStackTrace();
        }


        // Select All Query

        String selectQuery = "SELECT  * FROM dorms";// tAnsco is your table name?


        SQLiteDatabase db2 = v1.getReadableDatabase();
        Cursor cursor = db2.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        boolean deneme = cursor.moveToNext();
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
    public List <String> getAllLabels2(){
        //kaydol();
        String[] dormNames = {"Cebeci Kyk Yurdu","Güvenlik Kyk Yurdu"};
        List<String> dorm = new ArrayList<String>();
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        /*
        ContentValues veriler = new ContentValues();
        veriler.put("dormName","test1");//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir
        */
        ContentValues veriler = new ContentValues();
        for(int i=0;i<dormNames.length;i++){

            veriler.put("dormName",dormNames[i]);
            try{
                db.insertOrThrow("dorms",null,veriler);//veritabanına ekleme yapıyor
                boolean control = true;

            }catch(Exception ex){
                ex.printStackTrace();
            }


        }




        // Select All Query

        String selectQuery = "SELECT  * FROM dorms";// tAnsco is your table name?


        SQLiteDatabase db2 = v1.getReadableDatabase();
        Cursor cursor = db2.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        int result = cursor.getCount();
        if(result>0){
            while(cursor.moveToNext()){
                dorm.add(cursor.getString(cursor.getColumnIndexOrThrow("dormName")));
            }

        }
        /*
        if ( cursor.moveToFirst () ) {
            do {
                dorm.add ( cursor.getString(1) );
            } while (cursor.moveToNext());
        }
        */


        // closing connection
        cursor.close();
        db.close();

        // returning labels
        return dorm;



    }
    private void kaydol(){
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();
        veriler.put("dormName","test1");//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir


        db.insertOrThrow("dorms",null,veriler);//veritabanına ekleme yapıyor



    }
}