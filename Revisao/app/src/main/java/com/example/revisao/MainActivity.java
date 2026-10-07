package com.example.revisao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etNum1 = findViewById(R.id.etNum1);
    EditText etNum2 = findViewById(R.id.etNum2);
    Button btnEnviar = findViewById(R.id.btnEnviar);
    double resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnEnviar.setOnClickListener(v -> {
            Double num1 = Double.parseDouble(etNum1.getText().toString());
            Double num2 = Double.parseDouble(etNum2.getText().toString());

            resultado = num1 + num2;

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("tvResultado", resultado);
            startActivity(intent);

        });

    }
}