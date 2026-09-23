package com.example.aula05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editPeso, editAltura;
    Button btnCalcular;

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
                } else if (imc < 25) {
                    intent = new Intent(MainActivity.this, ResultadoNormalActivity.class);
                } else  if (imc < 30) {
                    intent = new Intent(MainActivity.this, ResultadoSobrePesoActivity.class);
                } else if (imc < 35) {
                    intent = new Intent(MainActivity.this, ResultadoObesidadeClasse1Activity.class);
                } else {
                    intent = new Intent(MainActivity.this, ResultadoObsedidadeClasse2Activity.class);
                }
                startActivity(intent);

                /*Abaixo do 18,5:
                Abaixo do peso 18,5 a 24,9:
                Peso normal ou adequado 25,5 a 24,9:
                Sobrepeso30,0 a 39,9: Obesidade
                Acima de 40,0: Obesidade grave*/

            }
        });
    }
}


