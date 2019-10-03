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


@SuppressWarnings("deprecation")
public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editSignInEmail;
    private EditText editSignInTextPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editSignInEmail = findViewById(R.id.sign_in_email);
        editSignInTextPassword = findViewById(R.id.sign_in_password);

        auth = FirebaseAuth.getInstance();

        findViewById(R.id.button_sign_in).setOnClickListener(this);
        findViewById(R.id.text_view_sing_up).setOnClickListener(this);
    }

    private void signIn() {
        final String email = editSignInEmail.getText().toString();
        final String password = editSignInTextPassword.getText().toString();

        if (validationSignInFields(email, password)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                onSuccess();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.failure),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

    }

    private void onSuccess() {
        Intent intent = new Intent(this, WellcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        clearFields();
    }


    private void showEmailAndPawwsordErrorMessage() {
        editSignInEmail.setError(getString(R.string.enter_email));
        editSignInTextPassword.setError(getString(R.string.enter_password));
        editSignInTextPassword.requestFocus();
        editSignInEmail.requestFocus();
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(new Intent(this, SignUpActivity.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void clearFields() {
        editSignInEmail.getText().clear();
        editSignInTextPassword.getText().clear();
    }

    private boolean validationSignInFields(final String email, final String password) {
        boolean alldone = true;

        if (email.isEmpty() && password.isEmpty()) {
            showEmailAndPawwsordErrorMessage();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editSignInEmail.setError(getString(R.string.invalid_email));
            editSignInEmail.requestFocus();
            return false;
        }

        return alldone;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_sing_up:
                startSignUpActivity();
                break;
            case R.id.button_sign_in:
                signIn();
                break;
        }
    }
}