package com.iot.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressWarnings("ALL")
public class WellcomeActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prf;
    TextView showUserNameTextView;
    public static final String GOOGLE_ACCOUNT = "google_account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        TextView showUserEmailTextView = findViewById(R.id.email_text_view);
        showUserNameTextView = findViewById(R.id.name_text_view);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);

        showUserNameTextView.setText("Name: " + prf.getString("userName", null));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            showUserEmailTextView.setText(user.getEmail());
        }
    }

    @Override
    public void onClick(View v) {
    }
}
