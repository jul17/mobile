package com.iot.mobiledevelopment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


@SuppressWarnings("deprecation")
public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editSignInEmail, editSignInTextPassword, editSignInTextUserName, editSignInTextPhoneNumber;

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private Object Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editSignInEmail = findViewById(R.id.sign_in_email);
        editSignInTextPassword = findViewById(R.id.sign_in_password);
        editSignInTextUserName = findViewById(R.id.sing_up_name);
        editSignInTextPhoneNumber = findViewById(R.id.sign_up_phone);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_google_button).setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        findViewById(R.id.button_sign_in).setOnClickListener(this);

        findViewById(R.id.text_view_sing_up).setOnClickListener(this);
    }

    private void signIn() {
        final String email = editSignInEmail.getText().toString();
        final String password = editSignInTextPassword.getText().toString();

        if (email.isEmpty() && password.isEmpty()) {
            editSignInEmail.setError("Enter email");
            editSignInTextPassword.setError("Enter password");
            editSignInTextPassword.requestFocus();
            editSignInEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editSignInEmail.setError("Invalid email address");
            editSignInEmail.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onSuccess();
                    clearFields();
                    auth.getCurrentUser();
                } else {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onSuccess() {
        Toast.makeText(getApplicationContext(), "Authorisation is Success", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, WellcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void clearFields() {
        editSignInEmail.getText().clear();
        editSignInTextPassword.getText().clear();
    }

    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_sing_up:
                Intent intent = new Intent(new Intent(this, SignUpActivity.class));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.button_sign_in:
                signIn();
                break;
            case R.id.sign_in_google_button:
                googleSignIn();
                break;

        }
    }
}