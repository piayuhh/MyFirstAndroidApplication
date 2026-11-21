package com.example.myfirstandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView errUsername, errPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        errUsername = findViewById(R.id.errUsername);
        errPassword = findViewById(R.id.errPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        View pillRegister = findViewById(R.id.pillRegister);
        TextView tvForgot = findViewById(R.id.tvForgot);
        ImageButton btnToggle = findViewById(R.id.btnTogglePassword);

        FormHelper.setupPasswordToggle(etPassword, btnToggle);
        FormHelper.clearErrorOnTyping(etUsername, errUsername);
        FormHelper.clearErrorOnTyping(etPassword, errPassword);

        // If we came here after registering, fill in the username
        String registeredUsername = getIntent().getStringExtra("registered_username");
        if (registeredUsername != null) {
            etUsername.setText(registeredUsername);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        pillRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Password reset is not built yet, so we just show a message
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,
                        "Password reset is not available yet.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptLogin() {
        FormHelper.clearError(etUsername, errUsername);
        FormHelper.clearError(etPassword, errPassword);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        // 1. Empty fields
        boolean valid = true;
        if (username.isEmpty()) {
            FormHelper.showError(etUsername, errUsername, "Please enter your username");
            valid = false;
        }
        if (password.isEmpty()) {
            FormHelper.showError(etPassword, errPassword, "Please enter your password");
            valid = false;
        }
        if (!valid) return;

        // 2. Does the account exist?
        Student student = StudentRepository.findByUsername(username);
        if (student == null) {
            FormHelper.showError(etUsername, errUsername, "No account found with this username");
            return;
        }

        // 3. Is the password correct?
        if (!student.getPassword().equals(password)) {
            FormHelper.showError(etPassword, errPassword, "Incorrect password");
            return;
        }

        // 4. Success: open the Dashboard and pass the student's info
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        intent.putExtra(DashboardActivity.EXTRA_STUDENT_NUMBER, student.getStudentNumber());
        intent.putExtra(DashboardActivity.EXTRA_LAST_NAME, student.getLastName());
        intent.putExtra(DashboardActivity.EXTRA_FIRST_NAME, student.getFirstName());
        intent.putExtra(DashboardActivity.EXTRA_MIDDLE_NAME, student.getMiddleName());
        intent.putExtra(DashboardActivity.EXTRA_PROGRAM, student.getProgram());
        intent.putExtra(DashboardActivity.EXTRA_SPECIALIZATION, student.getSpecialization());
        intent.putExtra(DashboardActivity.EXTRA_YEAR_LEVEL, student.getYearLevel());
        startActivity(intent);
        finish();
    }
}
