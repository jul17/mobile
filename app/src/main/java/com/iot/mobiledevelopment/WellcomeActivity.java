package com.iot.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressWarnings("ALL")
public class WellcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private TextView showUserNameTextView;
    private TextView showUserEmailTextView;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        showUserEmailTextView = findViewById(R.id.wellcome_email_text_view);
        showUserNameTextView = findViewById(R.id.wellcome_name_text_view);
        findViewById(R.id.wellcome_sign_out_button).setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        FirebaseUser userCredentials = auth.getCurrentUser();
        if (userCredentials != null) {
            showUserEmailTextView.setText(userCredentials.getEmail());
            showUserNameTextView.setText(userCredentials.getDisplayName());
            toastMessage(getString(R.string.wellcome) + " " + userCredentials.getDisplayName());
        } else {
            toastMessage(getString(R.string.success_sign_out));
        }
    }


    private void signOut() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wellcome_sign_out_button:
                signOut();
                break;
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
