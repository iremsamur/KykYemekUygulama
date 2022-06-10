package com.example.kykyemekuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AppScreen extends AppCompatActivity {

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
                Intent intent = new Intent(AppScreen.this,MainActivity.class);

                startActivity(intent);
                break;
            case R.id.menu_item_account:
                Toast.makeText(this,"Account",Toast.LENGTH_LONG);
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
        setContentView(R.layout.activity_app_screen);
    }
}