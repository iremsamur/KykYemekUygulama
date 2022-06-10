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

public class RegisterScreen extends AppCompatActivity {
    private Database v1;
    EditText name ;
    EditText surname;
    EditText TC;
    EditText mail;
    EditText phone;
    EditText address;
    EditText password;


    String studentName = "" ;
    String studentSurname  = "";
    String studentTC   = "" ;
    String studentMail  = "";
    String studentPhone  = "";
    String studentAddress  = "";
    String studentPassword  = "" ;
    String studentPasswordAgain  = "";
    EditText passwordAgain;

    Button kayitbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        kayitbtn = (Button) findViewById(R.id.btnKayit);
        v1 = new Database(this);



        kayitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = (EditText)findViewById(R.id.txtName);
                surname = (EditText)findViewById(R.id.txtSurname);
                TC = (EditText)findViewById(R.id.txtTC);
                mail = (EditText)findViewById(R.id.txtMail);
                phone = (EditText)findViewById(R.id.txtPhone);
                address = (EditText)findViewById(R.id.txtAddress);
                password = (EditText)findViewById(R.id.txtPassword);
                passwordAgain = (EditText)findViewById(R.id.txtPasswordAgain);
                studentName = name.getText().toString();
                studentSurname = surname.getText().toString();
                studentTC = TC.getText().toString();
                studentMail = mail.getText().toString();
                studentPhone = phone.getText().toString();
                studentAddress = address.getText().toString();
                studentPassword = password.getText().toString();
                studentPasswordAgain = passwordAgain.getText().toString();
                try{


                    if(TextUtils.isEmpty(studentName) ==true || TextUtils.isEmpty(studentSurname)==true  || TextUtils.isEmpty(studentTC)==true  || TextUtils.isEmpty(studentMail)==true  || TextUtils.isEmpty(studentPhone)==true  || TextUtils.isEmpty(studentAddress)==true  || TextUtils.isEmpty(studentPassword)==true  || TextUtils.isEmpty(studentPasswordAgain)==true){
                        Toast.makeText(getApplicationContext(),"Tüm alanlar doldurulmalıdır!!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(studentPassword.equals(studentPasswordAgain)==true){
                            boolean checkUserAvailable = checkUserIsAvailable(studentTC);
                            boolean checkUserAvailable2 = checkUserIsAvailable2(studentMail);
                            if(checkUserAvailable==true || checkUserAvailable2==true){
                                Toast.makeText(getApplicationContext(),"Sistemde böyle bir kullanıcı bulunmaktadır!!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                boolean check = kaydol(studentName,studentSurname,studentTC,studentMail,studentPhone,studentAddress,studentPassword);


                                if(check==true){
                                    Toast.makeText(getApplicationContext(),"Kayıt başarıyla yapıldı",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterScreen.this,MainActivity.class);

                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Kayıt gerçekleştirilemedi",Toast.LENGTH_LONG).show();
                                }

                            }


                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Şifre ve tekrar şifre alanları uyumlu olmalıdır!!",Toast.LENGTH_LONG).show();


                        }
                    }



                }
                catch (Exception ex){

                    ex.printStackTrace();
                }
                finally{
                    v1.close();
                }



            }
        });

    }

    private boolean kaydol(String studentNameValue,String studentSurnameValue,String studentTCValue,String studentMailValue,String studentPhoneNumberValue,String studentAddressValue,String studentPasswordValue){
        SQLiteDatabase db = v1.getWritableDatabase();//veritabanı yazma işlemi
        ContentValues veriler = new ContentValues();
        veriler.put("studentName",studentNameValue);//contentvalues put metodu ile hangi alanlara ne ekleneceği verilir
        veriler.put("studentSurname",studentSurnameValue);
        veriler.put("studentTC",studentTCValue);
        veriler.put("studentMail",studentMailValue);

        veriler.put("studentPhoneNumber",studentPhoneNumberValue);
        veriler.put("studentAddress",studentAddressValue);
        veriler.put("studentPassword",studentPasswordValue);
        long result = db.insertOrThrow("students",null,veriler);//veritabanına ekleme yapıyor
        if(result==-1){
            return false;
        }
        else{
            return true;
        }


    }
    public boolean checkUserIsAvailable(String TC) {


        SQLiteDatabase db = v1.getReadableDatabase();

        // selection argument
        Cursor cursor = db.rawQuery("select * from students where studentTC = ?",new String[]{TC});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }
    public boolean checkUserIsAvailable2(String Email) {


        SQLiteDatabase db = v1.getWritableDatabase();

        // selection argument
        Cursor cursor = db.rawQuery("select * from students where studentTC = ?",new String[]{Email});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }

}