package com.example.tt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class LoginRegisterActivity extends AppCompatActivity {

    private MaterialButton btnLogin, btnRegister;
    private View logoParent;
    private TextView tvHeading, tvSubtitle;
    private MaterialCardView buttonCard;
    private CardView featureCard1, featureCard2, featureCard3;
    private TextView developerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edge To Edge
        EdgeToEdge.enable(this);

        // Status Bar Color with Gradient Effect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(this, R.color.primary)
            );

            // Make status bar icons dark or light based on theme
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        setContentView(R.layout.activity_login_register);

        // Initialize Views
        initViews();

        // Setup Window Insets
        setupWindowInsets();

        // Start Entrance Animations
        startEntranceAnimations();

        // Setup Click Listeners
        setupClickListeners();

        // Setup Developer Credit Click
        setupDeveloperCredit();
    }

    private void initViews() {
        btnLogin = findViewById(R.id.lr_btn_login);
        btnRegister = findViewById(R.id.lr_btn_register);
        logoParent = findViewById(R.id.logoParent);
        tvHeading = findViewById(R.id.lr_textView1);
        tvSubtitle = findViewById(R.id.lr_subtitle);
        buttonCard = findViewById(R.id.buttonCard);
        featureCard1 = findViewById(R.id.featureCard1);
        featureCard2 = findViewById(R.id.featureCard2);
        featureCard3 = findViewById(R.id.featureCard3);
        developerName = findViewById(R.id.developer_name);
    }

    private void setupWindowInsets() {
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
    }

    private void startEntranceAnimations() {

        // Logo Animation - Scale and Fade
        logoParent.setScaleX(0.6f);
        logoParent.setScaleY(0.6f);
        logoParent.setAlpha(0f);

        logoParent.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(800)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        // Heading Animation - Slide Up
        tvHeading.setTranslationY(40f);
        tvHeading.setAlpha(0f);

        tvHeading.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(600)
                .setStartDelay(200)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        // Subtitle Animation
        tvSubtitle.setTranslationY(30f);
        tvSubtitle.setAlpha(0f);

        tvSubtitle.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(600)
                .setStartDelay(350)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        // Button Card Animation - Scale from bottom
        buttonCard.setScaleY(0.95f);
        buttonCard.setAlpha(0f);

        buttonCard.animate()
                .scaleY(1f)
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        // Feature Cards Animation - Sequential
        animateFeatureCard(featureCard1, 600);
        animateFeatureCard(featureCard2, 700);
        animateFeatureCard(featureCard3, 800);

        // Developer Section Animation
        developerName.setAlpha(0f);
        developerName.animate()
                .alpha(1f)
                .setDuration(400)
                .setStartDelay(900)
                .start();
    }

    private void animateFeatureCard(View card, int startDelay) {
        card.setTranslationY(30f);
        card.setAlpha(0f);

        card.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(startDelay)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void setupClickListeners() {

        // Login Button with Ripple Animation
        btnLogin.setOnClickListener(v -> {
            animateButtonClick(btnLogin, () -> {
                Intent intent = new Intent(LoginRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
        });

        // Register Button with Ripple Animation
        btnRegister.setOnClickListener(v -> {
            animateButtonClick(btnRegister, () -> {
                Intent intent = new Intent(LoginRegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            });
        });

        // Optional: Add ripple effect to feature cards
        setupFeatureCardClick(featureCard1, "Safe & Verified Rides");
        setupFeatureCardClick(featureCard2, "Easy Booking System");
        setupFeatureCardClick(featureCard3, "Best Price Guarantee");
    }

    private void animateButtonClick(MaterialButton button, Runnable action) {
        // Scale animation for button press
        button.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() -> {
                    button.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .withEndAction(action)
                            .start();
                })
                .start();

        // Optional: Add a slight elevation change
        ValueAnimator elevationAnimator = ValueAnimator.ofFloat(2f, 6f, 2f);
        elevationAnimator.setDuration(200);
        elevationAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            button.setElevation(value);
        });
        elevationAnimator.start();
    }

    private void setupFeatureCardClick(CardView card, String featureName) {
        card.setOnClickListener(v -> {
            // Subtle feedback when clicking feature cards
            card.animate()
                    .scaleX(0.98f)
                    .scaleY(0.98f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        card.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start();

                        // Show toast or snackbar with feature info
                        Toast.makeText(
                                LoginRegisterActivity.this,
                                featureName,
                                Toast.LENGTH_SHORT
                        ).show();
                    })
                    .start();
        });

        // Add touch feedback
        card.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case android.view.MotionEvent.ACTION_DOWN:
                    card.animate().scaleX(0.98f).scaleY(0.98f).setDuration(100).start();
                    break;
                case android.view.MotionEvent.ACTION_UP:
                case android.view.MotionEvent.ACTION_CANCEL:
                    card.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    break;
            }
            return false;
        });
    }

    private void setupDeveloperCredit() {
        developerName.setOnClickListener(v -> {
            // Developer credit animation
            ObjectAnimator pulseAnimator = ObjectAnimator.ofFloat(developerName, "scaleX", 1f, 1.1f, 1f);
            pulseAnimator.setDuration(300);
            pulseAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            pulseAnimator.start();

            // You can add a dialog or intent here showing developer info
            Toast.makeText(
                    LoginRegisterActivity.this,
                    "Developed by Shelu Pandey",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure any animations are reset
        if (btnLogin != null && btnRegister != null) {
            btnLogin.setScaleX(1f);
            btnLogin.setScaleY(1f);
            btnRegister.setScaleX(1f);
            btnRegister.setScaleY(1f);
        }
    }
}