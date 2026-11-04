
package com.example.aula12_autenticao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView txtEmail;
    Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtEmail = findViewById(R.id.txtEmail);
        btnSair = findViewById(R.id.btnSair);

        String email = getIntent().getStringExtra("email");

        txtEmail.setText("Bem-vindo, " + email + "!");

        btnSair.setOnClickListener(v -> {

            Intent intent = new Intent(
                    HomeActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
            finish();
        });
    }
}

