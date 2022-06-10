package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AdminOperationsScreen extends AppCompatActivity {

    Button btnNewRecord;
    EditText adminName;
    EditText adminSurname;
    EditText adminTC;
    EditText adminPhone;
    EditText adminPassword;
    EditText srchAdminTCNo;
    TextView adminList;
    Button listAdmin;
    Button srchAdmin;
    Button updateAdmin;
    Button deleteAdmin;
    String adminNameValue ="";
    String adminSurnameValue ="";
    String adminTCValue ="";
    String adminPhoneValue ="";
    String adminPasswordValue ="";
    String searchAdminTC="";

    private Database v1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_operations_screen);
        v1 = new Database(this);
        btnNewRecord = (Button) findViewById(R.id.btnNewAdmin);
        adminName =(EditText) findViewById(R.id.txtAdminName2);
        adminSurname =(EditText) findViewById(R.id.txtAdminSurname2);
        adminTC = (EditText)findViewById(R.id.txtAdminTC2);
        adminPhone = (EditText)findViewById(R.id.txtAdminPhone2);
        adminPassword =(EditText) findViewById(R.id.txtAdminPassword2);
        adminList = (TextView) findViewById(R.id.recordingAdminList);
        adminList.setMovementMethod(new ScrollingMovementMethod());
        listAdmin = (Button) findViewById(R.id.btnListAdmin);
        srchAdmin = (Button) findViewById(R.id.btnSearchAdmin);
        srchAdminTCNo =  (EditText)findViewById(R.id.txtSearchAdminTCNo);
        deleteAdmin = (Button) findViewById(R.id.btnDeleteAdmin);
        updateAdmin = (Button) findViewById(R.id.btnUpdateAdmin);

        btnNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminNameValue = adminName.getText().toString();
                adminSurnameValue = adminSurname.getText().toString();
                adminTCValue = adminTC.getText().toString();
                adminPhoneValue = adminPhone.getText().toString();
                adminPasswordValue = adminPassword.getText().toString();

                if(adminNameValue.equals("")==false && adminSurnameValue.equals("")==false && adminTCValue.equals("")==false && adminPhoneValue.equals("")==false && adminPasswordValue.equals("")==false){
                    if(checkUserIsAvailable(adminTCValue)){
                        Toast.makeText(getApplicationContext(),"Zaten bu tc'de kayıtlı bir kullanıcı bulunmaktadır!!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
                        ContentValues veriler = new ContentValues();
                        veriler.put("adminName",adminNameValue);//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir
                        veriler.put("adminSurname",adminSurnameValue);
                        veriler.put("adminTC",adminTCValue);
                        veriler.put("adminPhone",adminPhoneValue);
                        veriler.put("adminPassword",adminPasswordValue);
                        long result = db.insertOrThrow("admins",null,veriler);//veritabanına ekleme yapıyor
                        if(result==-1){
                            Toast.makeText(getApplicationContext(),"Kayıt gerçekleştirilemedi",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Yeni admin başarıyla eklendi.",Toast.LENGTH_SHORT).show();
                            Cursor cursor = getRecords();
                            showAdmins(cursor);


                        }

                    }


                }
                else{
                    Toast.makeText(getApplicationContext(),"Lütfen tüm alanları doldurunuz!!",Toast.LENGTH_SHORT).show();
                }













            }
        });
        listAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = getRecords();
                showAdmins(cursor);
            }
        });
        srchAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAdminTC = srchAdminTCNo.getText().toString();
                if(TextUtils.isEmpty(searchAdminTC)){
                    Toast.makeText(getApplicationContext(),"Aranacak TC Kimlik No girilmelidir!!",Toast.LENGTH_LONG).show();
                }
                else{
                    getAdminByTC(searchAdminTC);
                    getSelectedAdminInformations(searchAdminTC);

                }

            }
        });
        deleteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAdminTC = srchAdminTCNo.getText().toString();
                if(TextUtils.isEmpty(searchAdminTC)){
                    Toast.makeText(getApplicationContext(),"Silinecek Adminin TC Kimlik No girilmelidir!!",Toast.LENGTH_LONG).show();
                }
                else{
                    deleteSelectedAdmin(searchAdminTC);
                    Toast.makeText(getApplicationContext(),"Başarıyla silindi.",Toast.LENGTH_LONG).show();
                    Cursor cursor = getRecords();
                    showAdmins(cursor);
                }
            }
        });

        updateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = adminName.getText().toString();
                String surname = adminSurname.getText().toString();
                String tc = adminTC.getText().toString();
                String phone = adminPhone.getText().toString();
                String password = adminPassword.getText().toString();
                String selectedTC = srchAdminTCNo.getText().toString();

                updateSelectedAdmin(name,surname,tc,phone,password,selectedTC);
                Toast.makeText(getApplicationContext(),"Başarıyla güncellendi",Toast.LENGTH_LONG).show();
                Cursor cursor = getRecords();
                showAdmins(cursor);
            }
        });

    }
    private String[] sutunlar={"adminName","adminSurname","adminTC","adminPhone","adminPassword"};
    private Cursor getRecords(){
        SQLiteDatabase db = v1.getWritableDatabase();
        Cursor okunanlar = null;
        try{
            okunanlar = db.query("admins",sutunlar,null,null,null,null,null);
            return okunanlar;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return okunanlar;

    }
    private void showAdmins(Cursor goster){
        StringBuilder builder = new StringBuilder();
        while(goster.moveToNext()){
            String add = goster.getString(goster.getColumnIndexOrThrow("adminName"));
            String soyadd = goster.getString(goster.getColumnIndexOrThrow("adminSurname"));
            String tc = goster.getString(goster.getColumnIndexOrThrow("adminTC"));
            String telefon = goster.getString(goster.getColumnIndexOrThrow("adminPhone"));
            String passwordd = goster.getString(goster.getColumnIndexOrThrow("adminPassword"));
            builder.append("ad : ").append(add+"\n");
            builder.append("soyad : ").append(soyadd+"\n");
            builder.append("TC : ").append(tc+"\n");
            builder.append("Telefon : ").append(telefon+"\n");
            builder.append("Şifre : ").append(passwordd+"\n");
            builder.append("--------------------").append("\n");
        }
        adminList.setText(builder);
    }
    public void getAdminByTC(String TC) {


        SQLiteDatabase db = v1.getReadableDatabase();

        // selection argument
        Cursor cursor = db.rawQuery("select * from admins where adminTC = ?",new String[]{TC});
        if(cursor.getCount()>0){
            showAdmins(cursor);
        }
        else{
            Toast.makeText(getApplicationContext(),"Böyle bir kullanıcı bulunmamaktadır!!",Toast.LENGTH_SHORT).show();
            adminList.setText("Kullanıcı bulunmamaktadır!");
        }



    }
    private void deleteSelectedAdmin(String TC){
        SQLiteDatabase db = v1.getReadableDatabase();
        db.delete("admins","adminTC"+"=?",new String[]{TC});
    }
    //guncelleme işlemi
    private void updateSelectedAdmin(String name,String surname,String newTC,String phone,String password,String selectedTC){

        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues cvGuncelle = new ContentValues();
        cvGuncelle.put("adminName",name);
        cvGuncelle.put("adminSurname",surname);
        cvGuncelle.put("adminTC",newTC);
        cvGuncelle.put("adminPhone",phone);
        cvGuncelle.put("adminPassword",password);
        db.update("admins",cvGuncelle,"adminTC"+"=?",new String[]{selectedTC});//ada göre güncelle diyorum
        db.close();
    }
    private void getSelectedAdminInformations(String TC){
        SQLiteDatabase db = v1.getWritableDatabase();
        Cursor okunanlar = null;
        String add = "";
        String soyadd = "";
        String tc= "";
        String telefon = "";
        String sifre = "";
        try{
            okunanlar = db.rawQuery("select * from admins where adminTC = ?",new String[]{TC});

            while(okunanlar.moveToNext()){
                add = okunanlar.getString(okunanlar.getColumnIndexOrThrow("adminName"));
                soyadd = okunanlar.getString(okunanlar.getColumnIndexOrThrow("adminSurname"));
                tc = okunanlar.getString(okunanlar.getColumnIndexOrThrow("adminTC"));
                telefon = okunanlar.getString(okunanlar.getColumnIndexOrThrow("adminPhone"));
                sifre = okunanlar.getString(okunanlar.getColumnIndexOrThrow("adminPassword"));

            }
            adminName.setText(add);
            adminSurname.setText(soyadd);
            adminTC.setText(tc);
            adminPhone.setText(telefon);
            adminPassword.setText(sifre);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public boolean checkUserIsAvailable(String TC) {


        SQLiteDatabase db = v1.getReadableDatabase();

        // selection argument
        Cursor cursor = db.rawQuery("select * from admins where adminTC = ?",new String[]{TC});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }


    /*
    private boolean kaydol(String adminName,String adminSurname,String adminTC,String adminPhone,String adminPassword){
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();
        veriler.put("adminName",adminName);//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir
        veriler.put("adminSurname",adminSurname);
        veriler.put("adminTC",adminTC);
        veriler.put("adminPhone",adminPhone);

        veriler.put("adminPassword",adminPassword);

        long result = db.insertOrThrow("admins",null,veriler);//veritabanına ekleme yapıyor
        if(result==-1){
            return false;
        }
        else{
            return true;
        }



    }

     */


}