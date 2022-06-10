package com.example.kykyemekuygulama;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button login ;
    Button register;
    Button forgotPassword;
    EditText userTC;
    EditText userPassword ;
    String userTCValue="";
    String userPasswordValue = "";
    private Database v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1 = new Database(this);
        login = (Button)findViewById(R.id.btnGiris);
        register = (Button)findViewById(R.id.btnRegister);
        forgotPassword = (Button) findViewById(R.id.btnResetPassword);
        userTC = (EditText) findViewById(R.id.userTC);
        userPassword = (EditText)findViewById(R.id.userPassword);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Şifremi Sıfırla");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setMessage("Lütfen şifrenizi sıfırlamak için sistemde kayıtlı mail adresinizi giriniz.");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Sıfırla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailTxt = input.getText().toString();
                if(emailTxt.equals("")==false){
                    String temporaryPassword=generateTemporaryPassword();
                    boolean checkMailResult = checkMail(emailTxt);
                    if(checkMailResult==true){
                        sendResetPasswordEmail(emailTxt,temporaryPassword);
                        showAlertMessage(temporaryPassword);
                        //Toast.makeText(getApplicationContext(),"Şifre başarıyla sıfırlandı. Yeni geçici şifreniz "+temporaryPassword+" ile sisteme giriş yapabilirsiniz \n Lütfen sisteme giriş yaptığınızda şifrenizi değiştirmeyi unutmayın.",Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Sistemde kayıtlı böyle bir mail adresi bulunmamaktadır!!",Toast.LENGTH_SHORT).show();

                    }


                }
                else{
                    Toast.makeText(getApplicationContext(),"Sıfırlamak için sistemde kayıtlı mail adresinizi giriniz.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTCValue = userTC.getText().toString();
                userPasswordValue = userPassword.getText().toString();

                if(TextUtils.isEmpty(userTCValue) ){
                    Toast.makeText(getApplicationContext(),"TC Alanı boş olamaz!!",Toast.LENGTH_SHORT).show();


                }
                else if(TextUtils.isEmpty(userPasswordValue)){
                    Toast.makeText(getApplicationContext(),"Şifre Alanı boş olamaz!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    //giriş
                    boolean check = checkUser(userTCValue,userPasswordValue);
                    if(check==true){
                        Intent intent = new Intent(MainActivity.this,UserMainPageScreen.class);

                        intent.putExtra("NameSurname",welcomeToAppScreen(userTCValue));
                        intent.putExtra("TCValue",userTCValue);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Kullanıcı adı veya parolanız hatalı!!",Toast.LENGTH_SHORT).show();
                        userTC.setText("");
                        userPassword.setText("");
                    }
                }





            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterScreen.class);

                startActivity(intent);

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(MainActivity.this,ForgotPassword.class);

                startActivity(intent);

                 */
                dialog.show();







            }
        });


    }
    public boolean checkUser(String TC,String password) {


        SQLiteDatabase db = v1.getWritableDatabase();

        // selection argument
       Cursor cursor = db.rawQuery("select * from students where studentTC = ? and studentPassword = ?",new String[]{TC,password});
       if(cursor.getCount()>0){
           return true;
       }
       else{
           return false;
       }

    }
    public String welcomeToAppScreen(String TC) {


        SQLiteDatabase db = v1.getWritableDatabase();
        String surname = "";
        String name = "";

        // selection argument
        Cursor cursor = db.rawQuery("select * from students where studentTC = ?",new String[]{TC});
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                name = cursor.getString(cursor.getColumnIndexOrThrow("studentName"));
               surname = cursor.getString(cursor.getColumnIndexOrThrow("studentSurname"));


            }
            return name+" "+surname;
        }
        else{
            return name+" "+surname;
        }

    }
    public void showAlertMessage(String password){
        AlertDialog.Builder builder2 =
                new AlertDialog.Builder(this);
        builder2.setTitle("Şifre Sıfırlama Talebi");
        builder2.setMessage("Şifre başarıyla sıfırlandı. Yeni geçici şifreniz "+password+" ile sisteme giriş yapabilirsiniz \n Lütfen sisteme giriş yaptığınızda şifrenizi değiştirmeyi unutmayın.");
        builder2.setCancelable(false);
        builder2.setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder2.create().show();
    }
    public boolean checkMail(String Email) {


        SQLiteDatabase db = v1.getWritableDatabase();

        // selection argument
        Cursor cursor = db.rawQuery("select * from students where studentMail = ?",new String[]{Email});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }
    public void sendResetPasswordEmail(String Email,String password){


        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues passwordChange = new ContentValues();

        passwordChange.put("studentPassword",password);
        db.update("students",passwordChange,"studentMail"+"=?",new String[]{Email});//ada göre güncelle diyorum
        db.close();

    }
    public String generateTemporaryPassword(){
        String metin = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        //  StringBuffer olutşurun
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 5; i++) {

            // metinden rastgele bir tane değer oku
            int index
                    = (int)(metin.length()
                    * Math.random());

            // sb değişkenine harfleri ekle
            sb.append(metin
                    .charAt(index));
        }

        return sb.toString();

    }


}