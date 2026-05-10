package com.example.tt;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY_MS = 2600;

    private ImageView logo;

    private MaterialCardView logoCard;

    private TextView appTitle;

    private TextView subtitle;

    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_splash_screen);

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

        startAnimations();

        moveToNextScreen();
    }

    private void initViews() {

        logo = findViewById(R.id.logo);

        logoCard = findViewById(R.id.logoCard);

        appTitle = findViewById(R.id.appTitle);

        subtitle = findViewById(R.id.textView);

        loadingText = findViewById(R.id.loadingText);
    }

    private void startAnimations() {

        // Logo Floating Animation

        ObjectAnimator floatingAnimator =
                ObjectAnimator.ofPropertyValuesHolder(
                        logoCard,
                        PropertyValuesHolder.ofFloat(
                                View.TRANSLATION_Y,
                                0f,
                                -18f,
                                0f
                        )
                );

        floatingAnimator.setDuration(2400);

        floatingAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        floatingAnimator.setInterpolator(
                new AccelerateDecelerateInterpolator()
        );

        floatingAnimator.start();

        // Logo Fade Animation

        logo.setAlpha(0f);

        logo.animate()
                .alpha(1f)
                .setDuration(1200)
                .start();

        // Title Animation

        appTitle.setTranslationY(40f);

        appTitle.setAlpha(0f);

        appTitle.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(900)
                .setStartDelay(300)
                .start();

        // Subtitle Animation

        subtitle.setTranslationY(40f);

        subtitle.setAlpha(0f);

        subtitle.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(900)
                .setStartDelay(500)
                .start();

        // Loading Animation

        loadingText.setAlpha(0.5f);

        loadingText.animate()
                .alpha(1f)
                .setDuration(900)
                .setStartDelay(700)
                .start();
    }

    private void moveToNextScreen() {

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent intent =
                    new Intent(
                            SplashScreen.this,
                            LoginRegisterActivity.class
                    );

            startActivity(intent);

            overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
            );

            finish();

        }, SPLASH_DELAY_MS);
    }
}