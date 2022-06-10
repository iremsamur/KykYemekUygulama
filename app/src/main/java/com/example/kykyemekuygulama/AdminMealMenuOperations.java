package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminMealMenuOperations extends AppCompatActivity {

    private String[] mealTime={"Kahvaltı","Akşam Yemeği"};
    private String[] mealCategories={"Kahvaltılık","Ara Sıcak","Tatlı"};
    Button btnNewMeal;
    Button btnMealList;

    Button btnUpdateMeal;
    Button btnDeleteMeal;
    Button btnSearchMeal;
    EditText mealName;
    EditText mealAmount;
    EditText mealPrice;
    EditText searchMealName;


    TextView mealList;
    EditText mealIngredients;
    Button listMeal;
    Button srchMeal;
    Button updateMeal;
    Button deleteMeal;

    String selectedMealTime="";
    String selectedMealCategory="";
    String mealNameValue ="";
    Integer mealAmountValue =0;
    Integer mealPriceValue =0;
    String mealTimeValue ="";
    String mealCategoryValue ="";
    String mealIngredientValue ="";
    String searchMealValue ="";


    private Database v1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_meal_menu_operations);
        v1 = new Database(this);
        btnNewMeal = (Button) findViewById(R.id.btnNewMeal);
        mealName = (EditText) findViewById(R.id.txtMealName);
        mealAmount = (EditText) findViewById(R.id.txtMealAmount);
        mealPrice = (EditText) findViewById(R.id.txtMealPrice);
        mealIngredients = (EditText) findViewById(R.id.txtMealIngredient);
        mealList = (TextView) findViewById(R.id.recordingMealList);
        searchMealName = (EditText) findViewById(R.id.txtSearchMeal);

        btnMealList = (Button) findViewById(R.id.btnListMeal);
        btnUpdateMeal= (Button) findViewById(R.id.btnUpdateMeal);
        btnDeleteMeal= (Button) findViewById(R.id.btnDeleteMeal);
        btnSearchMeal = (Button) findViewById(R.id.btnSearchMeal);




        ArrayAdapter<String> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mealTime);

        Spinner spinner = (Spinner)findViewById(R.id.spnMealTime);
        spinner.setAdapter(adapter);
        ArrayAdapter<String> adapter2 =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mealCategories);

        Spinner spinner2 = (Spinner)findViewById(R.id.spnMealCategory);
        spinner2.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Bir öğe seçildi. kullanarak seçilen öğeyi geri alabilirsiniz.
                // getSelectedItem()

                //Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                selectedMealTime= adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Bir seçenek kaldırılırsa ne yapmalı
                // veya başka bir şey
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Bir öğe seçildi. kullanarak seçilen öğeyi geri alabilirsiniz.
                // getSelectedItem()

                //Toast.makeText(getApplicationContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                selectedMealCategory= adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Bir seçenek kaldırılırsa ne yapmalı
                // veya başka bir şey
            }
        });
        btnNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mealNameValue = mealName.getText().toString();
                mealAmountValue = Integer.parseInt(mealAmount.getText().toString());
                mealPriceValue = Integer.parseInt(mealPrice.getText().toString());
                mealTimeValue = selectedMealTime;
                mealCategoryValue = selectedMealCategory;
                mealIngredientValue = mealIngredients.getText().toString();


                if(mealNameValue.equals("")==false && mealAmountValue.equals("")==false && mealPriceValue.equals("")==false && mealTimeValue.equals("")==false && mealCategoryValue.equals("")==false && mealIngredientValue.equals("")==false){
                    if(checkMealIsAvailable(mealNameValue)){
                        Toast.makeText(getApplicationContext(),"Zaten isimde kayıtlı bir yemek bulunmaktadır!!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(checkMealCountState(mealTimeValue)>5){
                            Toast.makeText(getApplicationContext(),"Aynı gün içerisinde bir öğün için en fazla 5 yemek eklenebilir!!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
                            ContentValues veriler = new ContentValues();
                            veriler.put("mealName",mealNameValue);//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir
                            veriler.put("mealPrice",mealPriceValue);
                            veriler.put("mealAmount",mealAmountValue);
                            veriler.put("mealTime",mealTimeValue);
                            veriler.put("mealCategory",mealCategoryValue);
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String data = df.format(new Date());
                            veriler.put("mealDate",data);
                            veriler.put("mealIngredient",mealIngredientValue);
                            long result = db.insertOrThrow("meals",null,veriler);//veritabanına ekleme yapıyor
                            if(result==-1){
                                Toast.makeText(getApplicationContext(),"Kayıt gerçekleştirilemedi",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Yeni yemek başarıyla eklendi.",Toast.LENGTH_SHORT).show();

                                Cursor cursor = getRecordMeals();
                                showMeals(cursor);


                            }

                        }


                    }


                }
                else{
                    Toast.makeText(getApplicationContext(),"Lütfen tüm alanları doldurunuz!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnMealList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getRecordMeals();
                showMeals(cursor);
            }
        });
        btnUpdateMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mealNamee = mealName.getText().toString();
                Integer mealAmountt = Integer.parseInt(mealAmount.getText().toString());
                Integer mealPricee = Integer.parseInt(mealPrice.getText().toString());
                String mealTimee = selectedMealTime;
                String mealCategoryy = selectedMealCategory;
                String mealIngredientt = mealIngredients.getText().toString();
                searchMealValue = searchMealName.getText().toString();

                updateSelectedAdmin(mealNamee,mealAmountt,mealPricee,mealTimee,mealCategoryy,mealIngredientt,searchMealValue);
                Toast.makeText(getApplicationContext(),"Başarıyla güncellendi",Toast.LENGTH_LONG).show();
                Cursor cursor = getRecordMeals();
                showMeals(cursor);

            }
        });
        btnSearchMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMealValue = searchMealName.getText().toString();
                if(TextUtils.isEmpty(searchMealValue)){
                    Toast.makeText(getApplicationContext(),"Aranacak yemek adı girilmelidir!!",Toast.LENGTH_LONG).show();
                }
                else{
                    getMealByName(searchMealValue);
                    getSelectedMealInformations(searchMealValue);

                }
            }
        });
        btnDeleteMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMealValue = searchMealName.getText().toString();
                if(TextUtils.isEmpty(searchMealValue)){
                    Toast.makeText(getApplicationContext(),"Silinecek yemeğin adı girilmelidir!!",Toast.LENGTH_LONG).show();
                }
                else{
                    deleteSelectedMeal(searchMealValue);
                    Toast.makeText(getApplicationContext(),"Başarıyla silindi.",Toast.LENGTH_LONG).show();
                    Cursor cursor = getRecordMeals();
                    showMeals(cursor);
                }

            }
        });
    }
    private void deleteSelectedMeal(String mealName){
        SQLiteDatabase db = v1.getReadableDatabase();
        db.delete("meals","mealName"+"=?",new String[]{mealName});
    }
    public void getMealByName(String name) {


        SQLiteDatabase db = v1.getReadableDatabase();

        // selection argument
        Cursor cursor = db.rawQuery("select * from meals where mealName = ?",new String[]{name});
        if(cursor.getCount()>0){
            showMeals(cursor);
        }
        else{
            Toast.makeText(getApplicationContext(),"Böyle bir yemek bulunmamaktadır!!",Toast.LENGTH_SHORT).show();
            mealList.setText("Yemek bulunmamaktadır!");
        }



    }
    public boolean checkMealIsAvailable(String mealName) {

        Cursor cursor = null;

        try{
            SQLiteDatabase db = v1.getReadableDatabase();

            // selection argument
            cursor = db.rawQuery("select * from meals where mealName = ?",new String[]{mealName});
            if(cursor.getCount()>0){
                return true;
            }
            else{
                return false;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }


    }
    public Integer checkMealCountState(String mealTime) {

        Cursor cursor = null;

        try{
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String data = df.format(new Date());
            SQLiteDatabase db = v1.getReadableDatabase();


            // selection argument
            cursor = db.rawQuery("select * from meals where mealDate = ? and mealTime=?",new String[]{data,mealTime});

        }catch(Exception ex){
            ex.printStackTrace();
        }


        if(cursor.getCount()>0){
            return cursor.getCount();
        }
        else{
            return 0;
        }

    }
    private String[] sutunlar={"mealName","mealAmount","mealPrice","mealTime","mealCategory","mealDate","mealIngredient"};
    private Cursor getRecordMeals(){
        SQLiteDatabase db = v1.getWritableDatabase();
        Cursor okunanlar = null;
        try{
            okunanlar = db.query("meals",sutunlar,null,null,null,null,null);
            return okunanlar;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return okunanlar;

    }
    private void showMeals(Cursor goster){
        StringBuilder builder = new StringBuilder();
        while(goster.moveToNext()){
            String yemekAdi = goster.getString(goster.getColumnIndexOrThrow("mealName"));
            String yemekMiktari = goster.getString(goster.getColumnIndexOrThrow("mealAmount"));
            String yemekZaman = goster.getString(goster.getColumnIndexOrThrow("mealTime"));
            String yemekFiyati= goster.getString(goster.getColumnIndexOrThrow("mealPrice"));
            String yemekTuru = goster.getString(goster.getColumnIndexOrThrow("mealCategory"));
            String yemekIcerik = goster.getString(goster.getColumnIndexOrThrow("mealIngredient"));
            builder.append("Ad : ").append(yemekAdi+"\n");
            builder.append("Miktar : ").append(yemekMiktari+"\n");
            builder.append("Öğün Türü : ").append(yemekZaman+"\n");
            builder.append("Fiyat : ").append(yemekFiyati+"TL\n");
            builder.append("Tür : ").append(yemekTuru+"\n");
            builder.append("İçerik : ").append(yemekIcerik+"\n");
            builder.append("--------------------").append("\n");
        }
        mealList.setText(builder);
    }
    //guncelleme işlemi
    private void updateSelectedAdmin(String mealName,Integer mealAmount,Integer mealPrice ,String mealTime,String mealCategory,String mealIngredient,String selectedMealName){

        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues cvGuncelle = new ContentValues();
        cvGuncelle.put("mealName",mealName);
        cvGuncelle.put("mealAmount",mealAmount);
        cvGuncelle.put("mealPrice",mealPrice);
        cvGuncelle.put("mealTime",mealTime);
        cvGuncelle.put("mealCategory",mealCategory);
        cvGuncelle.put("mealIngredient",mealIngredient);
        db.update("meals",cvGuncelle,"mealName"+"=?",new String[]{selectedMealName});//ada göre güncelle diyorum
        db.close();
    }
    private void getSelectedMealInformations(String selectedMealName){
        SQLiteDatabase db = v1.getWritableDatabase();
        Cursor okunanlar = null;
        String yemekAdi = "";
        String yemekMiktari = "";
        String yemekFiyati= "";
        String yemekZaman = "";
        String yemekKategori = "";
        String yemekIcerik = "";

        try{
            okunanlar = db.rawQuery("select * from meals where mealName = ?",new String[]{selectedMealName});

            while(okunanlar.moveToNext()){
                yemekAdi = okunanlar.getString(okunanlar.getColumnIndexOrThrow("mealName"));
                yemekMiktari = okunanlar.getString(okunanlar.getColumnIndexOrThrow("mealAmount"));
                yemekFiyati = okunanlar.getString(okunanlar.getColumnIndexOrThrow("mealPrice"));

                yemekIcerik = okunanlar.getString(okunanlar.getColumnIndexOrThrow("mealIngredient"));


            }
            mealName.setText(yemekAdi);
            mealAmount.setText(yemekMiktari);
            mealPrice.setText(yemekFiyati);

            mealIngredients.setText(yemekIcerik);




        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}