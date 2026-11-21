package com.example.myfirstandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT_NUMBER = "student_number";
    public static final String EXTRA_LAST_NAME = "last_name";
    public static final String EXTRA_FIRST_NAME = "first_name";
    public static final String EXTRA_MIDDLE_NAME = "middle_name";
    public static final String EXTRA_PROGRAM = "program";
    public static final String EXTRA_SPECIALIZATION = "specialization";
    public static final String EXTRA_YEAR_LEVEL = "year_level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        String studentNumber = intent.getStringExtra(EXTRA_STUDENT_NUMBER);
        String lastName = intent.getStringExtra(EXTRA_LAST_NAME);
        String firstName = intent.getStringExtra(EXTRA_FIRST_NAME);
        String middleName = intent.getStringExtra(EXTRA_MIDDLE_NAME);
        String program = intent.getStringExtra(EXTRA_PROGRAM);
        String specialization = intent.getStringExtra(EXTRA_SPECIALIZATION);
        String yearLevel = intent.getStringExtra(EXTRA_YEAR_LEVEL);

        // Middle name and specialization are optional
        if (middleName == null || middleName.isEmpty()) {
            middleName = "Not provided";
        }
        if (specialization == null || specialization.isEmpty()) {
            specialization = "Not specified";
        }

        ((TextView) findViewById(R.id.tvWelcome)).setText("Hey, " + firstName + ".");
        ((TextView) findViewById(R.id.tvStudentNumber)).setText(studentNumber);
        ((TextView) findViewById(R.id.tvLastName)).setText(lastName);
        ((TextView) findViewById(R.id.tvFirstName)).setText(firstName);
        ((TextView) findViewById(R.id.tvMiddleName)).setText(middleName);
        ((TextView) findViewById(R.id.tvProgram)).setText(program);
        ((TextView) findViewById(R.id.tvSpecialization)).setText(specialization);
        ((TextView) findViewById(R.id.tvYearLevel)).setText(yearLevel);

        // Dynamically compute avatar initials (e.g., ST for Sophia Terol)
        TextView tvAvatar = findViewById(R.id.tvAvatarInitials);
        if (tvAvatar != null) {
            StringBuilder initials = new StringBuilder();
            if (firstName != null && !firstName.trim().isEmpty()) {
                initials.append(firstName.trim().toUpperCase().charAt(0));
            }
            if (lastName != null && !lastName.trim().isEmpty()) {
                initials.append(lastName.trim().toUpperCase().charAt(0));
            }
            if (initials.length() > 0) {
                tvAvatar.setText(initials.toString());
            }
        }

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "You have been logged out.",
                        Toast.LENGTH_SHORT).show();
                Intent logout = new Intent(DashboardActivity.this, LoginActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
                finish();
            }
        });
    }
}
