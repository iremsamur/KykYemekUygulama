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

        Intent intent = getIntent();//getIntent metodu di??er aktiviteden g??nderilen
        //veriyi kar????lamak i??in kullan??l??r
        String nameSurname  = intent.getStringExtra("NameSurname");
        userTC = intent.getStringExtra("TCValue");
        welcome.setText("Ho??geldiniz "+nameSurname);

        List<String> cities = getAllCities();
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);

        Spinner spinner = (Spinner)findViewById(R.id.spinnercities);
        spinner.setAdapter(adapter);
        //se??ilen ??geyi alma
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Bir ????e se??ildi. kullanarak se??ilen ????eyi geri alabilirsiniz.
                // getSelectedItem()

                //Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                selectedCity = adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Bir se??enek kald??r??l??rsa ne yapmal??
                // veya ba??ka bir ??ey
            }
        });
        List<String> dorms = getAllDormsByCity();
        ArrayAdapter<String> adapter2 =  new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, dorms);

        Spinner spinner2 = (Spinner)findViewById(R.id.spinnerdorms);
        spinner2.setAdapter(adapter2);
        //se??ilen ??geyi alma
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Bir ????e se??ildi. kullanarak se??ilen ????eyi geri alabilirsiniz.
                // getSelectedItem()

                //Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                selectedDorm = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Bir se??enek kald??r??l??rsa ne yapmal??
                // veya ba??ka bir ??ey
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
        SQLiteDatabase db = v1.getWritableDatabase();//veritaban?? yazma i??lemi
        ContentValues veriler = new ContentValues();
        veriler.put("cityName",cityName);//contentvalues put metodu ile hangi alanlara ne eklenece??i verilir

        db.insertOrThrow("cities",null,veriler);//veritaban??na ekleme yap??yor

    }
    private void addDorms(String dormName){
        SQLiteDatabase db = v1.getWritableDatabase();//veritaban?? yazma i??lemi
        ContentValues veriler = new ContentValues();

        veriler.put("dormName",dormName);
        try{
            db.insertOrThrow("dorms",null,veriler);//veritaban??na ekleme yap??yor

        }catch(Exception ex){
            ex.printStackTrace();
        }



    }
    private List<String> getAllCities(){
        //addCities("Ankara");
        //addCities("??stanbul");
        //addCities("??zmir");

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
        //addDorms("Bah??elievler Yurdu");
        //addDorms("??ankaya Yurdu");
        //addDorms("Be??ikta?? Yurdu");
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