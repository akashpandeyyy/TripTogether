package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    private MaterialButton btnSignIn;
    private TextView tvSignUp;

    private TextInputEditText etMobile;
    private TextInputEditText etPassword;

    // Hardcoded Credentials
    private static final String DEMO_MOBILE = "987654321";
    private static final String DEMO_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);

        // Edge To Edge Padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content),
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

        btnSignIn = findViewById(R.id.btn_sign_in);
        tvSignUp = findViewById(R.id.l_sign_Up);

        etMobile = findViewById(R.id.l_mobile);
        etPassword = findViewById(R.id.l_password);
    }

    private void setupAnimations() {

        View loginCard = findViewById(R.id.loginCard);

        loginCard.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.slide_up)
        );
    }

    private void setupClickListeners() {

        btnSignIn.setOnClickListener(v -> validateLogin());

        tvSignUp.setOnClickListener(v -> {

            Intent intent =
                    new Intent(LoginActivity.this, RegisterActivity.class);

            startActivity(intent);
        });
    }

    private void validateLogin() {

        String mobile =
                etMobile.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        // Validation

        if (TextUtils.isEmpty(mobile)) {

            etMobile.setError("Enter mobile number");
            etMobile.requestFocus();
            return;
        }

        if (mobile.length() < 9) {

            etMobile.setError("Invalid mobile number");
            etMobile.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {

            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return;
        }

        // Loading State

        btnSignIn.setEnabled(false);
        btnSignIn.setText("Please wait...");

        btnSignIn.postDelayed(() -> {

            // Hardcoded Login

            if (mobile.equals(DEMO_MOBILE)
                    && password.equals(DEMO_PASSWORD)) {

                Toast.makeText(
                        LoginActivity.this,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(LoginActivity.this, MainActivity.class);

                startActivity(intent);

                finish();

            } else {

                Toast.makeText(
                        LoginActivity.this,
                        "Invalid Credentials",
                        Toast.LENGTH_SHORT
                ).show();
            }

            // Reset Button

            btnSignIn.setEnabled(true);
            btnSignIn.setText("Log In");

        }, 1200);
    }
}