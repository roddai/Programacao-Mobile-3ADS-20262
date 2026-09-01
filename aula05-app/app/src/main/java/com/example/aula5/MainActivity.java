package com.example.aula5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editPeso, editAltura;
    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double peso = Double.parseDouble(editPeso.getText().toString());
                double altura = Double.parseDouble(editAltura.getText().toString());
                double imc = peso / (altura * altura);

                Intent intent;
                if (imc < 18.5) {
                    intent = new Intent(MainActivity.this, ResultadoAbaixoDoPesoActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, ResultadoObesidadeClasse2.class);
                }
                startActivity(intent);
            }
        });
    }
}