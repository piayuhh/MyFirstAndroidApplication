package com.example.myfirstandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirm, etStudentNumber,
            etLastName, etFirstName, etMiddleName, etProgram, etSpecialization;

    private TextView errUsername, errPassword, errConfirm, errStudentNumber,
            errLastName, errFirstName, errProgram;

    private RadioGroup rgYearLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        etStudentNumber = findViewById(R.id.etStudentNumber);
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);
        etProgram = findViewById(R.id.etProgram);
        etSpecialization = findViewById(R.id.etSpecialization);
        rgYearLevel = findViewById(R.id.rgYearLevel);

        errUsername = findViewById(R.id.errUsername);
        errPassword = findViewById(R.id.errPassword);
        errConfirm = findViewById(R.id.errConfirm);
        errStudentNumber = findViewById(R.id.errStudentNumber);
        errLastName = findViewById(R.id.errLastName);
        errFirstName = findViewById(R.id.errFirstName);
        errProgram = findViewById(R.id.errProgram);

        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvLogin = findViewById(R.id.tvLogin);
        ImageButton btnBack = findViewById(R.id.btnBack);

        // Eye buttons
        FormHelper.setupPasswordToggle(etPassword, (ImageButton) findViewById(R.id.btnTogglePassword));
        FormHelper.setupPasswordToggle(etConfirm, (ImageButton) findViewById(R.id.btnToggleConfirm));

        // Remove red errors when the user types again
        FormHelper.clearErrorOnTyping(etUsername, errUsername);
        FormHelper.clearErrorOnTyping(etPassword, errPassword);
        FormHelper.clearErrorOnTyping(etConfirm, errConfirm);
        FormHelper.clearErrorOnTyping(etStudentNumber, errStudentNumber);
        FormHelper.clearErrorOnTyping(etLastName, errLastName);
        FormHelper.clearErrorOnTyping(etFirstName, errFirstName);
        FormHelper.clearErrorOnTyping(etProgram, errProgram);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        // Both the back button and the "Log in" link close this screen
        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        tvLogin.setOnClickListener(goBack);
        btnBack.setOnClickListener(goBack);
    }

    private void attemptRegister() {
        clearAllErrors();

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();
        String studentNumber = etStudentNumber.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String middleName = etMiddleName.getText().toString().trim();         // optional
        String program = etProgram.getText().toString().trim();
        String specialization = etSpecialization.getText().toString().trim(); // optional

        // Year level (1st, 2nd, 3rd or 4th). 1st is selected by default.
        RadioButton selectedYear = findViewById(rgYearLevel.getCheckedRadioButtonId());
        String yearLevel = selectedYear.getText().toString() + " Year";

        // Required fields: show every missing one at once
        boolean valid = true;
        valid &= required(etUsername, errUsername, username, "Username is required");
        valid &= required(etPassword, errPassword, password, "Password is required");
        valid &= required(etConfirm, errConfirm, confirm, "Please confirm your password");
        valid &= required(etStudentNumber, errStudentNumber, studentNumber, "Student number is required");
        valid &= required(etLastName, errLastName, lastName, "Last name is required");
        valid &= required(etFirstName, errFirstName, firstName, "First name is required");
        valid &= required(etProgram, errProgram, program, "Program is required");
        if (!valid) return;

        if (password.length() < 8) {
            FormHelper.showError(etPassword, errPassword, "Password must be at least 8 characters");
            return;
        }
        if (!password.equals(confirm)) {
            FormHelper.showError(etConfirm, errConfirm, "Passwords do not match");
            return;
        }

        // No duplicates
        if (StudentRepository.isUsernameTaken(username)) {
            FormHelper.showError(etUsername, errUsername, "This username is already taken");
            return;
        }
        if (StudentRepository.isStudentNumberTaken(studentNumber)) {
            FormHelper.showError(etStudentNumber, errStudentNumber, "This student number is already registered");
            return;
        }

        // Save in the temporary in-memory list
        Student student = new Student(username, password, studentNumber, lastName,
                firstName, middleName, program, specialization, yearLevel);
        StudentRepository.addStudent(student);

        Toast.makeText(this, "Registration successful! Please log in.",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("registered_username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Shows an error if the value is empty. Returns true when the value is OK.
    private boolean required(EditText field, TextView error, String value, String message) {
        if (value.isEmpty()) {
            FormHelper.showError(field, error, message);
            return false;
        }
        return true;
    }

    private void clearAllErrors() {
        FormHelper.clearError(etUsername, errUsername);
        FormHelper.clearError(etPassword, errPassword);
        FormHelper.clearError(etConfirm, errConfirm);
        FormHelper.clearError(etStudentNumber, errStudentNumber);
        FormHelper.clearError(etLastName, errLastName);
        FormHelper.clearError(etFirstName, errFirstName);
        FormHelper.clearError(etProgram, errProgram);
    }
}
