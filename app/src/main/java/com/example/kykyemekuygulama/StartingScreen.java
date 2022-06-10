package com.example.kykyemekuygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StartingScreen extends AppCompatActivity {

    ImageView logo1;
   // ImageView logo2;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);
        logo1 = findViewById(R.id.imageView7);
      //  logo2 = findViewById(R.id.imageView8);
        text = findViewById(R.id.textView8);


        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        anim.reset();
        logo1.clearAnimation();
    //    logo2.clearAnimation();
        text.clearAnimation();
        logo1.startAnimation(anim);
     //   logo2.startAnimation(anim);
        text.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //animasyon bittiğinde buraya geçsin
                Intent intent = new Intent(StartingScreen.this,KykAppScreen.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
}