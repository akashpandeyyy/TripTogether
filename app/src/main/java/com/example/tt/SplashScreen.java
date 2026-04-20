package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY_MS = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        // Delay and move to LoginRegisterActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY_MS);
    }
}