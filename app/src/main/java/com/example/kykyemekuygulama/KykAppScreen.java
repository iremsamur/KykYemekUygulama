package com.example.kykyemekuygulama;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class KykAppScreen extends AppCompatActivity {

    Button btnAdmin ;
    Button btnUser;
    TextView txtclock;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyk_app_screen);
        btnAdmin = (Button) findViewById(R.id.btnGoToAdmin);
        btnUser = (Button) findViewById(R.id.btnGoToUser);
        txtclock = (TextView) findViewById(R.id.txtclock) ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        txtclock.setText(dtf.format(now));
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KykAppScreen.this,AdminScreen.class);

                startActivity(intent);
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(KykAppScreen.this,MainActivity.class);

                startActivity(intent);
            }
        });
    }
}