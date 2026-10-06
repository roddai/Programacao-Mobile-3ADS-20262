package com.example.estudo02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SegundaTela extends AppCompatActivity {
    Button btn2;

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.segunda_tela);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.segunda), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text1 = findViewById(R.id.text1);
        btn2 = findViewById(R.id.btn2);

        int resultado = getIntent().getIntExtra("resultado", 0);
        text1.setText(String.valueOf("A soma da: "+ resultado));

        btn2.setOnClickListener(v -> {
            finish();
        });

    }
}