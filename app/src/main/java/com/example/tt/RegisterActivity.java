package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton btnSignUp;
    private TextView tvLogin;

    private TextInputEditText etName;
    private TextInputEditText etEmail;
    private TextInputEditText etMobile;
    private TextInputEditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_register);

        // Edge To Edge Insets

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(android.R.id.content),
                (v, insets) -> {

                    int systemBars =
                            WindowInsetsCompat.Type.systemBars();

                    v.setPadding(
                            insets.getInsets(systemBars).left,
                            insets.getInsets(systemBars).top,
                            insets.getInsets(systemBars).right,
                            insets.getInsets(systemBars).bottom
                    );

                    return insets;
                });

        initViews();

        setupAnimations();

        setupClickListeners();
    }

    private void initViews() {

        btnSignUp = findViewById(R.id.re_btn_sign_up);

        tvLogin = findViewById(R.id.re_signn_Up);

        etName = findViewById(R.id.re_name);

        etEmail = findViewById(R.id.re_mail);

        etMobile = findViewById(R.id.re_mob);

        etPassword = findViewById(R.id.re_pasword);
    }

    private void setupAnimations() {

        View registerCard = findViewById(R.id.registerCard);

        registerCard.startAnimation(
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.slide_up
                )
        );
    }

    private void setupClickListeners() {

        btnSignUp.setOnClickListener(v -> validateInputs());

        tvLogin.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);

            finish();
        });
    }

    private void validateInputs() {

        String name =
                etName.getText().toString().trim();

        String email =
                etEmail.getText().toString().trim();

        String mobile =
                etMobile.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        // Name Validation

        if (TextUtils.isEmpty(name)) {

            etName.setError("Enter full name");

            etName.requestFocus();

            return;
        }

        // Email Validation

        if (TextUtils.isEmpty(email)) {

            etEmail.setError("Enter email address");

            etEmail.requestFocus();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            etEmail.setError("Invalid email");

            etEmail.requestFocus();

            return;
        }

        // Mobile Validation

        if (TextUtils.isEmpty(mobile)) {

            etMobile.setError("Enter mobile number");

            etMobile.requestFocus();

            return;
        }

        if (mobile.length() < 10) {

            etMobile.setError("Invalid mobile number");

            etMobile.requestFocus();

            return;
        }

        // Password Validation

        if (TextUtils.isEmpty(password)) {

            etPassword.setError("Enter password");

            etPassword.requestFocus();

            return;
        }

        if (password.length() < 6) {

            etPassword.setError("Password must be at least 6 characters");

            etPassword.requestFocus();

            return;
        }

        // Loading State

        btnSignUp.setEnabled(false);

        btnSignUp.setText("Creating Account...");

        btnSignUp.postDelayed(() -> {

            Toast.makeText(
                    RegisterActivity.this,
                    "Account Created Successfully",
                    Toast.LENGTH_SHORT
            ).show();

            // Redirect To Login

            Intent intent =
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    );

            // Auto Fill Login Credentials

            intent.putExtra("mobile", mobile);

            intent.putExtra("password", password);

            startActivity(intent);

            finish();

        }, 1500);
    }
}