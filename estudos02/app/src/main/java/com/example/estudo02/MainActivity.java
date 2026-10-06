package com.example.estudo02;

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
    Button btn1;
    EditText input1, input2;

    int res;

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

        btn1 = findViewById(R.id.btn1);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);

        btn1.setOnClickListener(v -> {
            int n1 = Integer.parseInt(input1.getText().toString());
            int n2 = Integer.parseInt(input2.getText().toString());

            res = Classe.somar(n1, n2);
            Intent ida = new Intent(MainActivity.this,SegundaTela.class);
            ida.putExtra("resultado", res);
            startActivity(ida);
        });
    }
}