package com.iot.mobiledevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;


@SuppressWarnings("ALL")
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText editTextSignUpEmail;
    private EditText editTextSignUpPassword;
    private EditText editTextSignUpUserName;
    private EditText editSignUpTextPhoneNumber;
    private static final Integer maxPasswordLength = 8;
    private final static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^380*.{10}");
    private boolean successValidation = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextSignUpEmail = findViewById(R.id.sign_up_email);
        editTextSignUpPassword = findViewById(R.id.sign_up_password);
        editTextSignUpUserName = findViewById(R.id.sing_up_name);
        editSignUpTextPhoneNumber = findViewById(R.id.sign_up_phone);

        auth = FirebaseAuth.getInstance();

        findViewById(R.id.button_sign_up).setOnClickListener(this);
        findViewById(R.id.text_view_sing_in).setOnClickListener(this);
    }

    private void signUp() {

        final String userName = editTextSignUpUserName.getText().toString();
        final String phoneNumber = editSignUpTextPhoneNumber.getText().toString();
        final String email = editTextSignUpEmail.getText().toString();
        final String password = editTextSignUpPassword.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (allFieldValidation(userName, phoneNumber, email, password)) {
            if (auth.getCurrentUser() != null) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    addUsername(userName);
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            getString(R.string.failure),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        }
    }

    private boolean allFieldValidation(final String userName, final String phoneNumber,
                                       final String email, final String password) {
        if (!(userNameFieldvalidation(userName))
                & !(phoneNumberValidationField(phoneNumber))
                & !(emailValidationField(email))
                & !(passwordFieldValidation(password))) {
            return false;
        }
        if (!(userNameFieldvalidation(userName))) {
            return false;
        }
        if (!(phoneNumberValidationField(phoneNumber))) {
            return false;
        }
        if (!(emailValidationField(email))) {
            return false;
        }
        if (!(passwordFieldValidation(password))) {
            return false;
        }

        return successValidation;
    }


    private void clearFields() {
        editTextSignUpUserName.getText().clear();
        editSignUpTextPhoneNumber.getText().clear();
        editTextSignUpEmail.getText().clear();
        editTextSignUpPassword.getText().clear();
    }

    private void addUsername(String userName) {
        FirebaseUser userProfile = auth.getCurrentUser();
        UserProfileChangeRequest userUpdateProfile = new UserProfileChangeRequest
                .Builder().setDisplayName(userName).build();

        userProfile.updateProfile(userUpdateProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task1) {
                        if (task1.isSuccessful()) {
                            startNexActivity(WelcomeActivity.class);
                        }
                    }

                });
    }

    private void startNexActivity(Class cls) {
        clearFields();

        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private boolean userNameFieldvalidation(String userName) {
        if (userName.isEmpty()) {
            editTextSignUpUserName.setError(getString(R.string.show_messg_required_name));
            editTextSignUpUserName.requestFocus();
            return false;
        }
        return successValidation;
    }

    private boolean phoneNumberValidationField(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            editSignUpTextPhoneNumber.setError(getString(R.string.show_messg_required_phone));
            editSignUpTextPhoneNumber.requestFocus();
            return false;
        } else if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            editSignUpTextPhoneNumber.setError(getString(R.string.show_messg_wrong_phone));
            editSignUpTextPhoneNumber.requestFocus();
            return false;
        }
        return successValidation;
    }

    private boolean emailValidationField(String email) {
        if (email.isEmpty()) {
            editTextSignUpEmail.setError(getString(R.string.show_messg_required_email));
            editTextSignUpEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextSignUpEmail.setError(getString(R.string.invalid_email));
            editTextSignUpEmail.requestFocus();
            return false;
        }


        return successValidation;
    }

    private boolean passwordFieldValidation(String password) {
        if (password.isEmpty()) {
            editTextSignUpPassword.setError(getString(R.string.required_password));
            editTextSignUpPassword.requestFocus();
            return false;
        } else if (password.length() < maxPasswordLength) {
            editTextSignUpPassword.setError(getString(R.string.weak_password));
            editTextSignUpPassword.requestFocus();
            return false;
        }
        return successValidation;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_up:
                signUp();
                break;
            case R.id.text_view_sing_in:
                startNexActivity(SignInActivity.class);
                break;
        }
    }
}

