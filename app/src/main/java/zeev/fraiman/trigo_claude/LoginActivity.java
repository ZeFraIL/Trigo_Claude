package zeev.fraiman.trigo_claude;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "TrigonometryApp";
    private static final String KEY_USER_LOGGED_IN = "user_logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private TextView tvToggleMode, tvModeTitle;
    private SharedPreferences sharedPreferences;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        initializeViews();
        setupClickListeners();
        updateUI();
    }

    private void initializeViews() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        //btnRegister = findViewById(R.id.btn_register);
        tvToggleMode = findViewById(R.id.tv_toggle_mode);
        tvModeTitle = findViewById(R.id.tv_mode_title);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> {
            if (isLoginMode) {
                performLogin();
            } else {
                performRegistration();
            }
        });

        tvToggleMode.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            updateUI();
        });
    }

    private void updateUI() {
        if (isLoginMode) {
            tvModeTitle.setText(R.string.login_title);
            btnLogin.setText(R.string.login_button);
            tvToggleMode.setText(R.string.no_account_register);
        } else {
            tvModeTitle.setText(R.string.registration_title);
            btnLogin.setText(R.string.register_button);
            tvToggleMode.setText(R.string.already_have_account_login);
        }
    }

    private void performLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateInput(username, password)) {
            String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
            String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

            if (username.equals(savedUsername) && password.equals(savedPassword)) {
                loginSuccess();
            } else {
                Toast.makeText(this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void performRegistration() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateInput(username, password)) {
            if (password.length() < 4) {
                Toast.makeText(this, R.string.error_password_too_short, Toast.LENGTH_SHORT).show();
                return;
            }

            // Save user data
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);
            editor.apply();

            Toast.makeText(this, R.string.registration_successful, Toast.LENGTH_SHORT).show();
            loginSuccess();
        }
    }

    private boolean validateInput(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.error_enter_username));
            etUsername.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_enter_password));
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void loginSuccess() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USER_LOGGED_IN, true);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}