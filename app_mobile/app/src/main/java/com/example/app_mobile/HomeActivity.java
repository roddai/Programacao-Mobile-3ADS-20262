package com.example.app_mobile;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvSuccessMessage = findViewById(R.id.tvSuccessMessage);
        String email = getIntent().getStringExtra("USER_EMAIL");

        if (email != null) {
            tvSuccessMessage.setText("Usuário " + email + " autenticado com sucesso!");
        }
    }
}