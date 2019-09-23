package com.iot.mobiledevelopment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Objects;


@SuppressWarnings("ALL")
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextSignUpEmail, editTextSignUpPassword, editTextSignUpUserName, editSignUpTextPhoneNumber;
    private FirebaseAuth auth;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextSignUpEmail = findViewById(R.id.sign_up_email);
        editTextSignUpPassword = findViewById(R.id.sign_up_password);
        editTextSignUpUserName = findViewById(R.id.sing_up_name);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
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

        if (email.isEmpty() && password.isEmpty() && phoneNumber.isEmpty() && userName.isEmpty()) {
            editTextSignUpEmail.setError("Email is required");
            editTextSignUpPassword.setError("Password is required");
            editTextSignUpUserName.setError("User name is required");
            editSignUpTextPhoneNumber.setError("Phone number is required");
            editTextSignUpEmail.requestFocus();
            editTextSignUpPassword.requestFocus();
            editSignUpTextPhoneNumber.requestFocus();
            editTextSignUpUserName.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextSignUpEmail.setError("Unvalid email address");
            return;
        }


        if (password.length() < 8 && phoneNumber.length() < 10 || phoneNumber.length() < 8) {
            editTextSignUpPassword.setError("Minimim length of password should be 8");
            editSignUpTextPhoneNumber.setError("Wrong phone number");
            editTextSignUpPassword.requestFocus();
            editTextSignUpUserName.requestFocus();
            return;
        }

        if (Objects.requireNonNull(Objects.requireNonNull(user).getEmail()).equals(email)){
            editTextSignUpEmail.setError("This user is already registread");
            editTextSignUpEmail.requestFocus();
            return;
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onSuccess();
                    clearFields();
                    auth.getCurrentUser();
                } else {
                    Toast.makeText(getApplicationContext(), "Authorisation is Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onSuccess(){
        Toast.makeText(getApplicationContext(), "Authorisation is Success", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, WellcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void clearFields(){
        editTextSignUpUserName.getText().clear();
        editSignUpTextPhoneNumber.getText().clear();
        editTextSignUpEmail.getText().clear();
        editTextSignUpPassword.getText().clear();
    }

        @Override
    public void onClick(View v) {
            SharedPreferences.Editor editor = pref.edit();
            String userName = editTextSignUpUserName.getText().toString();
            editor.putString("userName", userName);
            editor.commit();
            editor.apply();

        switch (v.getId()){
            case R.id.button_sign_up:
                signUp();
                break;
            case R.id.text_view_sing_in:
                Intent intent = new Intent(this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }
}

