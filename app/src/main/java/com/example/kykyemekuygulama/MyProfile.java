package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyProfile extends AppCompatActivity {

    EditText updateName ;
    EditText updateSurname;
    EditText updateTC;
    EditText updateMail;
    EditText updatePhone;
    EditText updateAddress;
    EditText updatePassword;

    Button updateProfilBtn;
    private Database v1;
    String userTC = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        v1 = new Database(this);
        updateName = findViewById(R.id.txtUpdateName);
        updateSurname = findViewById(R.id.txtUpdateSurname);
        updateTC = findViewById(R.id.txtUpdateTC);
        updatePhone = findViewById(R.id.txtUpdatePhone);
        updateAddress = findViewById(R.id.txtUpdateAddress);
        updatePassword = findViewById(R.id.txtUpdatePassword);
        updateProfilBtn = findViewById(R.id.btnUpdateProfil);
        updateMail = findViewById(R.id.txtUpdateMail);



        Intent intent2 = getIntent();
        userTC = intent2.getStringExtra("userTCLogged");
        getSelectedAdminInformations(userTC);

        updateProfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateName.getText().toString();
                String surname = updateSurname.getText().toString();
                String tc = updateTC.getText().toString();
                String phone = updatePhone.getText().toString();
                String adress = updateAddress.getText().toString();
                String password = updatePassword.getText().toString();
                String mail = updateMail.getText().toString();
                updateLoggedUser(name,surname,tc,mail,phone,adress,password,userTC);
                Toast.makeText(getApplicationContext(),"Başarıyla güncellendi",Toast.LENGTH_LONG).show();
            }
        });



    }
    private void updateLoggedUser(String name,String surname,String newTC,String mail
                                     ,String phone,String adres,String password,String selectedTC){

        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues cvGuncelle = new ContentValues();
        cvGuncelle.put("studentName",name);
        cvGuncelle.put("studentSurname",surname);
        cvGuncelle.put("studentTC",newTC);
        cvGuncelle.put("studentMail",mail);
        cvGuncelle.put("studentPhoneNumber",phone);
        cvGuncelle.put("studentAddress",adres);
        cvGuncelle.put("studentPassword",password);
        try{
            db.update("students",cvGuncelle,"studentTC"+"=?",new String[]{selectedTC});//ada göre güncelle diyorum
        }catch(Exception ex){
            ex.printStackTrace();
        }

        db.close();
    }
    private void getSelectedAdminInformations(String TC){
        SQLiteDatabase db = v1.getWritableDatabase();
        Cursor okunanlar = null;
        String add = "";
        String soyadd = "";
        String tc= "";
        String telefon = "";
        String adres = "";
        String sifre = "";
        String mail = "";
        try{
            okunanlar = db.rawQuery("select * from students where studentTC = ?",new String[]{TC});

            while(okunanlar.moveToNext()){
                add = okunanlar.getString(okunanlar.getColumnIndexOrThrow("studentName"));
                soyadd = okunanlar.getString(okunanlar.getColumnIndexOrThrow("studentSurname"));
                tc = okunanlar.getString(okunanlar.getColumnIndexOrThrow("studentTC"));
                mail = okunanlar.getString(okunanlar.getColumnIndexOrThrow("studentMail"));
                telefon = okunanlar.getString(okunanlar.getColumnIndexOrThrow("studentPhoneNumber"));
                adres = okunanlar.getString(okunanlar.getColumnIndexOrThrow("studentAddress"));
                sifre = okunanlar.getString(okunanlar.getColumnIndexOrThrow("studentPassword"));

            }
            updateName.setText(add);
            updateSurname.setText(soyadd);
            updateTC.setText(tc);
            updatePhone.setText(telefon);
            updateAddress.setText(adres);
            updatePassword.setText(sifre);
            updateMail.setText(mail);


        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}