package com.example.kykyemekuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserMainPageScreen extends AppCompatActivity {


    private Database v1;
    TextView welcome;
    String selectedCity="";
    String selectedDorm="";
    Button goOn;
    String userTC = "";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_home:
                Toast.makeText(this,"Home",Toast.LENGTH_LONG);
                break;
            case R.id.menu_item_exit:
                Intent intent = new Intent(UserMainPageScreen.this,MainActivity.class);



                startActivity(intent);
                break;
            case R.id.menu_item_account:
                Toast.makeText(this,"Account",Toast.LENGTH_LONG);
                Intent intent2 = getIntent();
                userTC = intent2.getStringExtra("TCValue");
                Intent intent3 = new Intent(UserMainPageScreen.this,MyProfile.class);

                intent3.putExtra("userTCLogged",userTC);


                startActivity(intent3);
                break;
            case R.id.menu_item_settings:
                Toast.makeText(this,"Settings",Toast.LENGTH_LONG);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page_screen);
        v1 = new Database(this);
        welcome = findViewById(R.id.txtWelcome);
        goOn = findViewById(R.id.btnGoOn);

        Intent intent = getIntent();//getIntent metodu diğer aktiviteden gönderilen
        //veriyi karşılamak için kullanılır
        String nameSurname  = intent.getStringExtra("NameSurname");
        userTC = intent.getStringExtra("TCValue");
        welcome.setText("Hoşgeldiniz "+nameSurname);

        List<String> cities = getAllCities();
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);

        Spinner spinner = (Spinner)findViewById(R.id.spinnercities);
        spinner.setAdapter(adapter);
        //seçilen ögeyi alma
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Bir öğe seçildi. kullanarak seçilen öğeyi geri alabilirsiniz.
                // getSelectedItem()

                //Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                selectedCity = adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Bir seçenek kaldırılırsa ne yapmalı
                // veya başka bir şey
            }
        });
        List<String> dorms = getAllDormsByCity();
        ArrayAdapter<String> adapter2 =  new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, dorms);

        Spinner spinner2 = (Spinner)findViewById(R.id.spinnerdorms);
        spinner2.setAdapter(adapter2);
        //seçilen ögeyi alma
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Bir öğe seçildi. kullanarak seçilen öğeyi geri alabilirsiniz.
                // getSelectedItem()

                //Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                selectedDorm = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Bir seçenek kaldırılırsa ne yapmalı
                // veya başka bir şey
            }
        });
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedCity!=null && selectedDorm!=null){
                    Intent intent = new Intent(UserMainPageScreen.this,UserMealMenuScreen.class);

                    intent.putExtra("SelectedCity",selectedCity);
                    intent.putExtra("SelectedDorm",selectedDorm);
                    intent.putExtra("userTCValue",userTC);
                    startActivity(intent);
                }

            }
        });











    }
    private void addCities(String cityName){
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();
        veriler.put("cityName",cityName);//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir

        db.insertOrThrow("cities",null,veriler);//veritabanına ekleme yapıyor

    }
    private void addDorms(String dormName){
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();

        veriler.put("dormName",dormName);
        try{
            db.insertOrThrow("dorms",null,veriler);//veritabanına ekleme yapıyor

        }catch(Exception ex){
            ex.printStackTrace();
        }



    }
    private List<String> getAllCities(){
        //addCities("Ankara");
        //addCities("İstanbul");
        //addCities("İzmir");

        SQLiteDatabase db = v1.getReadableDatabase();
        List<String> list = new ArrayList<String>();

        // selection argument
        Cursor cursor = db.rawQuery("select * from cities",null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
    private List<String> getAllDormsByCity(){
        //addDorms("Bahçelievler Yurdu");
        //addDorms("Çankaya Yurdu");
        //addDorms("Beşiktaş Yurdu");
        //addDorms("Konak Yurdu");



        SQLiteDatabase db = v1.getReadableDatabase();
        List<String> list = new ArrayList<String>();

        // selection argument
        Cursor cursor = db.rawQuery("select * from dorms",null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
    private Integer getSelectedCity(String cityName){
        SQLiteDatabase db = v1.getWritableDatabase();
        Integer cityID = 0;
        Cursor cursor = db.rawQuery("select * from cities where cityName = "+cityName,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                cityID = cursor.getInt(cursor.getColumnIndexOrThrow("cityID"));



            }
            return cityID;
        }
        else{
            return 0;
        }



        // selection argument



    }

}