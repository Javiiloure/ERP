package com.example.erp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Timer t = new Timer();
        Intent intent = new Intent(SplashScreen.this, Login.class);
        ImageView fondo = findViewById(R.id.fondo);

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },3000);

        fondo.setOnClickListener(view -> {
            t.cancel();
            startActivity(intent);
            finish();
        });
    }
}