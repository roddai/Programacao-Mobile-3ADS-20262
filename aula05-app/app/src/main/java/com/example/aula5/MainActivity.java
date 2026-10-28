package com.example.aula5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editPeso, editAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
    }

    public void calcularIMC(View view) {
        String sPeso = editPeso.getText().toString();
        String sAltura = editAltura.getText().toString();

        if (sPeso.isEmpty() || sAltura.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        double peso = Double.parseDouble(sPeso);
        double altura = Double.parseDouble(sAltura);
        double imc = peso / (altura * altura);

        Intent intent;

        // Regras da OMS conforme a imagem
        if (imc < 18.5) {
            intent = new Intent(this, ResultadoAbaixoDoPesoActivity.class);
        } else if (imc < 25.0) {
            intent = new Intent(this, ResultadoNormalActivity.class);
        } else if (imc < 30.0) {
            intent = new Intent(this, ResultadoSobrepesoActivity.class);
        } else if (imc < 35.0) {
            intent = new Intent(this, ResultadoObesidadeClasse1Activity.class);
        } else {
            // Acima de 35 (Classe II e III)
            intent = new Intent(this, ResultadoObesidadeClasse2Activity.class);
        }
        
        startActivity(intent);
    }
}
