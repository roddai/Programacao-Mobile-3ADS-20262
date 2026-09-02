package com.example.aula5;

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

    EditText editPeso, editaltura;

    Button bntCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        editPeso = findViewById(R.id.editPeso);
        editaltura = findViewById(R.id.editaltura);
        bntCalcular = findViewById(R.id.bntCalcular);

        bntCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                double peso = Double.parseDouble(editPeso.getText().toString());
                double altura = Double.parseDouble(editaltura.getText().toString());
                double imc = peso / (altura * altura);

                Intent intent;

                if(imc < 18.5){
                    intent = new Intent(MainActivity.this,ResultadoAbaixoDoPesoActivity.class);
                } else{
                    intent = new Intent(MainActivity.this,ResultadoObesidadeClasse1Activity.class);;
                }

                startActivity(intent);
            }
        });
    }
}