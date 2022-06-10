package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminScreen extends AppCompatActivity {

    Button btnGiris ;
    EditText adminTC;
    EditText adminPass;
    String adminTCValue="";
    String adminPasswordValue = "";
    private Database v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        v1 = new Database(this);
        btnGiris = (Button)findViewById(R.id.btnAdminLogin);


        adminTC = (EditText) findViewById(R.id.txtAdminTCNo);
        adminPass = (EditText)findViewById(R.id.txtAdminPassword);
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kaydol();
                adminTCValue = adminTC.getText().toString();
                adminPasswordValue = adminPass.getText().toString();

                if(TextUtils.isEmpty(adminTCValue) ){
                    Toast.makeText(getApplicationContext(),"TC Alanı boş olamaz!!",Toast.LENGTH_SHORT).show();


                }
                else if(TextUtils.isEmpty(adminPasswordValue)){
                    Toast.makeText(getApplicationContext(),"Şifre Alanı boş olamaz!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    //giriş
                    boolean check = checkUser(adminTCValue,adminPasswordValue);
                    if(check==true){
                        Intent intent = new Intent(AdminScreen.this,AdminMenuScreen.class);


                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Kullanıcı adı veya parolanız hatalı!!",Toast.LENGTH_SHORT).show();
                        adminTC.setText("");
                        adminPass.setText("");
                    }
                }
            }
        });
    }
    public boolean checkUser(String TC,String password) {


        SQLiteDatabase db = v1.getWritableDatabase();

        // selection argument
        Cursor cursor = db.rawQuery("select * from admins where adminTC = ? and adminPassword = ?",new String[]{TC,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }
    private void kaydol(){
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();
        veriler.put("adminName","irem");//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir
        veriler.put("adminSurname","samur");
        veriler.put("adminTC","12345678901");
        veriler.put("adminPhone","05341234567");

        veriler.put("adminPassword","admin");

        db.insertOrThrow("admins",null,veriler);//veritabanına ekleme yapıyor



    }
}