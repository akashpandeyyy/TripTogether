package com.example.tt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
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

public class LoginActivity extends AppCompatActivity {

    private MaterialButton btnSignIn;
    private TextView tvSignUp, tvForgot;
    private TextInputEditText etMobile, etPassword;
    private TextInputLayout mobileLayout, passwordLayout;
    private MaterialCardView backCard;
    private View loginCard;

    private SharedPreferences sharedPreferences;

    // Hardcoded Credentials
    private static final String DEMO_MOBILE = "9876543210";
    private static final String DEMO_PASSWORD = "123456";

    private Handler handler;
    private boolean isProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("TT_USER_DATA", MODE_PRIVATE);

        // Check if user is already logged in
        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            navigateToMain();
            return;
        }

        // Edge To Edge Padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content),
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
        setupClickListeners();
        setupBackButton();
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void initViews() {
        btnSignIn = findViewById(R.id.btn_sign_in);
        tvSignUp = findViewById(R.id.l_sign_Up);
        tvForgot = findViewById(R.id.txtForgot);
        etMobile = findViewById(R.id.l_mobile);
        etPassword = findViewById(R.id.l_password);
        mobileLayout = findViewById(R.id.l_mobile_layout);
        passwordLayout = findViewById(R.id.l_password_layout);
        backCard = findViewById(R.id.backCard);
        loginCard = findViewById(R.id.loginCard);

        // Load entrance animation
        if (loginCard != null) {
            Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            loginCard.startAnimation(slideUp);
        }
    }

    private void setupTextWatchers() {
        // Mobile number formatting and validation
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mobileLayout != null && mobileLayout.isErrorEnabled()) {
                    mobileLayout.setErrorEnabled(false);
                }

                // Auto-format mobile number (if length reaches 10, move focus)
                if (s != null && s.length() == 10) {
                    etPassword.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Password validation
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

    private void setupClickListeners() {
        // Login button click
        btnSignIn.setOnClickListener(v -> {
            if (!isProcessing) {
                validateAndLogin();
            }
        });

        // Sign up navigation
        tvSignUp.setOnClickListener(v -> {
            animateView(tvSignUp);
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // Forgot password
        tvForgot.setOnClickListener(v -> {
            animateView(tvForgot);
            showForgotPasswordDialog();
        });
    }

    private void setupBackButton() {
        if (backCard != null) {
            backCard.setOnClickListener(v -> {
                animateView(backCard);
                onBackPressed();
            });
        }
    }

    private void validateAndLogin() {
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValid = true;

        // Validate Mobile Number
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

        // Validate Password
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
        } else {
            passwordLayout.setErrorEnabled(false);
        }

        if (isValid) {
            performLogin(mobile, password);
        } else {
            // Shake animation for error feedback
            shakeView(loginCard);
        }
    }

    private void performLogin(String mobile, String password) {
        isProcessing = true;

        // Show loading state
        btnSignIn.setEnabled(false);
        btnSignIn.setText("Logging in...");

        // Simulate network delay
        handler.postDelayed(() -> {
            // Hardcoded Login Check
            if (mobile.equals(DEMO_MOBILE) && password.equals(DEMO_PASSWORD)) {
                // Success
                saveLoginState(mobile);
                showSuccessAndNavigate();
            } else {
                // Failure
                showLoginError();
            }

            // Reset button state
            btnSignIn.setEnabled(true);
            btnSignIn.setText("Log In");
            isProcessing = false;
        }, 1500);
    }

    private void saveLoginState(String mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", true);
        editor.putString("user_mobile", mobile);
        
        // Add default user info if not present (for demo purposes)
        if (!sharedPreferences.contains("user_name")) {
            editor.putString("user_name", "Som Pandey");
        }
        if (!sharedPreferences.contains("user_email")) {
            editor.putString("user_email", "sompandey@gmail.com");
        }
        editor.apply();
    }

    private void showSuccessAndNavigate() {
        Toast.makeText(
                LoginActivity.this,
                "✓ Login Successful!",
                Toast.LENGTH_SHORT
        ).show();

        // Add success animation
        btnSignIn.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(200)
                .withEndAction(this::navigateToMain)
                .start();
    }

    private void showLoginError() {
        Toast.makeText(
                LoginActivity.this,
                "✗ Invalid mobile number or password",
                Toast.LENGTH_LONG
        ).show();

        // Clear password field
        etPassword.setText("");
        etPassword.requestFocus();

        // Shake animation for error feedback
        shakeView(loginCard);

        // Highlight error fields
        passwordLayout.setError("Invalid password");
        passwordLayout.setErrorEnabled(true);
    }

    private void showForgotPasswordDialog() {
        // Simple dialog for forgot password
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Reset Password")
                .setMessage("Enter your registered mobile number to reset password")
                .setPositiveButton("Reset", (dialog, which) -> {
                    Toast.makeText(this,
                            "Reset link sent to your registered email",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(true)
                .show();
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
        if (mobileLayout != null) mobileLayout.setErrorEnabled(false);
        if (passwordLayout != null) passwordLayout.setErrorEnabled(false);
        if (btnSignIn != null) {
            btnSignIn.setEnabled(true);
            btnSignIn.setText("Log In");
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