package zeev.fraiman.trigo_claude;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "TrigonometryApp";
    private static final String KEY_USER_LOGGED_IN = "user_logged_in";

    private Button btnTheory, btnPractice, btnQuiz, btnProgress, btnSettings;
    private FrameLayout fragmentContainer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Check authorization
        if (!isUserLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();

        // Load theory fragment by default
        loadFragment(new TheoryFragment());
        setActiveButton(btnTheory);
    }

    private void initializeViews() {
        btnTheory = findViewById(R.id.btn_theory);
        btnPractice = findViewById(R.id.btn_practice);
        btnQuiz = findViewById(R.id.btn_quiz);
        btnProgress = findViewById(R.id.btn_progress);
        btnSettings = findViewById(R.id.btn_settings);
        fragmentContainer = findViewById(R.id.fragment_container);
    }

    private void setupClickListeners() {
        btnTheory.setOnClickListener(v -> {
            loadFragment(new TheoryFragment());
            setActiveButton(btnTheory);
        });

        btnPractice.setOnClickListener(v -> {
            loadFragment(new PracticeFragment());
            setActiveButton(btnPractice);
        });

        btnQuiz.setOnClickListener(v -> {
            loadFragment(new QuizFragment());
            setActiveButton(btnQuiz);
        });

        btnProgress.setOnClickListener(v -> {
            loadFragment(new ProgressFragment());
            setActiveButton(btnProgress);
        });

        btnSettings.setOnClickListener(v -> {
            loadFragment(new SettingsFragment());
            setActiveButton(btnSettings);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void setActiveButton(Button activeButton) {
        // Reset all button states
        resetButtonStates();

        // Set active state for the selected button
        activeButton.setSelected(true);
    }

    private void resetButtonStates() {
        Button[] buttons = {btnTheory, btnPractice, btnQuiz, btnProgress, btnSettings};
        for (Button button : buttons) {
            button.setSelected(false);
        }
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_USER_LOGGED_IN, false);
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USER_LOGGED_IN, false);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}