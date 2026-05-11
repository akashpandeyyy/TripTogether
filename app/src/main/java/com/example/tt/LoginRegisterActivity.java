package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginRegisterActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // EDGE TO EDGE
        EdgeToEdge.enable(this);

        // STATUS BAR COLOR
        getWindow().setStatusBarColor(
                ContextCompat.getColor(this, R.color.primary)
        );

        // FULL SCREEN SMOOTH UI
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_login_register);

        // INIT
        btnLogin = findViewById(R.id.lr_btn_login);
        btnRegister = findViewById(R.id.lr_btn_register);

        // LOGIN BUTTON
        btnLogin.setOnClickListener(v -> {

            btnLogin.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction(() -> {

                        btnLogin.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100);

                        Intent intent =
                                new Intent(
                                        LoginRegisterActivity.this,
                                        LoginActivity.class
                                );

                        startActivity(intent);

                        overridePendingTransition(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out
                        );
                    });
        });

        // REGISTER BUTTON
        btnRegister.setOnClickListener(v -> {

            btnRegister.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction(() -> {

                        btnRegister.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100);

                        Intent intent =
                                new Intent(
                                        LoginRegisterActivity.this,
                                        RegisterActivity.class
                                );

                        startActivity(intent);

                        overridePendingTransition(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out
                        );
                    });
        });
    }
}