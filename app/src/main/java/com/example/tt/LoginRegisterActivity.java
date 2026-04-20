package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginRegisterActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_register);

        btnLogin = findViewById(R.id.lr_btn_login);
        btnRegister = findViewById(R.id.lr_btn_register);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegisterActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}