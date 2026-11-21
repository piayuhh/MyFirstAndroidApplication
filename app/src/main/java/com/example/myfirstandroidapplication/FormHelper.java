package com.example.myfirstandroidapplication;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Small helper used by the Login and Register screens.
 * It shows/clears the red error under a box and handles the eye button.
 */
public class FormHelper {

    // Show a red message under the box and turn its border red
    public static void showError(EditText field, TextView errorView, String message) {
        errorView.setText(message);
        errorView.setVisibility(View.VISIBLE);
        field.setBackgroundResource(R.drawable.bg_input_error);
    }

    // Hide the message and return the box to normal
    public static void clearError(EditText field, TextView errorView) {
        errorView.setVisibility(View.GONE);
        field.setBackgroundResource(R.drawable.bg_input);
    }

    // Clear the error automatically as soon as the user starts typing again
    public static void clearErrorOnTyping(final EditText field, final TextView errorView) {
        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (errorView.getVisibility() == View.VISIBLE) {
                    clearError(field, errorView);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // Eye button: show or hide the password
    public static void setupPasswordToggle(final EditText field, final ImageButton toggle) {
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHidden =
                        field.getTransformationMethod() instanceof PasswordTransformationMethod;
                if (isHidden) {
                    field.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    toggle.setImageResource(R.drawable.ic_eye);
                } else {
                    field.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    toggle.setImageResource(R.drawable.ic_eye_off);
                }
                field.setSelection(field.getText().length());
            }
        });
    }
}
