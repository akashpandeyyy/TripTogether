package com.example.tt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton btnSignUp;
    private TextView tvLogin;
    private MaterialCardView backCard;

    private TextInputEditText etName, etEmail, etMobile, etPassword;
    private TextInputLayout nameLayout, emailLayout, mobileLayout, passwordLayout;

    private Handler handler;
    private boolean isProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Edge To Edge Insets
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(android.R.id.content),
                (v, insets) -> {
                    int systemBars = WindowInsetsCompat.Type.systemBars();
                    v.setPadding(
                            insets.getInsets(systemBars).left,
                            insets.getInsets(systemBars).top,
                            insets.getInsets(systemBars).right,
                            insets.getInsets(systemBars).bottom
                    );
                    return insets;
                });

        handler = new Handler(Looper.getMainLooper());
        initViews();
        setupTextWatchers();
        setupAnimations();
        setupClickListeners();
    }

    private void initViews() {
        btnSignUp = findViewById(R.id.re_btn_sign_up);
        tvLogin = findViewById(R.id.re_signn_Up);
        backCard = findViewById(R.id.backCard);

        etName = findViewById(R.id.re_name);
        etEmail = findViewById(R.id.re_mail);
        etMobile = findViewById(R.id.re_mob);
        etPassword = findViewById(R.id.re_pasword);

        nameLayout = findViewById(R.id.re_name_layout);
        emailLayout = findViewById(R.id.re_mail_layout);
        mobileLayout = findViewById(R.id.re_mob_layout);
        passwordLayout = findViewById(R.id.re_password_layout);
    }

    private void setupTextWatchers() {
        // Real-time validation for name
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nameLayout != null && nameLayout.isErrorEnabled()) {
                    nameLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Real-time validation for email
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (emailLayout != null && emailLayout.isErrorEnabled()) {
                    emailLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Real-time validation for mobile
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mobileLayout != null && mobileLayout.isErrorEnabled()) {
                    mobileLayout.setErrorEnabled(false);
                }
                // Auto move to password when mobile length reaches 10
                if (s != null && s.length() == 10) {
                    etPassword.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Real-time validation for password
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passwordLayout != null && passwordLayout.isErrorEnabled()) {
                    passwordLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupAnimations() {
        View registerCard = findViewById(R.id.registerCard);
        if (registerCard != null) {
            registerCard.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.slide_up)
            );
        }
    }

    private void setupClickListeners() {
        btnSignUp.setOnClickListener(v -> {
            if (!isProcessing) {
                validateAndRegister();
            }
        });

        tvLogin.setOnClickListener(v -> {
            animateView(tvLogin);
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        // Back button click
        if (backCard != null) {
            backCard.setOnClickListener(v -> {
                animateView(backCard);
                onBackPressed();
            });
        }
    }

    private void validateAndRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValid = true;

        // Name Validation
        if (TextUtils.isEmpty(name)) {
            nameLayout.setError("Enter full name");
            nameLayout.setErrorEnabled(true);
            etName.requestFocus();
            isValid = false;
        } else if (name.length() < 3) {
            nameLayout.setError("Name must be at least 3 characters");
            nameLayout.setErrorEnabled(true);
            etName.requestFocus();
            isValid = false;
        } else {
            nameLayout.setErrorEnabled(false);
        }

        // Email Validation
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Enter email address");
            emailLayout.setErrorEnabled(true);
            etEmail.requestFocus();
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Enter valid email address");
            emailLayout.setErrorEnabled(true);
            etEmail.requestFocus();
            isValid = false;
        } else {
            emailLayout.setErrorEnabled(false);
        }

        // Mobile Validation
        if (TextUtils.isEmpty(mobile)) {
            mobileLayout.setError("Enter mobile number");
            mobileLayout.setErrorEnabled(true);
            etMobile.requestFocus();
            isValid = false;
        } else if (mobile.length() != 10) {
            mobileLayout.setError("Enter valid 10-digit mobile number");
            mobileLayout.setErrorEnabled(true);
            etMobile.requestFocus();
            isValid = false;
        } else if (!mobile.matches("\\d+")) {
            mobileLayout.setError("Mobile number must contain only digits");
            mobileLayout.setErrorEnabled(true);
            etMobile.requestFocus();
            isValid = false;
        } else {
            mobileLayout.setErrorEnabled(false);
        }

        // Password Validation
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Enter password");
            passwordLayout.setErrorEnabled(true);
            etPassword.requestFocus();
            isValid = false;
        } else if (password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            passwordLayout.setErrorEnabled(true);
            etPassword.requestFocus();
            isValid = false;
        } else if (password.length() > 20) {
            passwordLayout.setError("Password must be less than 20 characters");
            passwordLayout.setErrorEnabled(true);
            etPassword.requestFocus();
            isValid = false;
        } else {
            passwordLayout.setErrorEnabled(false);
        }

        if (isValid) {
            performRegistration(name, email, mobile, password);
        } else {
            shakeView(findViewById(R.id.registerCard));
        }
    }

    private void performRegistration(String name, String email, String mobile, String password) {
        isProcessing = true;

        // Show loading state
        btnSignUp.setEnabled(false);
        btnSignUp.setText("Creating Account...");

        // Simulate network/database operation
        handler.postDelayed(() -> {
            // Save user data to SharedPreferences or Database
            saveUserData(name, email, mobile, password);

            Toast.makeText(
                    RegisterActivity.this,
                    "✓ Account Created Successfully!",
                    Toast.LENGTH_LONG
            ).show();

            // Navigate to Login with credentials
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("mobile", mobile);
            intent.putExtra("password", password);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // Reset button state
            btnSignUp.setEnabled(true);
            btnSignUp.setText("Create Account");
            isProcessing = false;

            finish();

        }, 1500);
    }

    private void saveUserData(String name, String email, String mobile, String password) {
        // Save to SharedPreferences
        android.content.SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_name", name);
        editor.putString("user_email", email);
        editor.putString("user_mobile", mobile);
        editor.putString("user_password", password);
        editor.putBoolean("is_registered", true);
        editor.apply();

        // You can also save to SQLite database here if needed
    }

    private void animateView(View view) {
        view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() -> {
                    view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start();
                })
                .start();
    }

    private void shakeView(View view) {
        if (view != null) {
            android.view.animation.Animation shake = android.view.animation.AnimationUtils.loadAnimation(
                    this, android.R.anim.slide_in_left);
            shake.setDuration(300);
            view.startAnimation(shake);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear any pending errors and reset button state
        if (nameLayout != null) nameLayout.setErrorEnabled(false);
        if (emailLayout != null) emailLayout.setErrorEnabled(false);
        if (mobileLayout != null) mobileLayout.setErrorEnabled(false);
        if (passwordLayout != null) passwordLayout.setErrorEnabled(false);

        if (btnSignUp != null) {
            btnSignUp.setEnabled(true);
            btnSignUp.setText("Create Account");
        }
        isProcessing = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}