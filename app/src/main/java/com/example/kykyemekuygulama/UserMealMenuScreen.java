package com.example.kykyemekuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMealMenuScreen extends AppCompatActivity {
    TextView txtDormName;
    Button changeDorm;
    CheckBox chkDinner;
    CheckBox chkBreakfast;
    TextView txtBreakfast;
    TextView txtDinner;
    private Database v1;


    /*
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
                Intent intent = new Intent(UserMealMenuScreen.this,MainActivity.class);

                startActivity(intent);
                break;
            case R.id.menu_item_account:
                Intent intent2 = new Intent(UserMealMenuScreen.this,MyProfile.class);
                startActivity(intent2);
                break;
            case R.id.menu_item_settings:
                Toast.makeText(this,"Settings",Toast.LENGTH_LONG);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_meal_menu_screen);
        v1 = new Database(this);
        txtDormName = findViewById(R.id.txtDormName);
        chkBreakfast = findViewById(R.id.chckBoxBreakfast);
        chkDinner = findViewById(R.id.chkBoxDinner);
        txtBreakfast = findViewById(R.id.txtBreakfast);
        txtDinner = findViewById(R.id.txtDinner);
        Intent intent = getIntent();//getIntent metodu diğer aktiviteden gönderilen
        //veriyi karşılamak için kullanılır
        String selectedDorm = intent.getStringExtra("SelectedDorm");
        txtDormName.setText(selectedDorm);

        changeDorm = findViewById(R.id.changeDorm);
        changeDorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(UserMealMenuScreen.this,UserMainPageScreen.class);


                startActivity(intent);
            }
        });
        chkBreakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkBreakfast.isChecked()){
                    //kahvaltı listeler
                    showBreakfastMeals();
                }
            }
        });
        chkDinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //akşam yemeği listeler
                showDinnerMeals();
            }
        });


    }
    private Cursor getRecordDinnerMeals(){
        SQLiteDatabase db = v1.getWritableDatabase();
        Cursor cursor = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String data = df.format(new Date());
        try{
            cursor = db.rawQuery("select * from meals where mealTime = ? and mealDate=?",new String[]{"Akşam Yemeği",data});

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return cursor;

    }
    private Cursor getRecordBreakfastMeals(){
        SQLiteDatabase db = v1.getWritableDatabase();
        Cursor cursor = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String data = df.format(new Date());
        try{
            cursor = db.rawQuery("select * from meals where mealTime = ? and mealDate=?",new String[]{"Kahvaltı",data});

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return cursor;

    }
    private void showBreakfastMeals(){
        Cursor goster = getRecordBreakfastMeals();
        StringBuilder builder = new StringBuilder();
        builder.append("Kahvaltı Menüsü \nAd\t\t\t\tMiktar\t\t\t\tÖğün Türü\t\t\t\tFiyat\t\t\t\tTür\t\t\t\t\tİçerik\n");
        while(goster.moveToNext()){
            String yemekAdi = goster.getString(goster.getColumnIndexOrThrow("mealName"));
            String yemekMiktari = goster.getString(goster.getColumnIndexOrThrow("mealAmount"));
            String yemekZaman = goster.getString(goster.getColumnIndexOrThrow("mealTime"));
            String yemekFiyati= goster.getString(goster.getColumnIndexOrThrow("mealPrice"));
            String yemekTuru = goster.getString(goster.getColumnIndexOrThrow("mealCategory"));
            String yemekIcerik = goster.getString(goster.getColumnIndexOrThrow("mealIngredient"));
            builder.append(yemekAdi+"\t\t\t\t");
            builder.append(yemekMiktari+"\t\t\t  ");
            builder.append(yemekZaman+"\t\t\t\t   ");
            builder.append(yemekFiyati+" TL\t\t\t\t");
            builder.append(yemekTuru+"\t\t\t\t ");
            builder.append(yemekIcerik+"\n");
            builder.append("-----------------------------------------------------------").append("\n");
        }
        txtBreakfast.setText(builder);
    }
    private void showDinnerMeals(){
        Cursor goster = getRecordDinnerMeals();
        StringBuilder builder = new StringBuilder();
        builder.append("Akşam Yemeği\nAd\t\t\t\tMiktar\t\t\t\tÖğün Türü\t\t\t\tFiyat\t\t\t\tTür\t\t\t\t\tİçerik\n");
        while(goster.moveToNext()){
            String yemekAdi = goster.getString(goster.getColumnIndexOrThrow("mealName"));
            String yemekMiktari = goster.getString(goster.getColumnIndexOrThrow("mealAmount"));
            String yemekZaman = goster.getString(goster.getColumnIndexOrThrow("mealTime"));
            String yemekFiyati= goster.getString(goster.getColumnIndexOrThrow("mealPrice"));
            String yemekTuru = goster.getString(goster.getColumnIndexOrThrow("mealCategory"));
            String yemekIcerik = goster.getString(goster.getColumnIndexOrThrow("mealIngredient"));
            builder.append(yemekAdi+"\t\t\t\t");
            builder.append(yemekMiktari+"\t\t\t\t  ");
            builder.append(yemekZaman+"\t\t\t\t   ");
            builder.append(yemekFiyati+" TL\t\t\t\t");
            builder.append(yemekTuru+"\t\t\t\t ");
            builder.append(yemekIcerik+"\n");
            builder.append("-----------------------------------------------------------").append("\n");
        }
        txtDinner.setText(builder);
    }
}