package com.iot.mobiledevelopment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;


@SuppressWarnings("ALL")
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private EditText editTextSignUpEmail;
    private EditText editTextSignUpPassword;
    private EditText editTextSignUpUserName;
    private EditText editSignUpTextPhoneNumber;
    private static final Integer maxPasswordLength = 8;
    private final static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^380*.{10}");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextSignUpEmail = findViewById(R.id.sign_up_email);
        editTextSignUpPassword = findViewById(R.id.sign_up_password);
        editTextSignUpUserName = findViewById(R.id.sing_up_name);
        editSignUpTextPhoneNumber = findViewById(R.id.sign_up_phone);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        findViewById(R.id.button_sign_up).setOnClickListener(this);
        findViewById(R.id.text_view_sing_in).setOnClickListener(this);
    }

    private void signUp() {

        final String userName = editTextSignUpUserName.getText().toString();
        final String phoneNumber = editSignUpTextPhoneNumber.getText().toString();
        final String email = editTextSignUpEmail.getText().toString();
        final String password = editTextSignUpPassword.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (validationSignUpFields(userName, phoneNumber, email, password, user)) {
            if (auth.getCurrentUser() != null) {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    onSuccess();
                                    saveUserInfo(userName, phoneNumber, email);
                                    auth.getCurrentUser();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.failure),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
    }

    private boolean validationSignUpFields(final String userName, final String phoneNumber,
                                           final String password, final String email, FirebaseUser user) {

        boolean alldone = true;

        if (email.isEmpty() && password.isEmpty() && phoneNumber.isEmpty() && userName.isEmpty()) {
            showErrorIsEmptyFields();
            return false;
        } else if (email.isEmpty()) {
            showEmailIsEmptyError();
            return false;
        } else if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches() && password.length() < 8) {
            showPhoneNumberErrorValidation();
            showPasswordErrorValidation();
            return false;
        } else if (Objects.requireNonNull(Objects.requireNonNull(user).getEmail()).equals(email)) {
            editTextSignUpEmail.setError(getString(R.string.show_messg_registered_user_err));
            editTextSignUpEmail.requestFocus();
            return false;
        } else if (phoneNumber.isEmpty() && userName.isEmpty()) {
            showPhoneNumberErrorIsEmpty();
            showUseNameIsEmptyError();
            return false;
        } else if (userName.isEmpty()) {
            showUseNameIsEmptyError();
            return false;
        } else if (password.length() < maxPasswordLength) {
            showPasswordErrorValidation();
        }

        return alldone;
    }

    private void saveUserInfo(final String userName, final String phoneNumber, final String email) {

        UserInformation userInfo = new UserInformation(userName, phoneNumber, email);

        userInfo.setEmail(email);
        userInfo.setPhonenumber(phoneNumber);
        userInfo.setUsername(userName);
        mDatabase.child("UserNode").child(auth.getCurrentUser().getUid()).setValue(userInfo,
                new DatabaseReference.CompletionListener() {
                    public void onComplete(DatabaseError databaseError,
                                           DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(getApplicationContext(), getString(R.string.data_saved),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void onSuccess() {
        startNexActivity(WellcomeActivity.class);
        clearFields();
    }

    private void clearFields() {
        editTextSignUpUserName.getText().clear();
        editSignUpTextPhoneNumber.getText().clear();
        editTextSignUpEmail.getText().clear();
        editTextSignUpPassword.getText().clear();
    }

    private void showErrorIsEmptyFields() {
        editTextSignUpEmail.setError(getString(R.string.show_messg_required_email));
        editTextSignUpPassword.setError(getString(R.string.required_password));
        editTextSignUpUserName.setError(getString(R.string.show_messg_required_name));
        editSignUpTextPhoneNumber.setError(getString(R.string.show_messg_required_phone));
        editTextSignUpEmail.requestFocus();
        editTextSignUpPassword.requestFocus();
        editSignUpTextPhoneNumber.requestFocus();
        editTextSignUpUserName.requestFocus();
    }

    private void showUseNameIsEmptyError() {
        editTextSignUpUserName.setError(getString(R.string.show_messg_required_name));
        editTextSignUpUserName.requestFocus();
    }

    private void showPhoneNumberErrorIsEmpty() {
        editSignUpTextPhoneNumber.setError(getString(R.string.show_messg_required_phone));
        editSignUpTextPhoneNumber.requestFocus();
    }

    private void showEmailIsEmptyError() {
        editTextSignUpEmail.setError(getString(R.string.show_messg_required_email));
        editTextSignUpEmail.requestFocus();
    }


    private void showPhoneNumberErrorValidation() {
        editSignUpTextPhoneNumber.setError(getString(R.string.show_messg_wrong_phone));
        editSignUpTextPhoneNumber.requestFocus();
    }

    private void showPasswordErrorValidation() {
        editTextSignUpPassword.setError(getString(R.string.show_messg_wrong_phone));
        editTextSignUpPassword.requestFocus();
    }

    private void startNexActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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

