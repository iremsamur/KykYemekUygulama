package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMenuScreen extends AppCompatActivity {

    Button btnAdminOp;
    Button btnMenuOp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_screen);
        btnAdminOp = findViewById(R.id.btnAdminOp);
        btnMenuOp  = findViewById(R.id.btnMenuOp);
        btnAdminOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenuScreen.this,AdminOperationsScreen.class);

                startActivity(intent);
            }
        });
        btnMenuOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenuScreen.this,AdminMealMenuOperations.class);

                startActivity(intent);
            }
        });

    }
}